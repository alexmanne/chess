package dataaccess;

import model.UserData;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDao implements UserDao{

    final private List<UserData> users = new ArrayList<>()

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }
}
