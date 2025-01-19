package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bishop extends ChessPiece {
    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int my_row = myPosition.getRow() - 1;        // Minus to match 0 index
        int my_col = myPosition.getColumn() - 1;     // Minus to match 0 index
        ChessPiece[][] my_board = board.getBoard();
        ChessGame.TeamColor myColor = my_board[my_row][my_row].getTeamColor();

        // Check for diagonals right and up of my position
        for (int i = 0; i < 8; i++){
            if ((my_row + i < 8) && (my_col + 1 < 8)) {
                if (my_board[my_row + i][my_col + i] == null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row + i, my_col + i));
                    movesAvailable.add(newMove);
                } else if (my_board[my_row + i][my_col + i].getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row + i, my_col + i));
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        // Check for diagonals right and down of my position
        for (int i = 0; i < 8; i++){
            if ((my_row - i >= 0) && (my_col + 1 < 8)) {
                if (my_board[my_row - i][my_col + i] == null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row - i, my_col + i));
                    movesAvailable.add(newMove);
                } else if (my_board[my_row - i][my_col + i].getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row - i, my_col + i));
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        // Check for diagonals left and down of my position
        for (int i = 0; i < 8; i++){
            if ((my_row - i >= 0) && (my_col - 1 >= 0)) {
                if (my_board[my_row - i][my_col - i] == null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row - i, my_col - i));
                    movesAvailable.add(newMove);
                } else if (my_board[my_row - i][my_col - i].getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row - i, my_col - i));
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        // Check for diagonals left and up of my position
        for (int i = 0; i < 8; i++){
            if ((my_row + i < 8) && (my_col - 1 >= 0)) {
                if (my_board[my_row + i][my_col - i] == null) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row + i, my_col - i));
                    movesAvailable.add(newMove);
                } else if (my_board[my_row + i][my_col - i].getTeamColor() != myColor) {
                    ChessMove newMove = new ChessMove(myPosition, new
                            ChessPosition(my_row + i, my_col - i));
                    movesAvailable.add(newMove);
                    break;
                } else break;
            } else break;
        }

        return movesAvailable;
    }
}
