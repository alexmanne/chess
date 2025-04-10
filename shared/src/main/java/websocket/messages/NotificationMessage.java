package websocket.messages;

import chess.ChessGame;

public class NotificationMessage extends ServerMessage {

    public ServerMessageType serverMessageType = ServerMessageType.NOTIFICATION;
    public String message;

    public NotificationMessage(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }
}
