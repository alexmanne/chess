package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.DatabaseManager;
import exception.DataAccessException;
import model.AuthData;
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

    private final ConnectionManager connections = new ConnectionManager();
    private final AuthDao authDB;

    public WebSocketHandler(AuthDao authDB) {
        this.authDB = authDB;
    }
//
    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

            if (command.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)) {
                command = new Gson().fromJson(message, MakeMoveCommand.class);
            }

            AuthData authData = authDB.getAuth(command.getAuthToken());
            String username = authData.username();

//             Make sure it is in the connection manager map
            saveSession(username, session);

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, command);
                case MAKE_MOVE -> make_move(session, username, command);
                case LEAVE -> leave(session, username, command);
                case RESIGN -> resign(session, username, command);
            }
        } catch (DataAccessException ex) {
            sendError(session.getRemote(), ex);
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

    private void saveSession(String username, Session session) {
        connections.add(username, session);
    }

    private void sendError(RemoteEndpoint remoteEndpoint, DataAccessException exception) {

    }

}
