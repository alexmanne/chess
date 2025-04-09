package ui.client;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import exception.DataAccessException;
import ui.EscapeSequences;
import ui.ServerFacade;
import ui.Repl;
import ui.State;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class GamePlayClient {

    private WebSocketFacade ws;
    private static ArrayList<String> emptyRow;
    private static ArrayList<String> whitePawnRow;
    private static ArrayList<String> blackPawnRow;

    public GamePlayClient(ServerFacade server, ServerMessageObserver observer, int port) throws DataAccessException {
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
        return null;
    }

    private String leave(Repl repl) throws DataAccessException {
        ws.leave(repl.authToken, repl.gameID);
        repl.authToken = null;
        repl.gameID = 0;
        repl.state = State.LOGGEDIN;
        repl.game = null;
        return String.format("%s left the game", repl.username);
    }

    private String move(Repl repl, String[] params) throws DataAccessException {
        String errorMessage = "Expected: <STARTING POSITION> <ENDING POSITION>. Example:\nd2 d4";
        if (params.length >= 1) {
            try {
                ChessPosition startPosition = serializePosition(params[0]);
                ChessPosition endPosition = serializePosition(params[1]);
                ChessMove chessMove = new ChessMove(startPosition, endPosition);
                ws.makeMove(repl.authToken, repl.gameID, chessMove);
                return String.format("Made move: %s -> %s", params[0], params[1]);
            } catch (IndexOutOfBoundsException ex) {
                throw new DataAccessException(400, errorMessage);
            }
        }
        throw new DataAccessException(400, errorMessage);
    }

    private String resign(Repl repl) throws DataAccessException {
        ws.resign(repl.authToken, repl.gameID);
        repl.authToken = null;
        repl.gameID = 0;
        repl.state = State.LOGGEDIN;
        repl.game = null;
        return String.format("%s resigned from the game", repl.username);
    }

    private String highlight(Repl repl, String[] params) throws DataAccessException {
        return null;
    }

    private ChessPosition serializePosition(String stringPosition) throws DataAccessException {
        String errorMessage = "Expected position is a letter than a number. Example:\nd2";
        ArrayList<String> viableLetters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        ArrayList<String> viableNumbers = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8"));

        if (stringPosition.length() != 2) {
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

    public String drawBlackBoard () {
        StringBuilder builder = new StringBuilder();



        return builder.toString();
    }

    public String drawWhiteBoard () {
        StringBuilder builder = new StringBuilder();



        return builder.toString();
    }

    // Creates the string to print a new board to the screen from white perspective
    public static String drawNewWhiteBoard() {
        StringBuilder builder = new StringBuilder();

        drawRows();

        builder.append(drawWhiteBoardHeader()).append("\n");
        builder.append(drawWhiteBoardBlackPieces()).append("\n");
        builder.append(drawGreenRow(7, blackPawnRow)).append("\n");
        builder.append(drawBlueRow(6, emptyRow)).append("\n");
        builder.append(drawGreenRow(5, emptyRow)).append("\n");
        builder.append(drawBlueRow(4, emptyRow)).append("\n");
        builder.append(drawGreenRow(3, emptyRow)).append("\n");
        builder.append(drawBlueRow(2, whitePawnRow)).append("\n");
        builder.append(drawWhiteBoardWhitePieces()).append("\n");
        builder.append(drawWhiteBoardHeader()).append("\n");

        return builder.toString();
    }

    // Creates the string to print a new board to the screen from black perspective
    public static String drawNewBlackBoard() {
        StringBuilder builder = new StringBuilder();

        drawRows();

        builder.append(drawBlackBoardHeader()).append("\n");
        builder.append(drawBlackBoardWhitePieces()).append("\n");
        builder.append(drawGreenRow(2, whitePawnRow)).append("\n");
        builder.append(drawBlueRow(3, emptyRow)).append("\n");
        builder.append(drawGreenRow(4, emptyRow)).append("\n");
        builder.append(drawBlueRow(5, emptyRow)).append("\n");
        builder.append(drawGreenRow(6, emptyRow)).append("\n");
        builder.append(drawBlueRow(7, blackPawnRow)).append("\n");
        builder.append(drawBlackBoardBlackPieces()).append("\n");
        builder.append(drawBlackBoardHeader()).append("\n");

        return builder.toString();
    }

    private static void drawRows() {
        emptyRow = new ArrayList<>();
        blackPawnRow = new ArrayList<>();
        whitePawnRow = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            emptyRow.add(EMPTY);
            blackPawnRow.add(BLACK_PAWN);
            whitePawnRow.add(WHITE_PAWN);
        }
    }

    private static String drawBlueRow(int rowNum, ArrayList<String> fill) {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                SET_BG_COLOR_BLUE + fill.get(0) +
                SET_BG_COLOR_DARK_GREEN + fill.get(1) +
                SET_BG_COLOR_BLUE + fill.get(2) +
                SET_BG_COLOR_DARK_GREEN + fill.get(3) +
                SET_BG_COLOR_BLUE + fill.get(4) +
                SET_BG_COLOR_DARK_GREEN + fill.get(5) +
                SET_BG_COLOR_BLUE + fill.get(6) +
                SET_BG_COLOR_DARK_GREEN + fill.get(7) +
                SET_BG_COLOR_DARK_GREY+ SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                RESET_BG_COLOR;
    }

    private static String drawGreenRow(int rowNum, ArrayList<String> fill) {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                SET_BG_COLOR_DARK_GREEN + fill.get(0) +
                SET_BG_COLOR_BLUE + fill.get(1) +
                SET_BG_COLOR_DARK_GREEN + fill.get(2) +
                SET_BG_COLOR_BLUE + fill.get(3) +
                SET_BG_COLOR_DARK_GREEN + fill.get(4) +
                SET_BG_COLOR_BLUE + fill.get(5) +
                SET_BG_COLOR_DARK_GREEN + fill.get(6) +
                SET_BG_COLOR_BLUE + fill.get(7) +
                SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                RESET_BG_COLOR;
    }

    private static String drawWhiteBoardWhitePieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 " +
                SET_BG_COLOR_DARK_GREEN + WHITE_ROOK +
                SET_BG_COLOR_BLUE + WHITE_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + WHITE_BISHOP +
                SET_BG_COLOR_BLUE + WHITE_QUEEN +
                SET_BG_COLOR_DARK_GREEN + WHITE_KING +
                SET_BG_COLOR_BLUE + WHITE_BISHOP +
                SET_BG_COLOR_DARK_GREEN + WHITE_KNIGHT +
                SET_BG_COLOR_BLUE + WHITE_ROOK +
                SET_BG_COLOR_DARK_GREY + " 1 " +
                RESET_BG_COLOR;
    }

    private static String drawWhiteBoardBlackPieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                SET_BG_COLOR_BLUE + SET_TEXT_COLOR_BLACK + BLACK_ROOK +
                SET_BG_COLOR_DARK_GREEN + BLACK_KNIGHT +
                SET_BG_COLOR_BLUE + BLACK_BISHOP +
                SET_BG_COLOR_DARK_GREEN + BLACK_QUEEN +
                SET_BG_COLOR_BLUE + BLACK_KING +
                SET_BG_COLOR_DARK_GREEN + BLACK_BISHOP +
                SET_BG_COLOR_BLUE + BLACK_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + BLACK_ROOK +
                SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                RESET_BG_COLOR;
    }

    private static String drawWhiteBoardHeader() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                EMPTY + " a  b  c  d  e  f  g  h " + EMPTY +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardWhitePieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 " +
                SET_BG_COLOR_BLUE + WHITE_ROOK +
                SET_BG_COLOR_DARK_GREEN + WHITE_KNIGHT +
                SET_BG_COLOR_BLUE + WHITE_BISHOP +
                SET_BG_COLOR_DARK_GREEN + WHITE_QUEEN +
                SET_BG_COLOR_BLUE + WHITE_KING +
                SET_BG_COLOR_DARK_GREEN + WHITE_BISHOP +
                SET_BG_COLOR_BLUE + WHITE_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + WHITE_ROOK +
                SET_BG_COLOR_DARK_GREY + " 1 " +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardBlackPieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_BLACK + BLACK_ROOK +
                SET_BG_COLOR_BLUE + BLACK_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + BLACK_BISHOP +
                SET_BG_COLOR_BLUE + BLACK_QUEEN +
                SET_BG_COLOR_DARK_GREEN + BLACK_KING +
                SET_BG_COLOR_BLUE + BLACK_BISHOP +
                SET_BG_COLOR_DARK_GREEN + BLACK_KNIGHT +
                SET_BG_COLOR_BLUE + BLACK_ROOK +
                SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardHeader() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                EMPTY + " h  g  f  e  d  c  b  a " + EMPTY +
                RESET_BG_COLOR;
    }

}
