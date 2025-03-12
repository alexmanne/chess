package service;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.AuthData;
import model.GameData;
import model.request.CreateRequest;
import model.request.JoinRequest;
import model.result.CreateResult;
import model.result.ListOneGameResult;
import model.result.ListResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameService {

    private final AuthDao authDB;
    private final GameDao gameDB;

    public GameService(AuthDao authDB, GameDao gameDB) {
        this.authDB = authDB;
        this.gameDB = gameDB;
    }

    public CreateResult createGame(CreateRequest request) throws DataAccessException {
        // Verify if authorized
        AuthData auth = authDB.getAuth(request.authToken());
        if (auth.authToken() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }

        // Verify if bad request
        if (badCreateRequest(request)) {
            throw new DataAccessException(400, "Error: bad request");
        }

        try {
            ChessGame newGame = new ChessGame();
            int gameID = gameDB.createGame(null, null,
                                            request.gameName(), newGame);
            return new CreateResult(gameID);

        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }

    /** Returns true if gameName is empty*/
    private boolean badCreateRequest (CreateRequest request) {
        if (request.gameName() == null) {
            return true;
        }
        return request.gameName().isEmpty();
    }

    public ListResult listGames (String authToken) throws DataAccessException {
        // Verify if authorized
        AuthData auth = authDB.getAuth(authToken);
        if (auth.authToken() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }

        try {
            // Get the games (has the actual ChessGame in it)
            HashMap<String, Collection<GameData>> gameDataList = gameDB.listGames();

            // Get the return list for JSON
            ArrayList<ListOneGameResult> resultGameList = new ArrayList<>();
            for (GameData game : gameDataList.get("games")) {
                resultGameList.add(new ListOneGameResult(game.gameID(),
                                                         game.whiteUsername(),
                                                         game.blackUsername(),
                                                         game.gameName()));
            }
            return new ListResult(resultGameList);

        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }

    public String joinGame (JoinRequest request) throws DataAccessException {
        // Verify if authorized
        AuthData auth = authDB.getAuth(request.authToken());
        if (auth.authToken() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }
        GameData game = gameDB.getGame(request.gameID());

        // Verify if bad request
        if (badJoinRequest(request, game)) {
            throw new DataAccessException(400, "Error: bad request");
        }

        // Verify if the game already has a player in the desired color
        if (request.playerColor().equals("WHITE")) {
            if (game.whiteUsername() != null) {
                throw new DataAccessException(403, "Error: already taken");
            }
        }
        if (request.playerColor().equals("BLACK")) {
            if (game.blackUsername() != null) {
                throw new DataAccessException(403, "Error: already taken");
            }
        }

        try {
            GameData updatedGame;
            if (request.playerColor().equals("WHITE")) {
                updatedGame = new GameData(game.gameID(), auth.username(), game.blackUsername(),
                                           game.gameName(), game.game());
            } else {
                updatedGame = new GameData(game.gameID(), game.whiteUsername(), auth.username(),
                                           game.gameName(), game.game());
            }
            gameDB.updateGame(updatedGame);
            return "";

        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }

    private boolean badJoinRequest(JoinRequest request, GameData game) {
        if (request.playerColor() == null) {return true;}
        if (request.gameID() == 0) {return true;}
        if (game == null) {return true;}     // means the gameID was invalid
        return !request.playerColor().equals("BLACK") & !request.playerColor().equals("WHITE");
    }
}
