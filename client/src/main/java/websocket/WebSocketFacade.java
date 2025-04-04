package websocket;

import com.google.gson.Gson;
import exception.DataAccessException;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    Session session;

    public WebSocketFacade(String url, ServerMessageObserver gameClient) throws DataAccessException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    try {
                        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                        gameClient.notify(serverMessage);
                    } catch (Exception ex) {
                        gameClient.notify(new ServerMessage(ServerMessage.ServerMessageType.ERROR));
                    }
                }
            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


}
