package ui.client;

import ui.ServerFacade;
import ui.Repl;
import ui.State;

import static ui.EscapeSequences.*;

public class GamePlayClient {

    private final ServerFacade server;

    public GamePlayClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String inputLine, Repl repl) {
        if (inputLine.equals("leave")) {
            repl.state = State.LOGGEDIN;
        }
        return inputLine;
    }

    // Creates the string to print a new board to the screen from white perspective
    public static String drawNewWhiteBoard() {
        StringBuilder builder = new StringBuilder();

        builder.append(drawWhiteBoardHeader()).append("\n");
        builder.append(drawWhiteBoardBlackPieces()).append("\n");
        builder.append(drawGreenRow(7, BLACK_PAWN)).append("\n");
        builder.append(drawBlueRow(6, EMPTY)).append("\n");
        builder.append(drawGreenRow(5, EMPTY)).append("\n");
        builder.append(drawBlueRow(4, EMPTY)).append("\n");
        builder.append(drawGreenRow(3, EMPTY)).append("\n");
        builder.append(drawBlueRow(2, WHITE_PAWN)).append("\n");
        builder.append(drawWhiteBoardWhitePieces()).append("\n");
        builder.append(drawWhiteBoardHeader()).append("\n");

        return builder.toString();
    }

    // Creates the string to print a new board to the screen from black perspective
    public static String drawNewBlackBoard() {
        StringBuilder builder = new StringBuilder();

        builder.append(drawBlackBoardHeader()).append("\n");
        builder.append(drawBlackBoardWhitePieces()).append("\n");
        builder.append(drawGreenRow(2, WHITE_PAWN)).append("\n");
        builder.append(drawBlueRow(3, EMPTY)).append("\n");
        builder.append(drawGreenRow(4, EMPTY)).append("\n");
        builder.append(drawBlueRow(5, EMPTY)).append("\n");
        builder.append(drawGreenRow(6, EMPTY)).append("\n");
        builder.append(drawBlueRow(7, BLACK_PAWN)).append("\n");
        builder.append(drawBlackBoardBlackPieces()).append("\n");
        builder.append(drawBlackBoardHeader()).append("\n");

        return builder.toString();
    }

    private static String drawBlueRow(int rowNum, String fill) {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_DARK_GREY+ SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                RESET_BG_COLOR;
    }

    private static String drawGreenRow(int rowNum, String fill) {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREEN + fill +
                SET_BG_COLOR_BLUE + fill +
                SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                RESET_BG_COLOR;
    }

    private static String drawWhiteBoardWhitePieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 " +
                SET_BG_COLOR_DARK_GREEN + WHITE_ROOK +
                SET_BG_COLOR_BLUE + WHITE_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + WHITE_BISHOP +
                SET_BG_COLOR_BLUE + WHITE_QUEEN +
                SET_BG_COLOR_DARK_GREEN + WHITE_KING +
                SET_BG_COLOR_BLUE + WHITE_BISHOP +
                SET_BG_COLOR_DARK_GREEN + WHITE_KNIGHT +
                SET_BG_COLOR_BLUE + WHITE_ROOK +
                SET_BG_COLOR_DARK_GREY + " 1 " +
                RESET_BG_COLOR;
    }

    private static String drawWhiteBoardBlackPieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                SET_BG_COLOR_BLUE + SET_TEXT_COLOR_BLACK + BLACK_ROOK +
                SET_BG_COLOR_DARK_GREEN + BLACK_KNIGHT +
                SET_BG_COLOR_BLUE + BLACK_BISHOP +
                SET_BG_COLOR_DARK_GREEN + BLACK_QUEEN +
                SET_BG_COLOR_BLUE + BLACK_KING +
                SET_BG_COLOR_DARK_GREEN + BLACK_BISHOP +
                SET_BG_COLOR_BLUE + BLACK_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + BLACK_ROOK +
                SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                RESET_BG_COLOR;
    }

    private static String drawWhiteBoardHeader() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                EMPTY + " a  b  c  d  e  f  g  h " + EMPTY +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardWhitePieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 " +
                SET_BG_COLOR_BLUE + WHITE_ROOK +
                SET_BG_COLOR_DARK_GREEN + WHITE_KNIGHT +
                SET_BG_COLOR_BLUE + WHITE_BISHOP +
                SET_BG_COLOR_DARK_GREEN + WHITE_QUEEN +
                SET_BG_COLOR_BLUE + WHITE_KING +
                SET_BG_COLOR_DARK_GREEN + WHITE_BISHOP +
                SET_BG_COLOR_BLUE + WHITE_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + WHITE_ROOK +
                SET_BG_COLOR_DARK_GREY + " 1 " +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardBlackPieces() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_BLACK + BLACK_ROOK +
                SET_BG_COLOR_BLUE + BLACK_KNIGHT +
                SET_BG_COLOR_DARK_GREEN + BLACK_BISHOP +
                SET_BG_COLOR_BLUE + BLACK_QUEEN +
                SET_BG_COLOR_DARK_GREEN + BLACK_KING +
                SET_BG_COLOR_BLUE + BLACK_BISHOP +
                SET_BG_COLOR_DARK_GREEN + BLACK_KNIGHT +
                SET_BG_COLOR_BLUE + BLACK_ROOK +
                SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 " +
                RESET_BG_COLOR;
    }

    private static String drawBlackBoardHeader() {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                EMPTY + " h  g  f  e  d  c  b  a " + EMPTY +
                RESET_BG_COLOR;
    }
}
