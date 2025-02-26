package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDao {

    /** Gets and lists all GameData. */
    Collection<GameData> listGames() throws DataAccessException;

    /** Retrieve a specified game with the given game ID. */
    GameData getGame(int gameID) throws DataAccessException;

    /** Creates and stores GameData or
     * Replace the game with the same gameID */
    void updateGame(GameData game) throws DataAccessException;

}
