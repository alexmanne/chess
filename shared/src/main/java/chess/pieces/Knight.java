package chess.pieces;

import chess.*;

import java.util.Collection;

public class Knight extends ChessPiece {
    public Knight(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, PieceType.KNIGHT);
    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
