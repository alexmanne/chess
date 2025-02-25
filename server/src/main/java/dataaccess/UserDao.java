package dataaccess;

import model.UserData;

public interface UserDao {

    /** Creates and stores a new user. */
    void createUser(UserData user) throws DataAccessException;

    /** Returns the UserData based on username. */
    UserData getUser(String username) throws DataAccessException;

}


