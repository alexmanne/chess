package chess.pieces;

import chess.*;

import java.util.Collection;

public class King extends ChessPiece {
    public King(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, PieceType.KING);
    }

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
