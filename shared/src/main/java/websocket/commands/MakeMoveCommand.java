package websocket.commands;

import chess.ChessMove;
import com.google.gson.Gson;

public class MakeMoveCommand extends UserGameCommand {

    private final CommandType commandType = CommandType.MAKE_MOVE;
    private final String authToken;
    private final Integer gameID;
    private ChessMove move;

    public MakeMoveCommand(String authToken, Integer gameID) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.authToken = authToken;
        this.gameID = gameID;
    }

    public void move(ChessMove move) {
        this.move = move;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
