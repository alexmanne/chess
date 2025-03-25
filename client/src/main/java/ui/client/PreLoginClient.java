package ui.client;

import exception.DataAccessException;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.RegisterResult;
import sharedserver.ServerFacade;
import ui.EscapeSequences;
import ui.Repl;
import ui.State;

import java.util.Arrays;

public class PreLoginClient {

    private final ServerFacade server;

    public PreLoginClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String inputLine, Repl repl) {
        try {
            var tokens = inputLine.split(" ");
            var cmd = (tokens.length > 0) ? tokens[0].toLowerCase() : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(repl, params);
                case "login" -> login(repl, params);
                case "quit" -> "quitting";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    public String register(Repl repl, String... params) throws DataAccessException {
        if (params.length >= 3) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
            RegisterRequest request = new RegisterRequest(username, password, email);
            RegisterResult result = server.register(request);
            repl.state = State.LOGGEDIN;
            repl.authToken = result.authToken();
            return "logging in " + request.username();
        }
        throw new DataAccessException(400, "Expected: <USERNAME> <PASSWORD> <EMAIL>. Example:\n" +
                "login user123 pass1234 user@email.com");
    }

    public String login(Repl repl, String... params) throws DataAccessException {
        if (params.length >= 2) {
            String username = params[0];
            String password = params[1];
            LoginRequest request = new LoginRequest(username, password);
            LoginResult result = server.login(request);
            repl.state = State.LOGGEDIN;
            repl.authToken = result.authToken();
            return "logging in " + result.username();
        }
        throw new DataAccessException(400, "Expected: <USERNAME> <PASSWORD>. Example:\n" +
                                           "login user123 pass1234");
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
