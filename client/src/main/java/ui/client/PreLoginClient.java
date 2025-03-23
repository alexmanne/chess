package ui.client;

public class PreLoginClient {

    public String eval(String inputLine) {
        return "evaluated";
    }

    public String help() {
        return """
               - signIn <yourname>
               - quit
               """;
    }
}
