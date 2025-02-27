package dataaccess;

import model.AuthData;

import java.util.UUID;

public interface AuthDao {

    /** Create a new AuthToken */
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    /** Creates and stores AuthData. */
    void createAuth(AuthData auth);

    /** Returns the AuthData based on authToken. */
    AuthData getAuth(String authToken);

    /** Deletes the AuthData given an authToken. */
    void deleteAuth(String authToken);

    /** Clears all data */
    void clear();
}
