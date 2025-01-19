package chess.pieces;

import chess.*;

import java.util.Collection;

public class Bishop extends ChessPiece {
    public Bishop(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, PieceType.BISHOP);
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
