package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King {

    public King() {    }

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();

        if (myRow != 1) {
            ChessPosition downOne = new ChessPosition(myRow - 1, myCol);
            if (board.getPiece(downOne) == null
                    || board.getPiece(downOne).getTeamColor() != myColor) {
                ChessMove down = new ChessMove(myPosition, downOne);
                movesAvailable.add(down);
            }


            if (myCol != 1) {
                ChessPosition downLeft = new ChessPosition(myRow - 1, myCol - 1);
                if (board.getPiece(downLeft) == null
                        || board.getPiece(downLeft).getTeamColor() != myColor) {
                    ChessMove leftDown = new ChessMove(myPosition, downLeft);
                    movesAvailable.add(leftDown);
                }
            }

            if (myCol != 8) {
                ChessPosition downRight = new ChessPosition(myRow - 1, myCol + 1);
                if (board.getPiece(downRight) == null
                        || board.getPiece(downRight).getTeamColor() != myColor) {
                    ChessMove rightDown = new ChessMove(myPosition, downRight);
                    movesAvailable.add(rightDown);
                }
            }
        }

        if (myRow != 8) {
            ChessPosition upOne = new ChessPosition(myRow + 1, myCol);
            if (board.getPiece(upOne) == null
                    || board.getPiece(upOne).getTeamColor() != myColor) {
                ChessMove up = new ChessMove(myPosition, upOne);
                movesAvailable.add(up);
            }


            if (myCol != 1) {
                ChessPosition upLeft = new ChessPosition(myRow + 1, myCol - 1);
                if (board.getPiece(upLeft) == null
                        || board.getPiece(upLeft).getTeamColor() != myColor) {
                    ChessMove leftUp = new ChessMove(myPosition, upLeft);
                    movesAvailable.add(leftUp);
                }
            }

            if (myCol != 8) {
                ChessPosition upRight = new ChessPosition(myRow + 1, myCol + 1);
                if (board.getPiece(upRight) == null
                        || board.getPiece(upRight).getTeamColor() != myColor) {
                    ChessMove rightUp = new ChessMove(myPosition, upRight);
                    movesAvailable.add(rightUp);
                }
            }
        }

        if (myCol != 1) {
            ChessPosition leftOne = new ChessPosition(myRow, myCol - 1);
            if (board.getPiece(leftOne) == null
                || board.getPiece(leftOne).getTeamColor() != myColor){
                ChessMove left = new ChessMove(myPosition, leftOne);
                movesAvailable.add(left);
            }
        }

        if (myCol != 8) {
            ChessPosition rightOne = new ChessPosition(myRow, myCol + 1);
            if (board.getPiece(rightOne) == null
                    || board.getPiece(rightOne).getTeamColor() != myColor){
                ChessMove right = new ChessMove(myPosition, rightOne);
                movesAvailable.add(right);
            }
        }

        return movesAvailable;
    }
}
