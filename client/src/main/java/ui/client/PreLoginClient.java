package ui.client;

import exception.DataAccessException;
import server.ServerFacade;
import ui.EscapeSequences;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;

public class PreLoginClient {

    private final ServerFacade server;

    public PreLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public String eval(String inputLine) {
        try {
            var tokens = inputLine.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0].toLowerCase() : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "quit" -> "quitting";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws DataAccessException {
        return null;
    }

    public String register(String... params) throws DataAccessException {
        return null;
    }

    public String help() {
        return EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_ITALIC +
                "Here are your options:\n" +

                // Register
                EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.RESET_TEXT_ITALIC +
                "\tregister <USERNAME> <PASSWORD> <EMAIL> " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- create an account\n" +

                // Login
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tlogin <USERNAME> <PASSWORD> " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- login to play chess\n" +

                // Quit
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tquit " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- leave the server\n" +

                // Login
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\thelp " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- print possible commands";
    }


}
