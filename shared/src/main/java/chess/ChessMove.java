package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition startPosition;
    private ChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition) {
        this.setStartPosition(startPosition);
        this.setEndPosition(endPosition);
        this.setPromotionPiece(null);
    }

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this(startPosition, endPosition);
        this.setPromotionPiece(promotionPiece);
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * Set ChessPosition of starting location
     */
    public void setStartPosition(ChessPosition startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Set ChessPosition of ending location
     */
    public void setEndPosition(ChessPosition endPosition) {
        this.endPosition = endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    public void setPromotionPiece(ChessPiece.PieceType promotionPiece) {
        this.promotionPiece = promotionPiece;
    }

    @Override
    public int hashCode() {
        if (promotionPiece == null) {
            return (startPosition.hashCode() * endPosition.hashCode());
        } else {
            return (startPosition.hashCode() * endPosition.hashCode()) ^ promotionPiece.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        ChessMove m = (ChessMove) obj;

        // return true if the row and column match
        return (endPosition.equals(m.endPosition) &&
                startPosition.equals(m.startPosition) &&
                promotionPiece == m.promotionPiece);
    }

    @Override
    public String toString() {
        return "Start " + startPosition +
                " End " + endPosition +
                " Promotion Piece: " + promotionPiece;
    }
}
