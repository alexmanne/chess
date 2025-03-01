package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDao implements UserDao{

    /** Creates a HashMap that connects username to UserData*/
    final private HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void createUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        if (!users.containsKey(username)) {
            return new UserData(null, null, null);
        }
        return users.get(username);
    }

    @Override
    public void clear() {
        users.clear();
    }
}
