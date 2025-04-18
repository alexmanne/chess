package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn {

    public Pawn() {   }

    public static void addWithPromotion(List<ChessMove> movesAvailable, ChessPosition startPosition,
                                        ChessPosition endPosition) {
        ChessMove newMove = new ChessMove(startPosition, endPosition,
                ChessPiece.PieceType.QUEEN);
        movesAvailable.add(newMove);
        ChessMove newMove1 = new ChessMove(startPosition, endPosition,
                ChessPiece.PieceType.BISHOP);
        movesAvailable.add(newMove1);
        ChessMove newMove2 = new ChessMove(startPosition, endPosition,
                ChessPiece.PieceType.ROOK);
        movesAvailable.add(newMove2);
        ChessMove newMove3 = new ChessMove(startPosition, endPosition,
                ChessPiece.PieceType.KNIGHT);
        movesAvailable.add(newMove3);
    }

    public static Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();
        // If pieceColor is BLACK, it can only move down
        if (myColor == ChessGame.TeamColor.BLACK && myRow - 1 > 0) {
            ChessPosition front = new ChessPosition(myRow - 1, myCol);
            // If it is empty in front
            if (board.getPiece(front) == null) {
                if (myRow - 1 == 1) {
                    addWithPromotion(movesAvailable, myPosition, front);
                } else {
                    ChessMove newMove = new ChessMove(myPosition, front);
                    movesAvailable.add(newMove);
                }
            }
            // If the pawn is in the original spot with two empty spaces
            if (myRow == 7) {
                ChessPosition front2 = new ChessPosition(myRow - 2, myCol);
                if (board.getPiece(front) == null && board.getPiece(front2) == null) {
                    ChessMove newMove = new ChessMove(myPosition, front2);
                    movesAvailable.add(newMove);
                }
            }
            // If the diagonals have an opposing team
            if (myCol - 1 > 0) {
                ChessPosition diagLeft = new ChessPosition(myRow - 1, myCol - 1);
                if (board.getPiece(diagLeft) != null
                        && board.getPiece(diagLeft).getTeamColor() != myColor) {
                    if (myRow - 1 == 1) {
                        addWithPromotion(movesAvailable, myPosition, diagLeft);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, diagLeft);
                        movesAvailable.add(newMove);
                    }
                }
            }
            if (myCol + 1 < 9) {
                ChessPosition diagRight = new ChessPosition(myRow - 1, myCol + 1);
                if (board.getPiece(diagRight) != null
                        && board.getPiece(diagRight).getTeamColor() != myColor) {
                    if (myRow - 1 == 1) {
                        addWithPromotion(movesAvailable, myPosition, diagRight);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, diagRight);
                        movesAvailable.add(newMove);
                    }
                }
            }
        }
        // If pieceColor is WHITE, it can only move up
        if (myColor == ChessGame.TeamColor.WHITE && myRow + 1 < 9) {
            ChessPosition front = new ChessPosition(myRow + 1, myCol);
            // If it is empty in front
            if (board.getPiece(front) == null) {
                if (myRow + 1 == 8) {
                    addWithPromotion(movesAvailable, myPosition, front);
                } else {
                    ChessMove newMove = new ChessMove(myPosition, front);
                    movesAvailable.add(newMove);
                }
            }
            // If the pawn is in the original spot with two empty spaces
            if (myRow == 2) {
                ChessPosition front2 = new ChessPosition(myRow + 2, myCol);
                if (board.getPiece(front) == null && board.getPiece(front2) == null) {
                    ChessMove newMove = new ChessMove(myPosition, front2);
                    movesAvailable.add(newMove);
                }
            }
            // If the diagonals have an opposing team
            if (myCol - 1 > 0) {
                ChessPosition diagLeft = new ChessPosition(myRow + 1, myCol - 1);
                if (board.getPiece(diagLeft) != null
                        && board.getPiece(diagLeft).getTeamColor() != myColor) {
                    if (myRow + 1 == 8) {
                        addWithPromotion(movesAvailable, myPosition, diagLeft);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, diagLeft);
                        movesAvailable.add(newMove);
                    }
                }
            }
            if (myCol + 1 < 9) {
                ChessPosition diagRight = new ChessPosition(myRow + 1, myCol + 1);
                if (board.getPiece(diagRight) != null
                        && board.getPiece(diagRight).getTeamColor() != myColor) {
                    if (myRow + 1 == 8) {
                        addWithPromotion(movesAvailable, myPosition, diagRight);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, diagRight);
                        movesAvailable.add(newMove);
                    }
                }
            }
        }
        return movesAvailable;
    }
}
