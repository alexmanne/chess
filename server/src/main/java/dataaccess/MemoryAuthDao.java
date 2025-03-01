package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDao implements AuthDao {

    /** Creates a HashMap that connects authToken to AuthData */
    final private HashMap<String, AuthData> tokens = new HashMap<>();

    @Override
    public void createAuth(AuthData auth) {
        tokens.put(auth.authToken(), auth);
    }

    @Override
    public AuthData getAuth(String authToken) {
        if (!tokens.containsKey(authToken)) {
            return new AuthData(null, null);
        }
        return tokens.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        tokens.remove(authToken);
    }

    @Override
    public void clear() {
        tokens.clear();
    }
}
