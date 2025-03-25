package ui;

import ui.client.GamePlayClient;
import ui.client.PostLoginClient;
import ui.client.PreLoginClient;

import java.util.Scanner;

public class Repl {

    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private final GamePlayClient gamePlayClient;
    public String authToken;
    public State state;

    public Repl(int port) {
        ServerFacade server = new ServerFacade(port);
        preLoginClient = new PreLoginClient(server);
        postLoginClient = new PostLoginClient(server);
        gamePlayClient = new GamePlayClient(server);
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


}
