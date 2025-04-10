package ui.client;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import exception.DataAccessException;
import ui.EscapeSequences;
import ui.Repl;
import ui.State;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class GamePlayClient {

    public WebSocketFacade ws;
    private boolean isConnected;
    private static ArrayList<String> emptyRow;
    private static ArrayList<String> whitePawnRow;
    private static ArrayList<String> blackPawnRow;

    public GamePlayClient(ServerMessageObserver observer, int port) throws DataAccessException {
        String serverUrl = "http://localhost:" + port;
        ws = new WebSocketFacade(serverUrl, observer);
    }

    public String eval(String inputLine, Repl repl) {
        try {
            var tokens = inputLine.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw(repl);
                case "leave" -> leave(repl);
                case "move" -> move(repl, params);
                case "resign" -> resign(repl);
                case "highlight" -> highlight(repl, params);
                case "quit" -> "quitting";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    private String redraw(Repl repl) {
        return repl.boardRepl.drawBoard(repl.game);
    }

    private String leave(Repl repl) throws DataAccessException {
        ws.leave(repl.authToken, repl.gameID);
        repl.gameID = 0;
        repl.state = State.LOGGEDIN;
        repl.game = null;
        isConnected = false;
        return String.format("%s left the game", repl.username);
    }

    private String move(Repl repl, String[] params) throws DataAccessException {
        String errorMessage = "Expected: <STARTING POSITION> <ENDING POSITION>. Example:\nd2 d4";
        try {
            if (repl.state.equals(State.PLAYINGBLACK)) {
                if (repl.game.getTeamTurn().equals(ChessGame.TeamColor.WHITE)) {
                    return "Not your turn";
                }
            } else if (repl.state.equals(State.PLAYINGWHITE)) {
                if (repl.game.getTeamTurn().equals(ChessGame.TeamColor.BLACK)) {
                    return "Not your turn";
                }
            }
            if (params.length != 2) {
                throw new DataAccessException(400, errorMessage);
            }

            ChessPosition startPosition = deserializePosition(params[0]);
            ChessPosition endPosition = deserializePosition(params[1]);
            ChessMove chessMove = new ChessMove(startPosition, endPosition);
            chessMove = handlePromotion(repl.game, chessMove);
            ws.makeMove(repl.authToken, repl.gameID, chessMove);
            return String.format("Move: %s -> %s", params[0], params[1]);
        } catch (IndexOutOfBoundsException ex) {
            throw new DataAccessException(400, errorMessage);
        } catch (NullPointerException ex) {
            throw new DataAccessException(500, "Please try again");
        }
    }

    private ChessMove handlePromotion(ChessGame game, ChessMove move) {
        String validPieces = "Valid pieces are: rook, knight, bishop, queen\n";
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = game.getBoard().getPiece(startPosition);
        if (piece.getPieceType() != ChessPiece.PieceType.PAWN) {
            return move;
        }
        if ((piece.getTeamColor() == ChessGame.TeamColor.WHITE && endPosition.getRow() == 8) ||
                (piece.getTeamColor() == ChessGame.TeamColor.BLACK && endPosition.getRow() == 1)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n" + "SELECT PROMOTION PIECE >>> ");
                String line = scanner.nextLine();
                if (line == null) {
                    System.out.print(validPieces);
                }
                else if (line.equalsIgnoreCase("rook")) {
                    return new ChessMove(startPosition, endPosition, ChessPiece.PieceType.ROOK);
                } else if (line.equalsIgnoreCase("knight")) {
                    return new ChessMove(startPosition, endPosition, ChessPiece.PieceType.KNIGHT);
                } else if (line.equalsIgnoreCase("bishop")) {
                    return new ChessMove(startPosition, endPosition, ChessPiece.PieceType.BISHOP);
                } else if (line.equalsIgnoreCase("queen")) {
                    return new ChessMove(startPosition, endPosition, ChessPiece.PieceType.QUEEN);
                } else {
                    System.out.print(validPieces);
                }
            }
        }
        return move;
    }

    private String resign(Repl repl) throws DataAccessException {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        System.out.print("Resigning forfeits the game and cannot be undone.\n");
        System.out.print("Type 'resign' to confirm: ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if (line.equals("resign")) {
            ws.resign(repl.authToken, repl.gameID);
            return "";
        } else {
            return "Did not resign";
        }
    }

    private String highlight(Repl repl, String[] params) throws DataAccessException {
        String errorMessage = "Expected: highlight <POSITION>. Example:\nhighlight d2";
        if (params.length != 1) {
            throw new DataAccessException(400, errorMessage);
        }
        ChessPosition position = deserializePosition(params[0]);
        return repl.boardRepl.highlight(repl.game, position);
    }

    private ChessPosition deserializePosition(String stringPosition) throws DataAccessException {
        String errorMessage = "Expected position is a letter than a number. Example:\nd2";
        ArrayList<String> viableLetters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        ArrayList<String> viableNumbers = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));

        if (stringPosition.length() != 2) {
            System.out.println(stringPosition.length());
            throw new DataAccessException(400, errorMessage);
        }
        String letter = stringPosition.substring(0, 1);
        String number = stringPosition.substring(1, 2);
        if (!viableLetters.contains(letter) || !viableNumbers.contains(number)){
            throw new DataAccessException(400, errorMessage);
        }
        // a1 is ChessPosition(1, 1)
        // f2 is ChessPosition(2, 6)
        int row = Integer.parseInt(number);
        int col = switch (letter) {
            case "a" -> 1;
            case "b" -> 2;
            case "c" -> 3;
            case "d" -> 4;
            case "e" -> 5;
            case "f" -> 6;
            case "g" -> 7;
            case "h" -> 8;
            default -> throw new DataAccessException(400, errorMessage);
        };
        return new ChessPosition(row, col);
    }

    private String help() {
        return SET_TEXT_COLOR_LIGHT_GREY + SET_TEXT_ITALIC +
                "Here are your options:\n" +

                // redraw
                SET_TEXT_COLOR_BLUE + RESET_TEXT_ITALIC +
                "\tredraw " +
                SET_TEXT_COLOR_WHITE +
                "- redraw the chess board\n" +

                // leave
                SET_TEXT_COLOR_BLUE +
                "\tleave " +
                SET_TEXT_COLOR_WHITE +
                "- leave the game\n" +

                // move
                SET_TEXT_COLOR_BLUE +
                "\tmove <STARTING POSITION> <ENDING POSITION> " +
                SET_TEXT_COLOR_WHITE +
                "- make a move\n" +

                // resign
                SET_TEXT_COLOR_BLUE +
                "\tresign " +
                SET_TEXT_COLOR_WHITE +
                "- forfeit the game\n" +

                // highlight
                SET_TEXT_COLOR_BLUE +
                "\thighlight " +
                SET_TEXT_COLOR_WHITE +
                "- highlight the possible moves\n" +

                // help
                SET_TEXT_COLOR_BLUE +
                "\thelp " +
                SET_TEXT_COLOR_WHITE +
                "- print possible commands";
    }

}
