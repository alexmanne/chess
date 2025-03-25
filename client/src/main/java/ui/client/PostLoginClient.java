package ui.client;

import exception.DataAccessException;
import model.request.CreateRequest;
import model.request.JoinRequest;
import model.result.CreateResult;
import model.result.ListOneGameResult;
import model.result.ListResult;
import ui.ServerFacade;
import ui.EscapeSequences;
import ui.Repl;
import ui.State;

import java.util.ArrayList;
import java.util.Arrays;

public class PostLoginClient {

    private final ServerFacade server;
    private ArrayList<ListOneGameResult> games;

    public PostLoginClient(ServerFacade server) {
        this.server = server;
    }

    public String eval(String inputLine, Repl repl) {
        try {
            var tokens = inputLine.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(repl, params);
                case "list" -> list(repl);
                case "join" -> join(repl, params);
                case "observe" -> observe(repl, params);
                case "logout" -> logout(repl);
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
                "create User's Game");
    }

    public String list(Repl repl) throws DataAccessException {
        ListResult result = server.listGames(repl.authToken);
        var stringResult = new StringBuilder();
        games = result.games();
        int id = 1;
        for (var game : games) {
            stringResult.append("- ").append(id);
            stringResult.append(": ").append(game.gameName()).append("\n");

            String white;
            String black;
            if (game.whiteUsername() == null) {white = "None";}
            else {white = game.whiteUsername();}
            if (game.blackUsername() == null) {black = "None";}
            else {black = game.blackUsername();}
            stringResult.append("\tWhite Player: ").append(white).append("\n");
            stringResult.append("\tBlack Player: ").append(black).append("\n");

            id++;
        }
        return stringResult.toString();
    }

    public String join(Repl repl, String... params) throws DataAccessException {
        if (games == null) {
            throw new DataAccessException(400, "Must list games first");
        }
        if (params.length >= 2) {
            int givenId = Integer.parseInt(params[0]);
            int gameId = games.get(givenId - 1).gameID();
            String color = params[1].toUpperCase();
            JoinRequest request = new JoinRequest(repl.authToken, color, gameId);
            server.joinGame(request);
            if (color.equals("WHITE")) {
                repl.state = State.PLAYINGWHITE;
            } else {
                repl.state = State.PLAYINGBLACK;
            }
            return "joined game: " + givenId;
        }
        throw new DataAccessException(400, "Expected: <ID> [WHITE|BLACK]. Example:\n" +
                "join 2 white");
    }

    public String observe(Repl repl, String... params) throws DataAccessException {
        if (games == null) {
            throw new DataAccessException(400, "Must list games first");
        }
        if (params.length >= 1) {
            int givenId = Integer.parseInt(params[0]);
            int gameId = games.get(givenId - 1).gameID();
            // Implement in phase 6
            repl.state = State.OBSERVING;
            return "observing game: "+ givenId;
        }
        throw new DataAccessException(400, "Expected: <ID>. Example:\n" +
                "observe 2");
    }

    public String logout(Repl repl) throws DataAccessException {
        server.logout(repl.authToken);
        repl.state = State.LOGGEDOUT;
        repl.authToken = "";
        return "logging out";
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
