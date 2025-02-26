package service;

import model.UserData;
import dataaccess.MemoryUserDao;

public class UserService {

    public UserData register(UserData registerRequest) {
        return MemoryUserDao::createUser(registerRequest);
    }
//    public LoginResult login(LoginRequest loginRequest) {}
//    public void logout(LogoutRequest logoutRequest) {}
}
