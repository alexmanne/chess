package websocket.messages;

import chess.ChessGame;

public class NotificationMessage extends ServerMessage {

    public ServerMessageType serverMessageType = ServerMessageType.NOTIFICATION;
    public String message;

    public NotificationMessage(String message) {
        super(serverMessageType);
        this.message = message;
    }
}
