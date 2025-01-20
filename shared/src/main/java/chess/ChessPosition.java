package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int row;
    private int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * Set which row this position is in
     * 1 codes for the bottom row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }

    /**
     * Set which column this position is in
     * 1 codes for the left row
     */
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", col, row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
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
        ChessPosition p = (ChessPosition) obj;

        // return true if the row and column match
        return (this.row == p.row && this.col == p.col);
    }

}
