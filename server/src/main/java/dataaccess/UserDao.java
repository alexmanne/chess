package dataaccess;

import model.UserData;

public interface UserDao {

    /** Creates and stores a new user. */
    void createUser(UserData user);

    /** Returns the UserData based on username. */
    UserData getUser(String username);

    /** Clears users of all data */
    void clear();

}


