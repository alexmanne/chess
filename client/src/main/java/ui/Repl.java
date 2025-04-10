package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.DataAccessException;
import ui.client.GamePlayClient;
import ui.client.PostLoginClient;
import ui.client.PreLoginClient;
import websocket.ServerMessageObserver;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGame;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.util.Scanner;

public class Repl implements ServerMessageObserver {

    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private final GamePlayClient gamePlayClient;
    public final ChessBoardRepl boardRepl;
    public String authToken;
    public String username;
    public int gameID;
    public ChessGame game;
    public State state;

    public Repl(int port) throws DataAccessException {
        ServerFacade server = new ServerFacade(port);
        preLoginClient = new PreLoginClient(server);
        postLoginClient = new PostLoginClient(server);
        gamePlayClient = new GamePlayClient(this, port);
        boardRepl = new ChessBoardRepl(null, null);
        state = State.LOGGEDOUT;
        authToken = "";
    }

    public void run() {
        System.out.println("Welcome to Alex's Chess Games. Type 'help' to get started.");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quitting")) {
            if (state == State.LOGGEDOUT) {
                result = runLoggedOut(scanner);
            } else if (state == State.LOGGEDIN) {
                result = runLoggedIn(scanner);
            } else {
                result = runGamePlay(scanner);
            }
        }
    }

    private String runLoggedOut(Scanner scanner) {
        printLoggedOutPrompt();
        String line = scanner.nextLine();
        String result = "";

        try {
            result = preLoginClient.eval(line, this);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
            System.out.print(result);

        } catch (Throwable e) {
            var msg = e.toString();
            System.out.print(msg);
        }
        System.out.println();

        return result;
    }

    public void printLoggedOutPrompt() {
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_WHITE + "[LOGGED OUT] >>> ");
    }


    private String runLoggedIn(Scanner scanner) {
        printLoggedInPrompt();
        String line = scanner.nextLine();
        String result = "";

        try {
            result = postLoginClient.eval(line, this);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
            System.out.print(result);
        } catch (Throwable e) {
            var msg = e.toString();
            System.out.print(msg);
        }
        System.out.println();

        return result;
    }

    public void printLoggedInPrompt() {
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_WHITE + "[LOGGED IN] >>> ");
    }

    private String runGamePlay(Scanner scanner) {
        printGamePlayPrompt();
        String line = scanner.nextLine();
        String result = "";

        try {
            result = gamePlayClient.eval(line, this);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
            System.out.print(result);
        } catch (Throwable e) {
            var msg = e.toString();
            System.out.print(msg);
        }
        System.out.println();

        return result;
    }

    public void printGamePlayPrompt() {
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_WHITE + "[IN GAME] >>> ");
    }

    @Override
    public void notify(String message) {
        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);

        switch (serverMessage.getServerMessageType()) {
            case NOTIFICATION -> displayNotification(
                    new Gson().fromJson(message, NotificationMessage.class));
            case ERROR -> displayError(
                    new Gson().fromJson(message, ErrorMessage.class));
            case LOAD_GAME -> loadGame(
                    new Gson().fromJson(message, LoadGame.class));
        }
    }

    void displayNotification(NotificationMessage serverMessage) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
        System.out.println(serverMessage.message);
        printGamePlayPrompt();
    }

    void displayError(ErrorMessage serverMessage) {
        System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
        System.out.println(serverMessage.errorMessage);
        printGamePlayPrompt();
    }

    void loadGame(LoadGame serverMessage) {
        this.game = serverMessage.game;
        boardRepl.setGame(serverMessage.game);
        String board = boardRepl.drawBoard();
        System.out.print(board);
        printGamePlayPrompt();
    }

}
