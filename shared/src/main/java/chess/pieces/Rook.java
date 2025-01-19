package chess.pieces;

import chess.*;

import java.util.Collection;

public class Rook extends ChessPiece {
    public Rook(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, PieceType.ROOK);
    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
