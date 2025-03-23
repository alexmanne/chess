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
}
