package server;

import exception.DataAccessException;
import model.request.CreateRequest;
import model.request.JoinRequest;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.*;
import org.junit.jupiter.api.*;
import serverclass.Server;
import sharedserver.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private String validAuthToken;

    @BeforeAll
    public static void init() {
        facade = new ServerFacade();
    }

    @BeforeEach
    public void setUp() throws DataAccessException {
        server = facade.server;
        facade.clear();
        RegisterRequest request1 = new RegisterRequest("genemann", "1234",
                "am@gmail.com");
        RegisterResult result1 = facade.register(request1);
        validAuthToken = result1.authToken();
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void goodRegisterTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                "am@gmail.com");

        RegisterResult goodResult = facade.register(goodRequest);
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

        facade.register(goodRequest);

        assertThrows(DataAccessException.class, () -> {
            facade.register(alreadyTakenRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            facade.register(badRequest);
        });
    }

    @Test
    public void goodLoginTest() throws DataAccessException {

        LoginRequest goodRequest = new LoginRequest("genemann", "1234");
        LoginResult goodResult = facade.login(goodRequest);

        assertEquals("genemann", goodResult.username());
        assertNotNull(goodResult.authToken(), "Result did not return auth String");

    }

    @Test
    public void negativeLoginTest() throws DataAccessException {

        LoginRequest goodRequest = new LoginRequest("genemann", "1234");
        LoginRequest badRequest = new LoginRequest("not a user", "1234");
        LoginRequest wrongPass = new LoginRequest("genemann", "5678");

        facade.login(goodRequest);

        assertThrows(DataAccessException.class, () -> {
            facade.login(badRequest);
        });
        assertThrows(DataAccessException.class, () -> {
            facade.login(wrongPass);
        });
    }

    @Test
    public void goodLogoutTest() throws DataAccessException {
        LoginRequest request1 = new LoginRequest("genemann", "1234");
        LoginResult result1 = facade.login(request1);

        String authToken1 = result1.authToken();
        assertDoesNotThrow(() -> facade.logout(authToken1));
    }

    @Test
    public void negativeLogoutTest() throws DataAccessException {
        LoginRequest request1 = new LoginRequest("genemann", "1234");
        LoginResult result1 = facade.login(request1);

        String authToken1 = result1.authToken();
        facade.logout(authToken1);

        assertThrows(DataAccessException.class, () -> {
            facade.logout("notAnAuthToken");
        });

    }

    @Test
    public void goodCreateGameTest() throws DataAccessException {
        CreateRequest goodRequest = new CreateRequest("BestGame", validAuthToken);
        CreateRequest checkGameIDReq = new CreateRequest("2", validAuthToken);

        facade.createGame(goodRequest);
        CreateResult checkGameIDRes = facade.createGame(checkGameIDReq);

        assertEquals(2, checkGameIDRes.gameID());
    }

    @Test
    public void negativeCreateGameTest() throws DataAccessException {
        CreateRequest goodRequest = new CreateRequest("BestGame", validAuthToken);
        CreateRequest emptyRequest = new CreateRequest(null, validAuthToken);
        CreateRequest invalidAuth = new CreateRequest("gameName", "notAnAuth");

        facade.createGame(goodRequest);

        assertThrows(DataAccessException.class, () -> {
            facade.createGame(emptyRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            facade.createGame(invalidAuth);
        });
    }

    @Test
    public void goodListGamesTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        CreateRequest request2 = new CreateRequest("BetterGame", validAuthToken);
        CreateRequest request3 = new CreateRequest("GoodGame", validAuthToken);
        facade.createGame(request1);
        facade.createGame(request2);
        facade.createGame(request3);

        ListResult listResult = facade.listGames(validAuthToken);

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
        facade.createGame(request1);
        facade.createGame(request2);
        facade.createGame(request3);

        facade.listGames(validAuthToken);

        assertThrows(DataAccessException.class, () -> {
            facade.listGames("notAnAuthToken");
        });
    }

    @Test
    public void goodJoinGameTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        facade.createGame(request1);

        JoinRequest goodRequest = new JoinRequest(validAuthToken, "WHITE", 1);
        assertDoesNotThrow(() -> facade.joinGame(goodRequest));
    }

    @Test
    public void negativeJoinGameTest() throws DataAccessException {
        CreateRequest request1 = new CreateRequest("BestGame", validAuthToken);
        facade.createGame(request1);

        JoinRequest goodRequest = new JoinRequest(validAuthToken, "WHITE", 1);
        facade.joinGame(goodRequest);

        JoinRequest badRequest = new JoinRequest(validAuthToken, "notAColor", 1);
        JoinRequest wrongGameID = new JoinRequest(validAuthToken, "BLACK", 3);
        JoinRequest invalidAuth = new JoinRequest("notAnAuthToken", "BLACK", 1);
        JoinRequest alreadyTaken = new JoinRequest(validAuthToken, "WHITE", 1);

        assertThrows(DataAccessException.class, () -> {
            facade.joinGame(badRequest);
        });

        assertThrows(DataAccessException.class, () -> {
            facade.joinGame(wrongGameID);
        });

        assertThrows(DataAccessException.class, () -> {
            facade.joinGame(invalidAuth);
        });

        assertThrows(DataAccessException.class, () -> {
            facade.joinGame(alreadyTaken);
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

        RegisterResult goodResult = facade.register(goodRequest);
        RegisterResult goodResult1 = facade.register(goodRequest1);
        RegisterResult goodResult2 = facade.register(goodRequest2);

        facade.clear();

        assertEquals("one", goodResult.username(), "Did not remove user 'one");
        assertEquals("two", goodResult1.username(), "Did not remove user 'two");
        assertEquals("three", goodResult2.username(), "Did not remove user 'three");

    }

    @Test
    public void negativeClearTest() throws DataAccessException {

        RegisterRequest goodRequest = new RegisterRequest("one", "1234",
                "am@gmail.com");

        facade.register(goodRequest);

        assertDoesNotThrow(() -> facade.clear());

    }

}
