package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.PAWN);
    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int my_row = myPosition.getRow() - 1;        // Minus to match 0 index
        int my_col = myPosition.getColumn() - 1;     // Minus to match 0 index
        ChessPiece[][] my_board = board.getBoard();
        ChessPiece myPiece = my_board[my_row][my_col];
        ChessGame.TeamColor myColor = myPiece.getTeamColor();

        // If pieceColor is BLACK, it can only move down
        if (myColor.equals(ChessGame.TeamColor.BLACK)){
            if (my_row - 1 >= 0) {
                // If it is empty in front
                if (my_board[my_row - 1][my_col] == null){
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row - 1, my_col));
                    movesAvailable.add(newMove);
                }

            }
        }

        // If pieceColor is WHITE, it can only move up

        return movesAvailable;
    }
}
