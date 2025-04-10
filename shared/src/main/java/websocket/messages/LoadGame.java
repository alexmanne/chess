package websocket.messages;

import chess.ChessGame;
import com.google.gson.Gson;

public class LoadGame extends ServerMessage {

    public ChessGame game;
    boolean gameOver;

    public LoadGame(ChessGame game, boolean gameOver) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;

        this.gameOver = gameOver;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
