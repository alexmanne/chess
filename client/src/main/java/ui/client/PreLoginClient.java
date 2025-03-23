package ui.client;

import exception.DataAccessException;
import server.ServerFacade;

import java.util.Arrays;

public class PreLoginClient {

    private final ServerFacade server;

    public PreLoginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public String eval(String inputLine) {
        try {
            var tokens = inputLine.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
//                case "signin" -> signIn(params);
//                case "rescue" -> rescuePet(params);
//                case "list" -> listPets();
//                case "signout" -> signOut();
//                case "adopt" -> adoptPet(params);
//                case "adoptall" -> adoptAllPets();
                case "quit" -> "quitting";
                default -> help();
            };
        } catch (DataAccessException ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        return """
               - signIn <yourname>
               - quit
               """;
    }
}
