package chess;

import chess.pieces.*;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    /**
     * Create a new chessboard with the BLACK team color on top
     * and WHITE team color on bottom.
     * <p>
     * Note: The structure of the indexing will follow the
     * structure of a chess board. (0,0) will be in the bottom
     * left and (7,7) will be in the top right.
     */
    public ChessBoard() {
        board = new ChessPiece[8][8];
        board[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        board[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        board[0][4] = new King(ChessGame.TeamColor.WHITE);
        board[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][7] = new Rook(ChessGame.TeamColor.WHITE);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }

        board[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        board[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][3] = new Queen(ChessGame.TeamColor.BLACK);
        board[7][4] = new King(ChessGame.TeamColor.BLACK);
        board[7][5] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][6] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][7] = new Rook(ChessGame.TeamColor.BLACK);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow() - 1;      // minus one to match 0 index
        int col = position.getColumn() - 1;   // minus one to match 0 index
        board[row][col] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow() - 1;      // minus one to match 0 index
        int col = position.getColumn() - 1;   // minus one to match 0 index
        return board[row][col];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        board = new ChessPiece[8][8];
        board[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        board[0][1] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][2] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][3] = new Queen(ChessGame.TeamColor.WHITE);
        board[0][4] = new King(ChessGame.TeamColor.WHITE);
        board[0][5] = new Bishop(ChessGame.TeamColor.WHITE);
        board[0][6] = new Knight(ChessGame.TeamColor.WHITE);
        board[0][7] = new Rook(ChessGame.TeamColor.WHITE);

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(ChessGame.TeamColor.WHITE);
        }

        board[7][0] = new Rook(ChessGame.TeamColor.BLACK);
        board[7][1] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][2] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][3] = new Queen(ChessGame.TeamColor.BLACK);
        board[7][4] = new King(ChessGame.TeamColor.BLACK);
        board[7][5] = new Bishop(ChessGame.TeamColor.BLACK);
        board[7][6] = new Knight(ChessGame.TeamColor.BLACK);
        board[7][7] = new Rook(ChessGame.TeamColor.BLACK);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(ChessGame.TeamColor.BLACK);
        }
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        ChessBoard b = (ChessBoard) obj;
        ChessPiece[][] bA = b.board;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (!board[i][j].equals(bA[i][j])) return false;
            }
        } return true;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(board);
    }
}
