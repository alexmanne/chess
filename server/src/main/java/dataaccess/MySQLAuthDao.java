package dataaccess;

import model.AuthData;

public class MySQLAuthDao implements AuthDao {

    boolean noTable;

    public MySQLAuthDao() {
        noTable = true;
    }

    @Override
    public void createAuth(AuthData auth) {

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void clear() {

    }
}
