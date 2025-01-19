package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends ChessPiece {
    public Rook(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int my_row = myPosition.getRow() - 1;        // Minus to match 0 index
        int my_col = myPosition.getColumn() - 1;     // Minus to match 0 index
        ChessPiece[][] my_board = board.getBoard();
        ChessGame.TeamColor myColor = my_board[my_row][my_row].getTeamColor();

        // Check for rows above my position
        for (int i = my_row; i < 8; i++){
            if (my_board[i][my_col] == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i + 1, my_col + 1));
                movesAvailable.add(newMove);
            } else if (my_board[i][my_col].getTeamColor() != myColor){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i + 1, my_col + 1));
                movesAvailable.add(newMove);
                break;
            } else break;
        }

        // Check for rows below my position
        for (int i = my_row; i >= 0; i--){
            if (my_board[i][my_col] == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i + 1, my_col + 1));
                movesAvailable.add(newMove);
            } else if (my_board[i][my_col].getTeamColor() != myColor) {
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i + 1, my_col + 1));
                movesAvailable.add(newMove);
                break;
            } else break;
        }

        // Check for cols right of my position
        for (int i = my_col; i < 8; i++){
            if (my_board[my_row][i] == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, i + 1));
                movesAvailable.add(newMove);
            } else if (my_board[my_row][i].getTeamColor() != myColor) {
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, i + 1));
                movesAvailable.add(newMove);
                break;
            } else break;
        }

        // Check for cols left of my position
        for (int i = my_col; i >= 0; i--){
            if (my_board[my_row][i] == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, i + 1));
                movesAvailable.add(newMove);
            } else if (my_board[my_row][i].getTeamColor() != myColor) {
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(my_row + 1, i + 1));
                movesAvailable.add(newMove);
                break;
            } else break;
        }
        return movesAvailable;
    }
}
