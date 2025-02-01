package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook {

    public Rook() {    }

    public static Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> movesAvailable = new ArrayList<>();
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();

        // Check for rows above my position
        for (int i = myRow + 1; i < 9; i++){
            if (board.getPiece(new ChessPosition(i, myCol)) == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i, myCol));
                movesAvailable.add(newMove);
            } else if (board.getPiece(new ChessPosition(i, myCol)).getTeamColor()
                            != myColor){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i, myCol));
                movesAvailable.add(newMove);
                break;
            } else {
                break;
            }
        }

        // Check for rows below my position
        for (int i = myRow - 1; i > 0; i--){
            if (board.getPiece(new ChessPosition(i, myCol)) == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i, myCol));
                movesAvailable.add(newMove);
            } else if (board.getPiece(new ChessPosition(i, myCol)).getTeamColor()
                            != myColor) {
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(i, myCol));
                movesAvailable.add(newMove);
                break;
            } else {
                break;
            }
        }

        // Check for cols right of my position
        for (int i = myCol + 1; i < 9; i++){
            if (board.getPiece(new ChessPosition(myRow, i)) == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(myRow, i));
                movesAvailable.add(newMove);
            } else if (board.getPiece(new ChessPosition(myRow, i)).getTeamColor()
                            != myColor) {
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(myRow, i));
                movesAvailable.add(newMove);
                break;
            } else {
                break;
            }
        }

        // Check for cols left of my position
        for (int i = myCol - 1; i > 0; i--){
            if (board.getPiece(new ChessPosition(myRow, i)) == null){
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(myRow, i));
                movesAvailable.add(newMove);
            } else if (board.getPiece(new ChessPosition(myRow, i)).getTeamColor()
                            != myColor) {
                ChessMove newMove = new ChessMove(myPosition, new
                        ChessPosition(myRow, i));
                movesAvailable.add(newMove);
                break;
            } else {
                break;
            }
        }
        return movesAvailable;
    }
}

