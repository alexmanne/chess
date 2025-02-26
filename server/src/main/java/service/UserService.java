package service;

import dataaccess.*;
import model.AuthData;
import model.request.RegisterRequest;
import model.result.RegisterResult;
import model.UserData;

public class UserService {

    private final UserDao userDB;
    private final AuthDao authDB;

    public UserService(UserDao userDB, AuthDao authDB) {
        this.userDB = userDB;
        this.authDB = authDB;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {

        if (badRequest(registerRequest)) {
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

            return new RegisterResult(newToken, user.username());

        } catch (Exception e) {
            // Catch a generic exception
            throw new DataAccessException(500, e.getMessage());
        }
    }

    /** Returns true if any part of registerRequest is empty*/
    private boolean badRequest(RegisterRequest registerRequest) {
        boolean answer = false;
        if (registerRequest.username().isEmpty()) {answer = true;}
        if (registerRequest.password().isEmpty()) {answer = true;}
        if (registerRequest.email().isEmpty()) {answer = true;}

        return answer;
    }
//    public LoginResult login(LoginRequest loginRequest) {}
//    public void logout(LogoutRequest logoutRequest) {}
}
