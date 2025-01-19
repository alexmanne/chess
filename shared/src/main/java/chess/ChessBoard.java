package chess;

import chess.pieces.*;

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
        board[0][1] = new Knight()
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }
}
