package chess.pieces;

import chess.*;

import java.util.Collection;

public class Pawn extends ChessPiece {
    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.PAWN);
    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
