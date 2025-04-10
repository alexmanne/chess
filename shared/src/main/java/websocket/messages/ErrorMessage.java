package websocket.messages;

import com.google.gson.Gson;

public class ErrorMessage extends ServerMessage {

    public ServerMessageType serverMessageType = ServerMessageType.ERROR;
    public String errorMessage;

    public ErrorMessage(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
