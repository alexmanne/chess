package dao;

import dataaccess.*;
import model.UserData;
import model.request.RegisterRequest;
import model.result.RegisterResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTests {

    UserDao userDB = new MySQLUserDAO();
    AuthDao authDB = new MySQLAuthDao();
    GameDao gameDB = new MySQLGameDao();

    @BeforeEach
    public void createServices() throws DataAccessException {
        userDB.clear();
    }

    @Test
    public void positiveCreateUser() {
        UserData user = new UserData("alex", "1234", "am@gmail.com");
        assertDoesNotThrow(() -> userDB.createUser(user));
    }

    @Test
    public void negativeCreateUser() throws DataAccessException {
        UserData user = new UserData("alex", "1234", "am@gmail.com");
        UserData copyUser = new UserData("alex", "1234", "am@gmail.com");

        userDB.createUser(user);
        assertThrows(DataAccessException.class, () -> {
            userDB.createUser(copyUser);
        });
    }

    @Test
    public void positiveGetUser() throws DataAccessException {
        UserData user = new UserData("alex", "1234", "am@gmail.com");
        userDB.createUser(user);
        UserData checkUser = userDB.getUser("alex");
        assertEquals(user.username(), checkUser.username());
        assertEquals(user.password(), checkUser.password());
    }

    @Test
    public void negativeGetUser() throws DataAccessException {
        UserData user = new UserData("alex", "1234", "am@gmail.com");
        userDB.createUser(user);
        UserData newUser = userDB.getUser("not alex");
        assertNull(newUser.username());
    }

    @Test
    public void positiveClear() {
        assertDoesNotThrow(() -> userDB.clear());
    }

}
