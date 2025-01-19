package chess;

import chess.pieces.*;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.setPieceColor(pieceColor);
        this.setPieceType(type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    public void setPieceColor(ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (pieceType) {
            case KING -> King.kingMoves(board, myPosition);
            case QUEEN -> Queen.queenMoves(board, myPosition);
            case BISHOP -> Bishop.bishopMoves(board, myPosition);
            case KNIGHT -> Knight.knightMoves(board, myPosition);
            case ROOK -> Rook.rookMoves(board, myPosition);
            case PAWN -> Pawn.pawnMoves(board, myPosition);
        };
    }

    @Override
    public int hashCode() {
        return pieceType.hashCode() * pieceColor.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        ChessPiece p = (ChessPiece) obj;
        return (this.pieceColor == p.pieceColor && this.pieceType == p.pieceType);
    }

    @Override
    public String toString() {
        return (this.pieceColor.toString() + this.pieceType.toString());
    }
}
