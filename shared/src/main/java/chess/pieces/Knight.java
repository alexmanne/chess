package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight {

    public Knight() {    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();


        // Check right 1, up 2 and down 2
        if (myCol + 1 < 9){
            if (myRow + 2 < 9) {
                ChessPosition thatPos = new ChessPosition(myRow + 2, myCol + 1);
                if (board.getPiece(thatPos) == null
                || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }

            if (myRow - 2 > 0) {
                ChessPosition thatPos = new ChessPosition(myRow - 2, myCol + 1);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }
        }

        // Check right 2, up 1 and down 1
        if (myCol + 2 < 9){
            if (myRow + 1 < 9) {
                ChessPosition thatPos = new ChessPosition(myRow + 1, myCol + 2);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }

            if (myRow - 1 > 0) {
                ChessPosition thatPos = new ChessPosition(myRow - 1, myCol + 2);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }
        }

        // Check left 1, up 2 and down 2
        if (myCol - 1 > 0){
            if (myRow + 2 < 9) {
                ChessPosition thatPos = new ChessPosition(myRow + 2, myCol - 1);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }

            if (myRow - 2 > 0) {
                ChessPosition thatPos = new ChessPosition(myRow - 2, myCol - 1);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }
        }

        // Check left 2, up 1 and down 1
        if (myCol - 2 > 0){
            if (myRow + 1 < 9) {
                ChessPosition thatPos = new ChessPosition(myRow + 1, myCol - 2);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }

            if (myRow - 1 > 0) {
                ChessPosition thatPos = new ChessPosition(myRow - 1, myCol - 2);
                if (board.getPiece(thatPos) == null
                        || board.getPiece(thatPos).getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, thatPos);
                    movesAvailable.add(newMove);
                }
            }
        }

        return movesAvailable;
    }
}
