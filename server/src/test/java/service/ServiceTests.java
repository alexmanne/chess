package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.request.CreateRequest;
import model.request.JoinRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    UserDao userDB = new MemoryUserDao();
    AuthDao authDB = new MemoryAuthDao();
    GameDao gameDB = new MemoryGameDao();
    UserService userService = new UserService(userDB, authDB, gameDB);
    GameService gameService = new GameService(authDB, gameDB);
    String validAuthToken;

    public ServiceTests() throws DataAccessException {
    }

    @BeforeEach
    public void createServices() throws DataAccessException {
        userService.clear();
        RegisterRequest request1 = new RegisterRequest("genemann", "1234",
                "am@gmail.com");
        RegisterResult result1 = userService.register(request1);
        validAuthToken = result1.authToken();
    }

    @Test
    public void goodRegisterTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                                                          "am@gmail.com");

        RegisterResult goodResult = userService.register(goodRequest);
        assertEquals("one", goodResult.username());
        assertNotNull(goodResult.authToken(), "Result did not return auth String");

    }

    @Test
    public void negativeRegisterTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                "am@gmail.com");
        RegisterRequest alreadyTakenRequest = new RegisterRequest("one", "1234",
                "am@gmail.com");
        RegisterRequest badRequest = new RegisterRequest("", "1234",
                "am@gmail.com");

        userService.register(goodRequest);

        assertThrows(DataAccessException.class, () -> {
            userService.register(alreadyTakenRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            userService.register(badRequest);
        });
    }

    @Test
    public void goodLoginTest() throws DataAccessException {

        LoginRequest goodRequest = new LoginRequest("genemann", "1234");
        LoginResult goodResult = userService.login(goodRequest);

        assertEquals("genemann", goodResult.username());
        assertNotNull(goodResult.authToken(), "Result did not return auth String");

    }

    @Test
    public void negativeLoginTest() throws DataAccessException {

        LoginRequest goodRequest = new LoginRequest("genemann", "1234");
        LoginRequest badRequest = new LoginRequest("not a user", "1234");
        LoginRequest wrongPass = new LoginRequest("genemann", "5678");

        userService.login(goodRequest);

        assertThrows(DataAccessException.class, () -> {
            userService.login(badRequest);
        });
        assertThrows(DataAccessException.class, () -> {
            userService.login(wrongPass);
        });
    }

    @Test
    public void goodLogoutTest() throws DataAccessException {
        LoginRequest request1 = new LoginRequest("genemann", "1234");
        LoginResult result1 = userService.login(request1);

        String authToken1 = result1.authToken();
        userService.logout(authToken1);

        AuthData auth = authDB.getAuth(authToken1);
        assertNull(auth.authToken());

    }

    @Test
    public void negativeLogoutTest() throws DataAccessException {
        LoginRequest request1 = new LoginRequest("genemann", "1234");
        LoginResult result1 = userService.login(request1);

        String authToken1 = result1.authToken();
        userService.logout(authToken1);

        assertThrows(DataAccessException.class, () -> {
            userService.logout("notAnAuthToken");
        });

    }

    @Test
    public void goodCreateGameTest() throws DataAccessException {
        CreateRequest goodRequest = new CreateRequest("BestGame", validAuthToken);
        CreateRequest checkGameIDReq = new CreateRequest("2", validAuthToken);

        CreateResult goodResult = gameService.createGame(goodRequest);
        CreateResult checkGameIDRes = gameService.createGame(checkGameIDReq);

        assertEquals("BestGame", gameDB.getGame(goodResult.gameID()).gameName());
        assertEquals(2, checkGameIDRes.gameID());
    }

    @Test
    public void negativeCreateGameTest() throws DataAccessException {
        CreateRequest goodRequest = new CreateRequest("BestGame", validAuthToken);
        CreateRequest emptyRequest = new CreateRequest(null, validAuthToken);
        CreateRequest invalidAuth = new CreateRequest("gameName", "notAnAuth");

        gameService.createGame(goodRequest);

        assertThrows(DataAccessException.class, () -> {
            gameService.createGame(emptyRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            gameService.createGame(invalidAuth);
        });
    }

    @Test
    public void goodListGamesTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        CreateRequest request2 = new CreateRequest("BetterGame", validAuthToken);
        CreateRequest request3 = new CreateRequest("GoodGame", validAuthToken);
        gameService.createGame(request1);
        gameService.createGame(request2);
        gameService.createGame(request3);

        ListResult listResult = gameService.listGames(validAuthToken);

        ListOneGameResult verifyResult = new ListOneGameResult(1, null,
                                                               null, "BestGame");
        assertTrue(listResult.games().contains(verifyResult));

        // Verify the size
        assertEquals(3, listResult.games().size());

    }

    @Test
    public void negativeListGamesTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        CreateRequest request2 = new CreateRequest("BetterGame", validAuthToken);
        CreateRequest request3 = new CreateRequest("GoodGame", validAuthToken);
        gameService.createGame(request1);
        gameService.createGame(request2);
        gameService.createGame(request3);

        gameService.listGames(validAuthToken);

        assertThrows(DataAccessException.class, () -> {
            gameService.listGames("notAnAuthToken");
        });
    }

    @Test
    public void goodJoinGameTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        gameService.createGame(request1);

        JoinRequest goodRequest = new JoinRequest(validAuthToken, "WHITE", 1);
        gameService.joinGame(goodRequest);
        GameData game = gameDB.getGame(1);
        assertEquals("genemann", game.whiteUsername());
    }

    @Test
    public void negativeJoinGameTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        gameService.createGame(request1);

        JoinRequest goodRequest = new JoinRequest(validAuthToken, "WHITE", 1);
        gameService.joinGame(goodRequest);

        JoinRequest badRequest = new JoinRequest(validAuthToken, "notAColor", 1);
        JoinRequest wrongGameID = new JoinRequest(validAuthToken, "BLACK", 3);
        JoinRequest invalidAuth = new JoinRequest("notAnAuthToken", "BLACK", 1);
        JoinRequest alreadyTaken = new JoinRequest(validAuthToken, "WHITE", 1);

        assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(badRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(wrongGameID);
        });

        assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(invalidAuth);
        });

        assertThrows(DataAccessException.class, () -> {
            gameService.joinGame(alreadyTaken);
        });
    }

    @Test
    public void goodClearTest() throws DataAccessException {

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

    @Test
    public void negativeClearTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                "am@gmail.com");

        userService.register(goodRequest);

        userService.clear();

        assertNull(userDB.getUser("one").username());
    }

}
