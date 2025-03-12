package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDao implements GameDao {

    boolean noTable;

    public MySQLGameDao() {
        noTable = true;
    }

    @Override
    public int createGame(String whiteUsername, String blackUsername,
                          String gameName, ChessGame game) throws DataAccessException {
        if (noTable) { configureDatabase(); }

        var statement = ("INSERT INTO games (whiteUsername, blackUsername, gameName, game) " +
                         "VALUES (?, ?, ?, ?)");
        var json = new Gson().toJson(game);
        return executeUpdate(statement, whiteUsername, blackUsername, gameName, json);
    }

    @Override
    public HashMap<String, Collection<GameData>> listGames() throws DataAccessException {
        if (noTable) { configureDatabase(); }

        HashMap<String, Collection<GameData>> gameMap = new HashMap<>();
        var gameArray = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, game FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        gameArray.add(readGame(rs));
                    }
                }
                gameMap.put("games", gameArray);
            }
        } catch (Exception e) {
            throw new DataAccessException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return gameMap;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        if (noTable) { configureDatabase(); }

        return null;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        if (noTable) { configureDatabase(); }

    }

    @Override
    public void clear() throws DataAccessException {
        if (noTable) { configureDatabase(); }

        var statement = "DROP TABLE IF EXISTS games";
        executeUpdate(statement);
        this.noTable = true;
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var json = rs.getString("game");
        var game = new Gson().fromJson(json, ChessGame.class);
        return new GameData(id, whiteUsername, blackUsername, gameName, game);
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`id`),
            );
            """
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
        this.noTable = false;
    }

}
