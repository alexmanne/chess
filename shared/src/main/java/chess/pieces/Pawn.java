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
                    if (my_row - 1 == 0) {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 1), PieceType.QUEEN);
                        movesAvailable.add(newMove);
                        ChessMove newMove1 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 1), PieceType.BISHOP);
                        movesAvailable.add(newMove1);
                        ChessMove newMove2 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 1), PieceType.ROOK);
                        movesAvailable.add(newMove2);
                        ChessMove newMove3 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 1), PieceType.KNIGHT);
                        movesAvailable.add(newMove3);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(my_row, my_col + 1));
                        movesAvailable.add(newMove);
                    }
                }
                // If the diagonals have an opposing team
                if (my_col - 1 >= 0 && my_board[my_row - 1][my_col - 1] != null
                    && my_board[my_row - 1][my_col - 1].getTeamColor() != myColor){
                    if (my_row - 1 == 0) {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(1, my_col), PieceType.QUEEN);
                        movesAvailable.add(newMove);
                        ChessMove newMove1 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col), PieceType.BISHOP);
                        movesAvailable.add(newMove1);
                        ChessMove newMove2 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col), PieceType.ROOK);
                        movesAvailable.add(newMove2);
                        ChessMove newMove3 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col), PieceType.KNIGHT);
                        movesAvailable.add(newMove3);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(my_row, my_col));
                        movesAvailable.add(newMove);
                    }
                }
                if (my_col + 1 < 8 && my_board[my_row - 1][my_col + 1] != null
                        && my_board[my_row - 1][my_col + 1].getTeamColor() != myColor){
                    if (my_row - 1 == 0) {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 2), PieceType.QUEEN);
                        movesAvailable.add(newMove);
                        ChessMove newMove1 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 2), PieceType.BISHOP);
                        movesAvailable.add(newMove1);
                        ChessMove newMove2 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 2), PieceType.ROOK);
                        movesAvailable.add(newMove2);
                        ChessMove newMove3 = new ChessMove(myPosition, new
                                ChessPosition(1, my_col + 2), PieceType.KNIGHT);
                        movesAvailable.add(newMove3);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(my_row, my_col + 2));
                        movesAvailable.add(newMove);
                    }
                }
                // If the pawn is in the original spot with two empty spaces
                if (my_row == 6 && my_board[my_row - 1][my_col] != null
                    && my_board[my_row - 2][my_col] != null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(5, my_col + 1));
                    movesAvailable.add(newMove);
                }

            }
        }

        // If pieceColor is WHITE, it can only move up
        if (myColor.equals(ChessGame.TeamColor.WHITE)){
            if (my_row + 1 < 8) {
                // If it is empty in front
                if (my_board[my_row + 1][my_col] == null){
                    if (my_row + 1 == 7) {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 1), PieceType.QUEEN);
                        movesAvailable.add(newMove);
                        ChessMove newMove1 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 1), PieceType.BISHOP);
                        movesAvailable.add(newMove1);
                        ChessMove newMove2 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 1), PieceType.ROOK);
                        movesAvailable.add(newMove2);
                        ChessMove newMove3 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 1), PieceType.KNIGHT);
                        movesAvailable.add(newMove3);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(my_row + 2, my_col + 1));
                        movesAvailable.add(newMove);
                    }
                }
                // If the diagonals have an opposing team
                if (my_col - 1 >= 0 && my_board[my_row + 1][my_col - 1] != null
                        && my_board[my_row + 1][my_col - 1].getTeamColor() != myColor){
                    if (my_row + 1 == 7) {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(8, my_col), PieceType.QUEEN);
                        movesAvailable.add(newMove);
                        ChessMove newMove1 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col), PieceType.BISHOP);
                        movesAvailable.add(newMove1);
                        ChessMove newMove2 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col), PieceType.ROOK);
                        movesAvailable.add(newMove2);
                        ChessMove newMove3 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col), PieceType.KNIGHT);
                        movesAvailable.add(newMove3);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(my_row + 2, my_col));
                        movesAvailable.add(newMove);
                    }
                }
                if (my_col + 1 < 8 && my_board[my_row + 1][my_col + 1] != null
                        && my_board[my_row + 1][my_col + 1].getTeamColor() != myColor){
                    if (my_row + 1 == 7) {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 2), PieceType.QUEEN);
                        movesAvailable.add(newMove);
                        ChessMove newMove1 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 2), PieceType.BISHOP);
                        movesAvailable.add(newMove1);
                        ChessMove newMove2 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 2), PieceType.ROOK);
                        movesAvailable.add(newMove2);
                        ChessMove newMove3 = new ChessMove(myPosition, new
                                ChessPosition(8, my_col + 2), PieceType.KNIGHT);
                        movesAvailable.add(newMove3);
                    } else {
                        ChessMove newMove = new ChessMove(myPosition, new
                                ChessPosition(my_row + 2, my_col + 2));
                        movesAvailable.add(newMove);
                    }
                }
                // If the pawn is in the original spot with two empty spaces
                if (my_row == 1 && my_board[my_row + 1][my_col] != null
                        && my_board[my_row + 2][my_col] != null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(4, my_col + 1));
                    movesAvailable.add(newMove);
                }
            }
        }
        return movesAvailable;
    }
}
