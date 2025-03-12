package dao;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTests {

    UserDao userDB = new MySQLUserDAO();
    AuthDao authDB = new MySQLAuthDao();
    GameDao gameDB = new MySQLGameDao();
    String goodUsername;
    String goodToken;

    @BeforeEach
    public void createServices() throws DataAccessException {
        userDB.clear();
        authDB.clear();
        gameDB.clear();

        UserData user = new UserData("alex", "1234", "am@gmail.com");
        userDB.createUser(user);
        this.goodUsername = "alex";

        String token = AuthDao.generateToken();
        AuthData auth = new AuthData("alex", token);
        authDB.createAuth(auth);
        this.goodToken = token;

        ChessGame game = new ChessGame();
        String whiteUsername = "Alex";
        String gameName = "Our Game";

        gameDB.createGame(whiteUsername, null,
                          gameName, game);
    }

    @Test
    public void positiveCreateUser() {
        UserData user = new UserData("Ali", "1234", "am@gmail.com");
        assertDoesNotThrow(() -> userDB.createUser(user));
    }

    @Test
    public void negativeCreateUser() throws DataAccessException {
        UserData user = new UserData("Ali", "1234", "am@gmail.com");
        UserData copyUser = new UserData("alex", "1234", "am@gmail.com");

        userDB.createUser(user);
        assertThrows(DataAccessException.class, () -> {
            userDB.createUser(copyUser);
        });
    }

    @Test
    public void positiveGetUser() throws DataAccessException {
        UserData checkUser = userDB.getUser("alex");
        assertEquals("alex", checkUser.username());
        assertEquals("am@gmail.com", checkUser.email());
    }

    @Test
    public void negativeGetUser() throws DataAccessException {
        UserData newUser = userDB.getUser("not alex");
        assertNull(newUser.username());
    }

    @Test
    public void positiveClearUser() {
        assertDoesNotThrow(() -> userDB.clear());
    }

    @Test
    public void positiveCreateAuth() {
        String token = AuthDao.generateToken();
        AuthData auth = new AuthData("alex", token);
        assertDoesNotThrow(() -> authDB.createAuth(auth));
    }

    @Test
    public void negativeCreateAuth() throws DataAccessException {
        String token = AuthDao.generateToken();
        AuthData auth = new AuthData("alex", token);
        AuthData copyAuth = new AuthData("alex", token);

        authDB.createAuth(auth);
        assertThrows(DataAccessException.class, () -> {
            authDB.createAuth(copyAuth);
        });
    }

    @Test
    public void positiveGetAuth() throws DataAccessException {
        AuthData checkAuth = authDB.getAuth(this.goodToken);
        assertEquals("alex", checkAuth.username());
        assertEquals(this.goodToken, checkAuth.authToken());
    }

    @Test
    public void negativeGetAuth() throws DataAccessException {
        AuthData checkAuth = authDB.getAuth("Not an Auth");
        assertNull(checkAuth.username());
    }

    @Test
    public void positiveDeleteAuth() {
        assertDoesNotThrow(() -> authDB.deleteAuth(this.goodToken));
    }

    @Test
    public void negativeDeleteAuth() {
        assertDoesNotThrow(() -> authDB.deleteAuth("Not a token"));
    }

    @Test
    public void positiveClearAuth() {
        assertDoesNotThrow(() -> authDB.clear());
    }

    @Test
    public void positiveCreateGame() {
        ChessGame game = new ChessGame();
        String whiteUsername = "Alex";
        String blackUsername = "Ali";
        String gameName = "Our Game";

        assertDoesNotThrow(() -> gameDB.createGame(whiteUsername, blackUsername,
                                                   gameName, game));
        assertDoesNotThrow(() -> gameDB.createGame(null, null,
                                                   gameName, game), "Does not handle null usernames.");
    }

    @Test
    public void negativeCreateGame() {
        assertThrows(DataAccessException.class, () -> {
            gameDB.createGame(null, null, null, new ChessGame());
        });
    }

    @Test
    public void positiveGetGame() throws DataAccessException {
        GameData gameData = gameDB.getGame(1);
        assertEquals("Our Game", gameData.gameName());
    }

    @Test
    public void negativeGetGame() throws DataAccessException {
        assertNull(gameDB.getGame(3));
    }

    @Test
    public void positiveListGames() throws DataAccessException {
        HashMap<String, Collection<GameData>> gameMap = gameDB.listGames();

        GameData verifyGame = new GameData(1, "Alex", null,
                                           "Our Game", new ChessGame());
        assertTrue(gameMap.get("games").contains(verifyGame));
    }

    @Test
    public void negativeListGames() {
        assertDoesNotThrow(() -> gameDB.listGames());
    }

    @Test
    public void positiveUpdateGame() throws DataAccessException {
        GameData addBlackUsername = new GameData(1, "Alex", "Ali",
                                                 "Our Game", new ChessGame());
        gameDB.updateGame(addBlackUsername);
        GameData retrievedGame = gameDB.getGame(1);
        assertEquals("Ali", retrievedGame.blackUsername());
    }

    @Test
    public void negativeUpdateGame() {
        GameData nullGame = new GameData(1, null, null,
                                         null, null);
        assertThrows(DataAccessException.class, () -> {
            gameDB.updateGame(nullGame);
        });
    }

    @Test
    public void positiveClearGame() {
        assertDoesNotThrow(() -> gameDB.clear());
    }

}
