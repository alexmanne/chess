package ui.client;

import chess.ChessGame;
import exception.DataAccessException;
import ui.ServerFacade;
import ui.Repl;
import ui.State;
import websocket.ServerMessageObserver;
import websocket.WebSocketFacade;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class GamePlayClient implements ServerMessageObserver {

    private final ServerFacade server;
    private ChessGame game;
    private WebSocketFacade ws;
    private static ArrayList<String> emptyRow;
    private static ArrayList<String> whitePawnRow;
    private static ArrayList<String> blackPawnRow;

    public GamePlayClient(ServerFacade server, int port) throws DataAccessException {
        this.server = server;
        String serverUrl = "http://localhost:" + port;
        ws = new WebSocketFacade(serverUrl, this);
    }

    public String eval(String inputLine, Repl repl) {
        try {
            var tokens = inputLine.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redraw(repl);
                case "leave" -> leave(repl);
                case "move" -> move(repl, params);
                case "resign" -> resign(repl);
                case "highlight" -> highlight(repl, params);
                case "quit" -> "quitting";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    private String redraw(Repl repl) {
        return null;
    }

    private String leave(Repl repl) throws DataAccessException {
        return null;
    }

    private String move(Repl repl, String[] params) throws DataAccessException {
        return null;
    }

    private String resign(Repl repl) throws DataAccessException {
        return null;
    }

    private String highlight(Repl repl, String[] params) throws DataAccessException {
        return null;
    }

    private String help() {
        return null;
    }

    public String drawBlackBoard () {
        StringBuilder builder = new StringBuilder();



        return builder.toString();
    }

    public String drawWhiteBoard () {
        StringBuilder builder = new StringBuilder();



        return builder.toString();
    }

    // Creates the string to print a new board to the screen from white perspective
    public static String drawNewWhiteBoard() {
        StringBuilder builder = new StringBuilder();

        drawRows();

        builder.append(drawWhiteBoardHeader()).append("\n");
        builder.append(drawWhiteBoardBlackPieces()).append("\n");
        builder.append(drawGreenRow(7, blackPawnRow)).append("\n");
        builder.append(drawBlueRow(6, emptyRow)).append("\n");
        builder.append(drawGreenRow(5, emptyRow)).append("\n");
        builder.append(drawBlueRow(4, emptyRow)).append("\n");
        builder.append(drawGreenRow(3, emptyRow)).append("\n");
        builder.append(drawBlueRow(2, whitePawnRow)).append("\n");
        builder.append(drawWhiteBoardWhitePieces()).append("\n");
        builder.append(drawWhiteBoardHeader()).append("\n");

        return builder.toString();
    }

    // Creates the string to print a new board to the screen from black perspective
    public static String drawNewBlackBoard() {
        StringBuilder builder = new StringBuilder();

        drawRows();

        builder.append(drawBlackBoardHeader()).append("\n");
        builder.append(drawBlackBoardWhitePieces()).append("\n");
        builder.append(drawGreenRow(2, whitePawnRow)).append("\n");
        builder.append(drawBlueRow(3, emptyRow)).append("\n");
        builder.append(drawGreenRow(4, emptyRow)).append("\n");
        builder.append(drawBlueRow(5, emptyRow)).append("\n");
        builder.append(drawGreenRow(6, emptyRow)).append("\n");
        builder.append(drawBlueRow(7, blackPawnRow)).append("\n");
        builder.append(drawBlackBoardBlackPieces()).append("\n");
        builder.append(drawBlackBoardHeader()).append("\n");

        return builder.toString();
    }

    private static void drawRows() {
        emptyRow = new ArrayList<>();
        blackPawnRow = new ArrayList<>();
        whitePawnRow = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            emptyRow.add(EMPTY);
            blackPawnRow.add(BLACK_PAWN);
            whitePawnRow.add(WHITE_PAWN);
        }
    }

    private static String drawBlueRow(int rowNum, ArrayList<String> fill) {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                SET_BG_COLOR_BLUE + fill.get(0) +
                SET_BG_COLOR_DARK_GREEN + fill.get(1) +
                SET_BG_COLOR_BLUE + fill.get(2) +
                SET_BG_COLOR_DARK_GREEN + fill.get(3) +
                SET_BG_COLOR_BLUE + fill.get(4) +
                SET_BG_COLOR_DARK_GREEN + fill.get(5) +
                SET_BG_COLOR_BLUE + fill.get(6) +
                SET_BG_COLOR_DARK_GREEN + fill.get(7) +
                SET_BG_COLOR_DARK_GREY+ SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                RESET_BG_COLOR;
    }

    private static String drawGreenRow(int rowNum, ArrayList<String> fill) {
        return SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE +
                " " + rowNum + " " +
                SET_BG_COLOR_DARK_GREEN + fill.get(0) +
                SET_BG_COLOR_BLUE + fill.get(1) +
                SET_BG_COLOR_DARK_GREEN + fill.get(2) +
                SET_BG_COLOR_BLUE + fill.get(3) +
                SET_BG_COLOR_DARK_GREEN + fill.get(4) +
                SET_BG_COLOR_BLUE + fill.get(5) +
                SET_BG_COLOR_DARK_GREEN + fill.get(6) +
                SET_BG_COLOR_BLUE + fill.get(7) +
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

    @Override
    public void notify(ServerMessage serverMessage) {
        switch (serverMessage.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(serverMessage);
            case ERROR -> displayError(serverMessage);
            case LOAD_GAME -> loadgame(serverMessage);
        }
    }

    void displayNotification(ServerMessage serverMessage) {}

    void displayError(ServerMessage serverMessage) {}

    void loadgame(ServerMessage serverMessage) {}
}
