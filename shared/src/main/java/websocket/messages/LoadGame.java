package websocket.messages;

import chess.ChessGame;
import com.google.gson.Gson;

public class LoadGame extends ServerMessage {

    public ServerMessageType serverMessageType = ServerMessageType.LOAD_GAME;
    public ChessGame game;

    public LoadGame(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
