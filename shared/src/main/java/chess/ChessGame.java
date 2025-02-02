package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard board;
    TeamColor teamTurn;

    public ChessGame() {
        setBoard(new ChessBoard());
        board.resetBoard();
        setTeamTurn(TeamColor.WHITE);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece myPiece = board.getPiece(startPosition);
        if (myPiece == null) {return null;}

        Collection<ChessMove> allMoves = myPiece.pieceMoves(board, startPosition);
        Collection<ChessMove> invalidMoves = new ArrayList<>();

        for (ChessMove move: allMoves) {
            ChessGame testGame = new ChessGame();
            testGame.setBoard(board);
            ChessPosition endPosition = move.getEndPosition();
            testGame.board.addPiece(endPosition, myPiece);
            testGame.board.addPiece(startPosition, null);
            if (testGame.isInCheck(teamTurn)) {
                invalidMoves.add(move);
            }
        }
        allMoves.removeAll(invalidMoves);
        return allMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);
        TeamColor otherTeamColor = TeamColor.BLACK;
        if (teamColor == TeamColor.BLACK) {otherTeamColor = TeamColor.WHITE;}

        Set<ChessPosition> otherTeamMoves = possibleTeamMoves(otherTeamColor);
        return otherTeamMoves.contains(kingPosition);
    }

    /**
     * Determines if the given king can move
     *
     * @param kingPosition The king's position to check for
     * @return True if the king cannot move
     */
    public boolean kingCannotMove(ChessPosition kingPosition) {

        // Need to check for pawns!!
        ChessPiece king = board.getPiece(kingPosition);
        TeamColor kingColor = king.getTeamColor();
        TeamColor otherTeamColor = TeamColor.BLACK;
        if (kingColor == TeamColor.BLACK) {otherTeamColor = TeamColor.WHITE;}
        Set<ChessPosition> otherTeamMoves = possibleTeamMoves(otherTeamColor);
        System.out.println(otherTeamMoves);
        Collection<ChessMove> possibleKingMoves = king.pieceMoves(board, kingPosition);
        for (ChessMove move : possibleKingMoves) {
            if (!otherTeamMoves.contains(move.getEndPosition())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {return false;}
        ChessPosition kingPosition = getKingPosition(teamColor);
        return kingCannotMove(kingPosition);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {return false;}
        ChessPosition kingPosition = getKingPosition(teamColor);
        return kingCannotMove(kingPosition);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     * Return a set possible positions for the team to move
     *
     * @param team the team color
     * @return A set of possible positions
     */
    public Set<ChessPosition> possibleTeamMoves (TeamColor team) {
        Set<ChessPosition> otherTeamMoves = new HashSet<>();
        for (int myRow = 1; myRow < 9; myRow++) {
            for (int myCol = 1; myCol < 9; myCol++) {
                ChessPosition curPosition = new ChessPosition(myRow, myCol);
                ChessPiece curPiece = board.getPiece(curPosition);
                if (curPiece == null) {
                    continue;
                }
                if (curPiece.getTeamColor() == team) {
                    Collection<ChessMove> moveList = curPiece.pieceMoves(board, curPosition);
                    for (ChessMove move: moveList) {
                        ChessPosition endPosition = move.getEndPosition();
                        otherTeamMoves.add(endPosition);
                    }
                }
            }
        }
        return otherTeamMoves;
    }

    /**
     * Return the position of the King for specified color
     *
     * @param team the team color
     * @return The position of the team's King
     */
    public ChessPosition getKingPosition(TeamColor team) {
        for (int myRow = 1; myRow < 9; myRow++) {
            for (int myCol = 1; myCol < 9; myCol++) {
                ChessPosition curPosition = new ChessPosition(myRow, myCol);
                ChessPiece curPiece = board.getPiece(curPosition);
                if (curPiece == null) {
                    continue;
                } if (curPiece.getPieceType() == ChessPiece.PieceType.KING &&
                        curPiece.getTeamColor() == team) {
                    return curPosition;
                }
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }
}
