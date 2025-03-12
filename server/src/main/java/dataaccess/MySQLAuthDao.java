package dataaccess;

import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static dataaccess.DatabaseManager.executeUpdate;

public class MySQLAuthDao implements AuthDao {

    boolean noTable;

    public MySQLAuthDao() {
        noTable = true;
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        var statement = "INSERT INTO tokens (username, authToken) VALUES (?, ?)";
        executeUpdate(statement, auth.username(), auth.authToken());
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM tokens WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    } else {
                        return new AuthData(null, null);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        var statement = "DELETE FROM tokens WHERE authToken=?";
        executeUpdate(statement, authToken);
    }

    @Override
    public void clear() throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        var statement = "DROP TABLE IF EXISTS tokens";
        executeUpdate(statement);
        this.noTable = true;
    }


    private AuthData readAuth(ResultSet rs) throws SQLException {
        var username = rs.getString("username");
        var authToken = rs.getString("authToken");
        return new AuthData(username, authToken);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  tokens (
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            );
            """
    };
}
