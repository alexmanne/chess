package service;

import chess.ChessGame;
import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;
import model.request.CreateRequest;
import model.result.CreateResult;
import model.result.RegisterResult;

public class GameService {

    private final UserDao userDB;
    private final AuthDao authDB;
    private final GameDao gameDB;

    public GameService(UserDao userDB, AuthDao authDB, GameDao gameDB) {
        this.userDB = userDB;
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

    public String logout(String authToken) throws DataAccessException {
        AuthData auth = authDB.getAuth(authToken);

        if (auth.authToken() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }

        try {
            authDB.deleteAuth(authToken);
            return "";
        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }


}
