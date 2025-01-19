package chess.pieces;

import chess.*;

import java.util.Collection;

public class Queen extends ChessPiece {
    public Queen(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.QUEEN);
    }

    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
