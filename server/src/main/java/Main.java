import chess.*;
import serverclass.Server;

public class Main {
    public static void main(String[] args) {

        try {
            var port = 8080;
            if (args.length >= 1) {
                port = Integer.parseInt(args[0]);
            }

            var chessServer = new Server();
            port = chessServer.run(port);
            System.out.printf("Server started on port %d with %s%n", port, "Memory Access");
            return;
        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
    }
}