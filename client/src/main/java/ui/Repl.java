package ui;

import ui.client.GamePlayClient;
import ui.client.PostLoginClient;
import ui.client.PreLoginClient;
import ui.EscapeSequences;

import java.util.Scanner;

public class Repl {

    private final PreLoginClient preLoginClient;
    private final PostLoginClient postLoginClient;
    private final GamePlayClient gamePlayClient;
    private String authToken;
    private boolean isLoggedIn;
    private boolean isPlaying;

    public Repl(String serverUrl) {
        preLoginClient = new PreLoginClient(serverUrl);
        postLoginClient = new PostLoginClient(serverUrl);
        gamePlayClient = new GamePlayClient(serverUrl);
        isLoggedIn = false;
        isPlaying = false;
        authToken = "";
    }

    public void run() {
        System.out.println("Welcome to Alex's Chess Games. Type 'help' to get started.");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quitting")) {
            if (this.isPlaying) {
                result = runGamePlay(scanner);
            } else if (this.isLoggedIn) {
                result = runLoggedIn(scanner);
            } else {
                result = runLoggedOut(scanner);
            }
        }
    }

    private String runLoggedOut(Scanner scanner) {
        printLoggedOutPrompt();
        String line = scanner.nextLine();
        String result = "";

        try {
            result = preLoginClient.eval(line);
            if (result.startsWith("logging in")) {
                isLoggedIn = true;
                var resultParts = result.split("\t");
                result = resultParts[0];
                authToken = resultParts[1];
            }
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
            result = postLoginClient.eval(line, authToken);
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
            result = gamePlayClient.eval(line);
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
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_WHITE + "[PLAYING] >>> ");
    }


}
