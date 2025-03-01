package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.request.CreateRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.CreateResult;
import model.result.LoginResult;
import model.result.RegisterResult;
import org.eclipse.jetty.util.log.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    UserDao userDB = new MemoryUserDao();
    AuthDao authDB = new MemoryAuthDao();
    GameDao gameDB = new MemoryGameDao();
    UserService userService = new UserService(userDB, authDB, gameDB);
    GameService gameService = new GameService(userDB, authDB, gameDB);
    String validAuthToken;

    @BeforeEach
    public void createServices() throws DataAccessException {
        userService.clear();
        RegisterRequest request1 = new RegisterRequest("genemann", "1234",
                "am@gmail.com");
        RegisterResult result1 = userService.register(request1);
        validAuthToken = result1.authToken();
    }

    @Test
    public void registerTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                                                          "am@gmail.com");
        RegisterRequest alreadyTakenRequest = new RegisterRequest("one", "1234",
                                                                  "am@gmail.com");
        RegisterRequest badRequest = new RegisterRequest("", "1234",
                                                         "am@gmail.com");

        RegisterResult goodResult = userService.register(goodRequest);
        assertEquals("one", goodResult.username());
        assertNotNull(goodResult.authToken(), "Result did not return auth String");

        assertThrows(DataAccessException.class, () -> {
            userService.register(alreadyTakenRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            userService.register(badRequest);
        });
    }

    @Test
    public void loginTest() throws DataAccessException {

        LoginRequest goodRequest = new LoginRequest("genemann", "1234");
        LoginRequest badRequest = new LoginRequest("not a user", "1234");
        LoginRequest wrongPass = new LoginRequest("genemann", "5678");

        LoginResult goodResult = userService.login(goodRequest);

        assertEquals("genemann", goodResult.username());
        assertNotNull(goodResult.authToken(), "Result did not return auth String");

        assertThrows(DataAccessException.class, () -> {
            userService.login(badRequest);
        });
        assertThrows(DataAccessException.class, () -> {
            userService.login(wrongPass);
        });
    }

    @Test
    public void logoutTest() throws DataAccessException {
        LoginRequest request1 = new LoginRequest("genemann", "1234");
        LoginResult result1 = userService.login(request1);

        String authToken1 = result1.authToken();
        userService.logout(authToken1);

        AuthData auth = authDB.getAuth(authToken1);
        assertNull(auth.authToken());

        assertThrows(DataAccessException.class, () -> {
            userService.logout("notAnAuthToken");
        });

    }

    @Test
    public void createGameTest() throws DataAccessException {
        CreateRequest goodRequest = new CreateRequest("BestGame", validAuthToken);
        CreateRequest checkGameIDReq = new CreateRequest("2", validAuthToken);
        CreateRequest emptyRequest = new CreateRequest(null, validAuthToken);
        CreateRequest invalidAuth = new CreateRequest("gameName", "notAnAuth");

        CreateResult goodResult = gameService.createGame(goodRequest);
        CreateResult checkGameIDRes = gameService.createGame(checkGameIDReq);

        assertEquals("BestGame", gameDB.getGame(goodResult.gameID()).gameName());
        assertEquals(2, checkGameIDRes.gameID());

        assertThrows(DataAccessException.class, () -> {
            gameService.createGame(emptyRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            gameService.createGame(invalidAuth);
        });
    }

    @Test
    public void clearTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                "am@gmail.com");
        RegisterRequest goodRequest1 = new RegisterRequest("two", "1234",
                "am@gmail.com");
        RegisterRequest goodRequest2 = new RegisterRequest("three", "1234",
                "am@gmail.com");

        RegisterResult goodResult = userService.register(goodRequest);
        RegisterResult goodResult1 = userService.register(goodRequest1);
        RegisterResult goodResult2 = userService.register(goodRequest2);

        userService.clear();

        assertEquals("one", goodResult.username(), "Did not remove user 'one");
        assertEquals("two", goodResult1.username(), "Did not remove user 'two");
        assertEquals("three", goodResult2.username(), "Did not remove user 'three");

    }

}
