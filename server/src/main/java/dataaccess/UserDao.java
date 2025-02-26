package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDao {

    /** Creates and stores a new user. */
    void createUser(UserData user);

    /** Returns the UserData based on username. */
    UserData getUser(String username);

}


