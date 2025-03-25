package ui.client;

import server.ServerFacade;

public class GamePlayClient {

    private final ServerFacade server;

    public GamePlayClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public String eval(String inputLine) {
        return "evaluated";
    }

    // Creates the string to print a new board to the screen from white perspective
    public static String drawNewWhiteBoard () {
        return null;
    }

    // Creates the string to print a new board to the screen from black perspective
    public static String drawNewBlackBoard () {
        return null;
    }
}
