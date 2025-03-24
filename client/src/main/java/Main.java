import chess.*;
import ui.EscapeSequences;
import ui.Repl;
import server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n");
        var serverUrl = "http://localhost:" + port;
        new Repl(serverUrl).run();
    }
}