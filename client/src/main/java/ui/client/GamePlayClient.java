package ui.client;

import ui.EscapeSequences;
import ui.ServerFacade;

public class GamePlayClient {

    private final ServerFacade server;
    private static final String blackPawn = EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_PAWN;
    private static final String whitePawn = EscapeSequences.SET_TEXT_COLOR_WHITE + EscapeSequences.WHITE_PAWN;

    public GamePlayClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String inputLine) {
        return inputLine;
    }

    // Creates the string to print a new board to the screen from white perspective
    public static String drawNewWhiteBoard() {
        StringBuilder builder = new StringBuilder();

        builder.append(drawWhiteBoardHeader()).append("\n");
        builder.append(drawWhiteBoardBlackPieces()).append("\n");
        builder.append(drawGreenRow(7, blackPawn)).append("\n");
        builder.append(drawBlueRow(6, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawGreenRow(5, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawBlueRow(4, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawGreenRow(3, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawBlueRow(2, whitePawn)).append("\n");
        builder.append(drawWhiteBoardWhitePieces()).append("\n");
        builder.append(drawWhiteBoardHeader()).append("\n");

        return builder.toString();
    }

    // Creates the string to print a new board to the screen from black perspective
    public static String drawNewBlackBoard() {
        StringBuilder builder = new StringBuilder();

        builder.append(drawBlackBoardHeader()).append("\n");
        builder.append(drawBlackBoardWhitePieces()).append("\n");
        builder.append(drawGreenRow(2, whitePawn)).append("\n");
        builder.append(drawBlueRow(3, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawGreenRow(4, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawBlueRow(5, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawGreenRow(6, EscapeSequences.EMPTY)).append("\n");
        builder.append(drawBlueRow(7, blackPawn)).append("\n");
        builder.append(drawBlackBoardBlackPieces()).append("\n");
        builder.append(drawBlackBoardHeader()).append("\n");

        return builder.toString();
    }

    private static String drawBlueRow(int rowNum, String fill) {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREY+ EscapeSequences.SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawGreenRow(int rowNum, String fill) {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + fill +
                EscapeSequences.SET_BG_COLOR_BLUE + fill +
                EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawWhiteBoardWhitePieces() {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " 1 " +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_ROOK +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_KNIGHT +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_BISHOP +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_QUEEN +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_KING +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_BISHOP +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_KNIGHT +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_ROOK +
                EscapeSequences.SET_BG_COLOR_DARK_GREY + " 1 " +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawWhiteBoardBlackPieces() {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " 8 " +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_ROOK +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_KNIGHT +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_BISHOP +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_QUEEN +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_KING +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_BISHOP +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_KNIGHT +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_ROOK +
                EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " 8 " +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawWhiteBoardHeader() {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE +
                EscapeSequences.EMPTY + " a  b  c  d  e  f  g  h " + EscapeSequences.EMPTY +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawBlackBoardWhitePieces() {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " 1 " +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_ROOK +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_KNIGHT +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_BISHOP +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_QUEEN +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_KING +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_BISHOP +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.WHITE_KNIGHT +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.WHITE_ROOK +
                EscapeSequences.SET_BG_COLOR_DARK_GREY + " 1 " +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawBlackBoardBlackPieces() {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " 8 " +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.SET_TEXT_COLOR_BLACK + EscapeSequences.BLACK_ROOK +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_KNIGHT +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_BISHOP +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_QUEEN +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_KING +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_BISHOP +
                EscapeSequences.SET_BG_COLOR_DARK_GREEN + EscapeSequences.BLACK_KNIGHT +
                EscapeSequences.SET_BG_COLOR_BLUE + EscapeSequences.BLACK_ROOK +
                EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE + " 8 " +
                EscapeSequences.RESET_BG_COLOR;
    }

    private static String drawBlackBoardHeader() {
        return EscapeSequences.SET_BG_COLOR_DARK_GREY + EscapeSequences.SET_TEXT_COLOR_WHITE +
                EscapeSequences.EMPTY + " h  g  f  e  d  c  b  a " + EscapeSequences.EMPTY +
                EscapeSequences.RESET_BG_COLOR;
    }
}
