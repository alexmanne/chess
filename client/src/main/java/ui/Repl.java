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
    private String authtoken;
    private boolean isLoggedIn;
    private boolean isPlaying;

    public Repl(String serverUrl) {
        preLoginClient = new PreLoginClient();
        postLoginClient = new PostLoginClient();
        gamePlayClient = new GamePlayClient();
        isLoggedIn = false;
        isPlaying = false;
    }

    public void run() {
        System.out.println("Welcome to Alex's Chess Games. Type 'help' to get started.");
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quitting")) {
            if (this.isPlaying == true) {
                result = runGamePlay(scanner);
            } else if (this.isLoggedIn == true) {
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
            result = postLoginClient.eval(line);
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
