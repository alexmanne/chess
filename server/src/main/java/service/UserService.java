package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.RegisterResult;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final UserDao userDB;
    private final AuthDao authDB;
    private final GameDao gameDB;

    public UserService(UserDao userDB, AuthDao authDB, GameDao gameDB) {
        this.userDB = userDB;
        this.authDB = authDB;
        this.gameDB = gameDB;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {

        if (badRegisterRequest(registerRequest)) {
            throw new DataAccessException(400, "Error: bad request");
        }

        var checkUser = userDB.getUser(registerRequest.username());
        if (checkUser.username() != null) {
            throw new DataAccessException(403, "Error: already taken");
        }

        try {
            UserData user = new UserData(registerRequest.username(), registerRequest.password(),
                    registerRequest.email());
            userDB.createUser(user);

            var newToken = AuthDao.generateToken();
            AuthData auth = new AuthData(user.username(), newToken);
            authDB.createAuth(auth);

            return new RegisterResult(auth.username(), auth.authToken());

        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }

    /** Returns true if any part of registerRequest is empty*/
    private boolean badRegisterRequest(RegisterRequest registerRequest) {
        if (registerRequest.username() == null |
            registerRequest.password() == null |
            registerRequest.email() == null) {
            return true;
        }

        boolean answer = registerRequest.username().isEmpty();
        if (registerRequest.password().isEmpty()) {answer = true;}
        if (registerRequest.email().isEmpty()) {answer = true;}

        return answer;
    }

    public String clear() throws DataAccessException {
        try {
            userDB.clear();
            authDB.clear();
            gameDB.clear();
            return "";
        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        UserData user = userDB.getUser(loginRequest.username());
        if (user.username() == null) {
            throw new DataAccessException(401, "Error: unauthorized");
        }

        var hashedPassword = user.password();
        if (!BCrypt.checkpw(loginRequest.password(), hashedPassword)) {
            throw new DataAccessException(401, "Error: unauthorized");
        }

        try {
            String newToken = AuthDao.generateToken();
            AuthData auth = new AuthData(user.username(), newToken);
            authDB.createAuth(auth);
            return new LoginResult(auth.username(), auth.authToken());
        } catch (Throwable e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
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
