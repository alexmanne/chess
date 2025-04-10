package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
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
import java.util.ArrayList;
import java.util.Arrays;
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

            AuthData authData = authDB.getAuth(command.getAuthToken());
            String username = authData.username();

//             Make sure it is in the connection manager map
            saveSession(username, session);

            if (command.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)) {
                MakeMoveCommand moveCommand = new Gson().fromJson(message, MakeMoveCommand.class);
                make_move(username, moveCommand);
            }

            switch (command.getCommandType()) {
                case CONNECT -> connect(session, username, command);
                case LEAVE -> leave(username, command);
                case RESIGN -> resign(username, command);
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

    private void make_move(String username, MakeMoveCommand command) throws DataAccessException {
        try {
            GameData gameData = gameDB.getGame(command.getGameID());
            ChessMove move = command.getMove();
            ChessGame game = gameData.game();
            game.makeMove(move);

            GameData newGame = new GameData(gameData.gameID(), gameData.whiteUsername(),
                    gameData.blackUsername(), gameData.gameName(), game);
            gameDB.updateGame(newGame);

            String startPosition = serializePosition(move.getStartPosition());
            String endPosition = serializePosition(move.getEndPosition());
            String message = String.format("%s moved %s -> %s", username, startPosition, endPosition);
            ServerMessage notification = new NotificationMessage(message);
            connections.broadcast(username, notification);
        } catch (DataAccessException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    private String serializePosition(ChessPosition chessPosition) throws DataAccessException {
        // a1 is ChessPosition(1, 1)
        // f2 is ChessPosition(2, 6)
        String rowString = Integer.toString(chessPosition.getRow());
        String colString = switch (chessPosition.getColumn()) {
            case 1 -> "a" ;
            case 2 -> "b";
            case 3 -> "c";
            case 4 -> "d";
            case 5 -> "e";
            case 6 -> "f";
            case 7 -> "g";
            case 8 -> "h";
            default -> throw new DataAccessException(400, "Failed to make move. Try again");
        };
        return colString + rowString;
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

    private void resign(String username, UserGameCommand command) throws DataAccessException {
        try {
            GameData gameData = gameDB.getGame(command.getGameID());
            String winner;
            if (username.equals(gameData.whiteUsername())) {
                winner = gameData.blackUsername();
            } else if (username.equals(gameData.blackUsername())) {
                winner = gameData.whiteUsername();
            } else {
                winner = "No one";
            }
            String message = String.format("%s left the game. %s won!", username, winner);
            ServerMessage notification = new NotificationMessage(message);
            connections.broadcast(username, notification);
        } catch (DataAccessException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    private void saveSession(String username, Session session) {
        connections.add(username, session);
    }

    private void sendError(DataAccessException exception) throws IOException {
        ServerMessage errorMessage = new ErrorMessage(exception.getMessage());
        connections.broadcast(null, errorMessage);
    }

}
