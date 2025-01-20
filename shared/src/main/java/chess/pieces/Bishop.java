package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop {

    public Bishop() {    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();

        // Check for diagonals right and up of my position
        for (int i = 1; i < 9; i++){
            if ((myRow + i < 9) && (myCol + i < 9)) {
                if (board.getPiece(new ChessPosition(myRow + i, myCol + i)) == null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(myRow + i, myCol + i));
                    movesAvailable.add(newMove);
                } else if (board.getPiece(new ChessPosition(myRow + i, myCol + i))
                                .getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(myRow + i, myCol + i));
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        // Check for diagonals right and down of my position
        for (int i = 1; i < 9; i++){
            if ((myRow - i > 0) && (myCol + i < 9)) {
                ChessPosition thatPosition = new ChessPosition(myRow - i, myCol + i);
                if (board.getPiece(thatPosition) == null) {
                    ChessMove newMove = new ChessMove(myPosition, thatPosition);
                    movesAvailable.add(newMove);
                } else if (board.getPiece(thatPosition).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPosition);
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        // Check for diagonals left and down of my position
        for (int i = 1; i < 9; i++){
            if ((myRow - i > 0) && (myCol - i > 0)) {
                ChessPosition thatPosition = new ChessPosition(myRow - i, myCol - i);
                if (board.getPiece(thatPosition) == null) {
                    ChessMove newMove = new ChessMove(myPosition, thatPosition);
                    movesAvailable.add(newMove);
                } else if (board.getPiece(thatPosition).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPosition);
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        // Check for diagonals left and up of my position
        for (int i = 1; i < 9; i++){
            if ((myRow + i < 9) && (myCol - i > 0)) {
                ChessPosition thatPosition = new ChessPosition(myRow + i, myCol - i);
                if (board.getPiece(thatPosition) == null) {
                    ChessMove newMove = new ChessMove(myPosition, thatPosition);
                    movesAvailable.add(newMove);
                } else if (board.getPiece(thatPosition).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPosition);
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        return movesAvailable;
    }
}
