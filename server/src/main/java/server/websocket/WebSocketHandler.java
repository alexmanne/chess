package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.DatabaseManager;
import dataaccess.GameDao;
import exception.DataAccessException;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.GameService;
import websocket.commands.*;
import websocket.messages.*;

import java.io.IOException;
import java.util.Timer;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final AuthDao authDB;
    private final GameDao gameDB;
    private final GameService gameService;

    public WebSocketHandler(AuthDao authDB, GameDao gameDB, GameService gameService) {
        this.authDB = authDB;
        this.gameDB = gameDB;
        this.gameService = gameService;
    }
//
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
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
                case LEAVE -> leave(username, command);
                case RESIGN -> resign(session, username, command);
            }
        } catch (DataAccessException ex) {
            sendError(ex);
        } catch (Throwable ex) {
            sendError(new DataAccessException(400, "Error: " + ex.getMessage()));
        }
    }
//
    private void connect(Session session, String username, UserGameCommand command) throws DataAccessException {
        try {
            connections.add(username, session);
            GameData gameData = gameDB.getGame(command.getGameID());
            String message;
            if (username.equals(gameData.whiteUsername())) {
                message = String.format("%s joined the game playing white", username);
            } else if (username.equals(gameData.blackUsername())) {
                message = String.format("%s joined the game playing black", username);
            } else {
                message = String.format("%s joined the game to observe", username);
            }
            ServerMessage notification = new NotificationMessage(message);
            connections.broadcast(null, notification);
        } catch (DataAccessException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    private void make_move(Session session, String username, UserGameCommand command) throws DataAccessException {

    }

    private void leave(String username, UserGameCommand command) throws DataAccessException {
        try {
            GameData gameData = gameDB.getGame(command.getGameID());
            String message = String.format("%s left the game", username);
            if (username.equals(gameData.whiteUsername())) {
                GameData newGame = new GameData(gameData.gameID(), null,
                        gameData.blackUsername(), gameData.gameName(), gameData.game());
                gameDB.updateGame(newGame);
            } else if (username.equals(gameData.blackUsername())) {
                GameData newGame = new GameData(gameData.gameID(), gameData.whiteUsername(),
                        null, gameData.gameName(), gameData.game());
                gameDB.updateGame(newGame);
            }
            ServerMessage notification = new NotificationMessage(message);
            connections.broadcast(username, notification);
        } catch (DataAccessException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    private void resign(Session session, String username, UserGameCommand command) throws DataAccessException {
    }

    private void saveSession(String username, Session session) {
        connections.add(username, session);
    }

    private void sendError(DataAccessException exception) throws IOException {
        ServerMessage errorMessage = new ErrorMessage(exception.getMessage());
        connections.broadcast(null, errorMessage);
    }

}
