package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.DatabaseManager;
import exception.DataAccessException;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.*;

import java.io.IOException;
import java.util.Timer;

@WebSocket
public class WebSocketHandler {

//    private final ConnectionManager connections = new ConnectionManager();
//
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

            String username = "FIX THIS";

//             Make sure it is in the connection manager map
            saveSession(command.getGameID(), session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, command);
                case MAKE_MOVE -> make_move(session, username, command);
                case LEAVE -> leave(session, username, command);
                case RESIGN -> resign(session, username, command);
            }
        } catch (DataAccessException ex) {
            sendError(session.getRemote(), new DataAccessException(400, "Error: unauthorized"));
        } catch (Throwable ex) {
            sendError(session.getRemote(), new DataAccessException(400, "Error: " + ex.getMessage()));
        }
    }
//
    private void connect(Session session, String username, UserGameCommand command) throws IOException {
    }

    private void make_move(Session session, String username, UserGameCommand command) throws IOException {
    }

    private void leave(Session session, String username, UserGameCommand command) throws IOException {
    }

    private void resign(Session session, String username, UserGameCommand command) throws IOException {
    }

    private void saveSession(int gameId, Session session) {
    }

    private void sendError(RemoteEndpoint remoteEndpoint, DataAccessException exception) {
    }

}
