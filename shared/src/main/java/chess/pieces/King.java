package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends ChessPiece {
    public King(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KING);
    }

    public static Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int my_row = myPosition.getRow() - 1;        // Minus to match 0 index
        int my_col = myPosition.getColumn() - 1;     // Minus to match 0 index
        ChessPiece[][] my_board = board.getBoard();
        ChessGame.TeamColor myColor = my_board[my_row][my_row].getTeamColor();

        if (my_row != 0){
            if (my_board[my_row - 1][my_col] == null
                    || my_board[my_row - 1][my_col].getTeamColor() != myColor) {
                ChessMove down = new ChessMove(myPosition, new
                        ChessPosition(my_row - 1, my_col));
                movesAvailable.add(down);

                if (my_col != 0 && (my_board[my_row - 1][my_col - 1] == null
                    || my_board[my_row - 1][my_col - 1].getTeamColor() != myColor)){
                    ChessMove leftDown = new ChessMove(myPosition, new
                            ChessPosition(my_row - 1, my_col - 1));
                    movesAvailable.add(leftDown);
                }

                if (my_col != 7 && (my_board[my_row - 1][my_col + 1] == null
                        || my_board[my_row - 1][my_col + 1].getTeamColor() != myColor)){
                    ChessMove rightDown = new ChessMove(myPosition, new
                            ChessPosition(my_row - 1, my_col + 1));
                    movesAvailable.add(rightDown);
                }
            }
        }

        if (my_row != 7){
            if (my_board[my_row + 1][my_col] == null
                    || my_board[my_row + 1][my_col].getTeamColor() != myColor) {
                ChessMove up = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, my_col));
                movesAvailable.add(up);

                if (my_col != 0 && (my_board[my_row + 1][my_col - 1] == null
                        || my_board[my_row + 1][my_col - 1].getTeamColor() != myColor)){
                    ChessMove leftUp = new ChessMove(myPosition, new
                            ChessPosition(my_row + 1, my_col - 1));
                    movesAvailable.add(leftUp);
                }

                if (my_col != 7 && (my_board[my_row + 1][my_col + 1] == null
                        || my_board[my_row + 1][my_col + 1].getTeamColor() != myColor)){
                    ChessMove rightUp = new ChessMove(myPosition, new
                            ChessPosition(my_row + 1, my_col + 1));
                    movesAvailable.add(rightUp);
                }
            }
        }

        if (my_col != 0 && (my_board[my_row][my_col - 1] == null
                || my_board[my_row][my_col - 1].getTeamColor() != myColor)){
            ChessMove left = new ChessMove(myPosition, new
                    ChessPosition(my_row, my_col - 1));
            movesAvailable.add(left);
        }

        if (my_col != 7 && (my_board[my_row][my_col + 1] == null
                || my_board[my_row][my_col + 1].getTeamColor() != myColor)){
            ChessMove right = new ChessMove(myPosition, new
                    ChessPosition(my_row, my_col + 1));
            movesAvailable.add(right);
        }

        return movesAvailable;
    }
}
