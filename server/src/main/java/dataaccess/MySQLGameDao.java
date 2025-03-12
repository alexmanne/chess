package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static dataaccess.DatabaseManager.executeUpdate;

public class MySQLGameDao implements GameDao {

    boolean noTable;

    public MySQLGameDao() {
        noTable = true;
    }

    @Override
    public int createGame(String whiteUsername, String blackUsername,
                          String gameName, ChessGame game) throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        var statement = ("INSERT INTO games (whiteUsername, blackUsername, gameName, game) " +
                         "VALUES (?, ?, ?, ?)");
        var json = new Gson().toJson(game);
        return executeUpdate(statement, whiteUsername, blackUsername, gameName, json);
    }

    @Override
    public HashMap<String, Collection<GameData>> listGames() throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        HashMap<String, Collection<GameData>> gameMap = new HashMap<>();
        var gameArray = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteUsername, blackUsername, " +
                            "gameName, game FROM games";
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
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id, whiteUsername, blackUsername, " +
                            "gameName, game FROM games WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

        var statement = ("UPDATE games SET whiteUsername = ?, blackUsername = ?, " +
                         "gameName = ?, game = ? WHERE id = ?");
        var json = new Gson().toJson(game.game());
        executeUpdate(statement, game.whiteUsername(), game.blackUsername(),
                                 game.gameName(), json, game.gameID());
    }

    @Override
    public void clear() throws DataAccessException {
        if (noTable) {
            DatabaseManager.configureDatabase(createStatements);
            this.noTable = false;
        }

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

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`id`)
            );
            """
    };
}
