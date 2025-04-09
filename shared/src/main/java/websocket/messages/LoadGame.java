package websocket.messages;

import chess.ChessGame;

public class LoadGame extends ServerMessage {

    public ServerMessageType serverMessageType = ServerMessageType.LOAD_GAME;
    public ChessGame game;

    public LoadGame(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }
}
