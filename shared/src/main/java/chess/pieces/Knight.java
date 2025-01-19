package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends ChessPiece {
    public Knight(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }

    public static Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int my_row = myPosition.getRow() - 1;        // Minus to match 0 index
        int my_col = myPosition.getColumn() - 1;     // Minus to match 0 index
        ChessPiece[][] my_board = board.getBoard();
        ChessGame.TeamColor myColor = my_board[my_row][my_row].getTeamColor();

        // Check right 1, up 2 and down 2
        if (my_col + 1 < 8){
            if (my_row + 2 < 8 && (my_board[my_row + 2][my_col + 1] == null
                || my_board[my_row + 2][my_col + 1].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 2, my_col + 1));
                movesAvailable.add(newMove);
            }
            if (my_row - 2 >= 0 && (my_board[my_row - 2][my_col + 1] == null
                || my_board[my_row - 2][my_col + 1].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row - 2, my_col + 1));
                movesAvailable.add(newMove);
            }
        }

        // Check right 2, up 1 and down 1
        if (my_col + 2 < 8){
            if (my_row + 1 < 8 && (my_board[my_row + 1][my_col + 2] == null
                || my_board[my_row + 1][my_col + 2].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, my_col + 2));
                movesAvailable.add(newMove);
            }
            if (my_row - 1 >= 0 && (my_board[my_row - 1][my_col + 2] == null
                || my_board[my_row - 1][my_col + 2].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row - 1, my_col + 2));
                movesAvailable.add(newMove);
            }
        }

        // Check left 1, up 2 and down 2
        if (my_col - 1 >= 0){
            if (my_row + 2 < 8 && (my_board[my_row + 2][my_col - 1] == null
                || my_board[my_row + 2][my_col - 1].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 2, my_col - 1));
                movesAvailable.add(newMove);
            }
            if (my_row - 2 >= 0 && (my_board[my_row - 2][my_col - 1] == null
                || my_board[my_row - 2][my_col - 1].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row - 2, my_col - 1));
                movesAvailable.add(newMove);
            }
        }

        // Check left 2, up 1 and down 1
        if (my_col - 2 >= 0){
            if (my_row + 1 < 8 && (my_board[my_row + 1][my_col - 2] == null
                || my_board[my_row + 1][my_col - 2].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, my_col - 2));
                movesAvailable.add(newMove);
            }
            if (my_row - 1 >= 0 && (my_board[my_row - 1][my_col - 2] == null
                || my_board[my_row - 1][my_col - 2].getTeamColor() != myColor)){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row - 1, my_col - 2));
                movesAvailable.add(newMove);
            }
        }

        return movesAvailable;
    }
}
