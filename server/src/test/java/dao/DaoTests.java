package dao;

import dataaccess.*;
import model.AuthData;
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
        authDB.clear();
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
        String token = AuthDao.generateToken();
        AuthData auth = new AuthData("alex", token);
        authDB.createAuth(auth);

        AuthData checkAuth = authDB.getAuth(token);
        assertEquals(auth.username(), checkAuth.username());
        assertEquals(token, checkAuth.authToken());
    }

    @Test
    public void negativeGetAuth() throws DataAccessException {
        String token = AuthDao.generateToken();
        AuthData auth = new AuthData("alex", token);
        authDB.createAuth(auth);

        AuthData checkAuth = authDB.getAuth("Not an Auth");
        assertNull(checkAuth.username());
    }

    @Test
    public void positiveDeleteAuth() throws DataAccessException {
        UserData user = new UserData("alex", "1234", "am@gmail.com");
        userDB.createUser(user);
        UserData checkUser = userDB.getUser("alex");
        assertEquals(user.username(), checkUser.username());
        assertEquals(user.password(), checkUser.password());
    }

    @Test
    public void negativeDeleteAuth() throws DataAccessException {
        UserData user = new UserData("alex", "1234", "am@gmail.com");
        userDB.createUser(user);
        UserData newUser = userDB.getUser("not alex");
        assertNull(newUser.username());
    }

    @Test
    public void positiveClearAuth() {
        assertDoesNotThrow(() -> userDB.clear());
    }

}
