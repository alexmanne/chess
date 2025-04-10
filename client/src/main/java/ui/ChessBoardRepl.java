package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

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

    public String drawBoard() {
        return null;
    }

    public String highlight(ChessPosition chessPosition) {
        return null;
    }
}
