package websocket.messages;

public class ErrorMessage extends ServerMessage {

    public ServerMessageType serverMessageType = ServerMessageType.ERROR;
    public String errorMessage;

    public ErrorMessage(String errorMessage) {
        super(serverMessageType);
        this.errorMessage = errorMessage;
    }
}
