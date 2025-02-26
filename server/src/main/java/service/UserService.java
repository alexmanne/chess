package service;

import dataaccess.UserDao;
import model.UserData;

public class UserService {

    public UserDao userDB;

    public UserService(UserDao userDB) {
        this.userDB = userDB;
    }

    public UserData register(UserData registerRequest) {
        return userDB.createUser(registerRequest);
    }
//    public LoginResult login(LoginRequest loginRequest) {}
//    public void logout(LogoutRequest logoutRequest) {}
}
