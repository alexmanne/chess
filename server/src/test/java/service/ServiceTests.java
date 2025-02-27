package service;

import dataaccess.*;
import model.request.RegisterRequest;
import model.result.RegisterResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import service.UserService;

public class ServiceTests {

    UserService userService;
    GameService gameService;

    @BeforeEach
    public void createServices() {
        UserDao userDB = new MemoryUserDao();
        AuthDao authDB = new MemoryAuthDao();
        GameDao gameDB = new MemoryGameDao();

        this.userService = new UserService(userDB, authDB, gameDB);
        this.gameService = new GameService(gameDB);
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

}
