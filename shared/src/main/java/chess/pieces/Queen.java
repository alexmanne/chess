package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen {

    public Queen() {    }

    public static Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> bishopMoves = new ArrayList<>(Bishop.bishopMoves(board, myPosition));
        List<ChessMove> rookMoves = new ArrayList<>(Rook.rookMoves(board, myPosition));
        bishopMoves.addAll(rookMoves);
        return bishopMoves;
    }
}
