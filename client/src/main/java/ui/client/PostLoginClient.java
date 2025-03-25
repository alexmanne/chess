package ui.client;

import exception.DataAccessException;
import model.request.CreateRequest;
import model.request.LoginRequest;
import model.result.CreateResult;
import model.result.LoginResult;
import server.ServerFacade;
import ui.EscapeSequences;
import ui.Repl;

import java.util.Arrays;

public class PostLoginClient {

    private final ServerFacade server;

    public PostLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public String eval(String inputLine, Repl repl) {
        try {
            var tokens = inputLine.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(repl, params);
                case "list" -> list(repl, params);
                case "join" -> join(repl, params);
                case "observe" -> observe(repl, params);
                case "logout" -> logout(repl, params);
                case "quit" -> "quitting";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    public String create(Repl repl, String... params) throws DataAccessException {
        if (params.length >= 1) {
            String gameName = String.join(" ", params);
            CreateRequest request = new CreateRequest(gameName, repl.authToken);
            CreateResult result = server.createGame(request);
            return "Created game: '" + gameName + "' with id: " + result.gameID();
        }
        throw new DataAccessException(400, "Expected: <NAME>. Example:\n" +
                "create Alex's Game");
    }

    public String list(Repl repl, String... params) throws DataAccessException {
        if (params.length == 0) {
            server.logout(repl.authToken);
            repl.isLoggedIn = false;
            repl.authToken = "";
            return "logging out";
        }
        throw new DataAccessException(400, "Expected: <USERNAME> <PASSWORD>. Example:\n" +
                "login user123 pass1234");
    }

    public String join(Repl repl, String... params) throws DataAccessException {
        if (params.length == 0) {
            server.logout(repl.authToken);
            repl.isLoggedIn = false;
            repl.authToken = "";
            return "logging out";
        }
        throw new DataAccessException(400, "Expected: <USERNAME> <PASSWORD>. Example:\n" +
                "login user123 pass1234");
    }

    public String observe(Repl repl, String... params) throws DataAccessException {
        if (params.length == 0) {
            server.logout(repl.authToken);
            repl.isLoggedIn = false;
            repl.authToken = "";
            return "logging out";
        }
        throw new DataAccessException(400, "Expected: <USERNAME> <PASSWORD>. Example:\n" +
                "login user123 pass1234");
    }

    public String logout(Repl repl, String... params) throws DataAccessException {
        if (params.length == 0) {
            server.logout(repl.authToken);
            repl.isLoggedIn = false;
            repl.authToken = "";
            return "logging out";
        }
        throw new DataAccessException(400, "Unexpected error");
    }

    public String help() throws DataAccessException {
        return EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY + EscapeSequences.SET_TEXT_ITALIC +
                "Here are your options:\n" +

                // Create Game
                EscapeSequences.SET_TEXT_COLOR_BLUE + EscapeSequences.RESET_TEXT_ITALIC +
                "\tcreate <NAME> " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- create a new game\n" +

                // list
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tlist " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- list available games\n" +

                // join
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tjoin <ID> [WHITE|BLACK] " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- join that game as that color\n" +

                // observe
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tobserve <ID> " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- observe that game\n" +

                // logout
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tlogout " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- logout of chess server\n" +

                // Quit
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\tquit " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- logout and leave the server\n" +

                // Login
                EscapeSequences.SET_TEXT_COLOR_BLUE +
                "\thelp " +
                EscapeSequences.SET_TEXT_COLOR_WHITE +
                "- print possible commands";
    }
}
