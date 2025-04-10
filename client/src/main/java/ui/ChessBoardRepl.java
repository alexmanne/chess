package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.HashMap;

import static ui.EscapeSequences.*;

public class ChessBoardRepl {

    private ChessGame game;
    private ChessGame.TeamColor playerColor;
    private HashMap<ChessPiece, String> converter = new HashMap<>();

    public ChessBoardRepl(ChessGame game, ChessGame.TeamColor playerColor) {
        this.game = game;
        this.playerColor = playerColor;
        this.converter.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN),
                SET_TEXT_COLOR_BLACK + BLACK_PAWN);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK),
                SET_TEXT_COLOR_BLACK + BLACK_ROOK);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT),
                SET_TEXT_COLOR_BLACK + BLACK_KNIGHT);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP),
                SET_TEXT_COLOR_BLACK + BLACK_BISHOP);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN),
                SET_TEXT_COLOR_BLACK + BLACK_QUEEN);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING),
                SET_TEXT_COLOR_BLACK + BLACK_KING);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN),
                SET_TEXT_COLOR_WHITE + WHITE_PAWN);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK),
                SET_TEXT_COLOR_WHITE + WHITE_ROOK);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT),
                SET_TEXT_COLOR_WHITE + WHITE_KNIGHT);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP),
                SET_TEXT_COLOR_WHITE + WHITE_BISHOP);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN),
                SET_TEXT_COLOR_WHITE + WHITE_QUEEN);
        this.converter.put(new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING),
                SET_TEXT_COLOR_WHITE + WHITE_KING);
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    public String drawBoard() {
        if (playerColor == ChessGame.TeamColor.WHITE) {
            return drawWhiteBoard();
        } else if (playerColor == ChessGame.TeamColor.BLACK) {
            return drawBlackBoard();
        } else {
            return drawWhiteBoard();
        }
    }

    private String drawWhiteBoard() {
        StringBuilder builder = new StringBuilder();
        ChessBoard board = game.getBoard();
        builder.append(drawWhiteBoardHeader());
        for (int row = 8; row > 0; row--) {
            ArrayList<String> nextRow = new ArrayList<>();
            for (int col = 1; col < 9; col++) {
                ChessPosition position = new ChessPosition(row, col);
                nextRow.add(converter.get(board.getPiece(position)));
            }
            if (row % 2 == 0) {
                builder.append(drawBlueRow(row, nextRow));
            } else {
                builder.append(drawGreenRow(row, nextRow));
            }
        }
        builder.append(drawWhiteBoardHeader());
        return builder.toString();
    }

    private String drawBlackBoard() {
        StringBuilder builder = new StringBuilder();
        ChessBoard board = game.getBoard();
        builder.append(drawWhiteBoardHeader());
        for (int row = 1; row < 9; row++) {
            ArrayList<String> nextRow = new ArrayList<>();
            for (int col = 8; col > 0; col--) {
                ChessPosition position = new ChessPosition(row, col);
                nextRow.add(converter.get(board.getPiece(position)));
            }
            if (row % 2 == 0) {
                builder.append(drawGreenRow(row, nextRow));
            } else {
                builder.append(drawBlueRow(row, nextRow));
            }
        }
        builder.append(drawWhiteBoardHeader());
        return builder.toString();
    }


    public String highlight(ChessPosition chessPosition) {
        return null;
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

    private static String drawWhiteBoardHeader() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                EMPTY + " a  b  c  d  e  f  g  h " + EMPTY +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardHeader() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                EMPTY + " h  g  f  e  d  c  b  a " + EMPTY +
                RESET_BG_COLOR;
    }
}
