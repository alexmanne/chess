package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public interface GameDao {

    /** Creates and stores GameData */
    void createGame(GameData game);

    /** Gets and lists all GameData. */
    HashMap<String, Collection<GameData>> listGames();

    /** Retrieve a specified game with the given game ID. */
    GameData getGame(int gameID);

    /** Replace the game with the same gameID */
    void updateGame(GameData game);

    /** Clears all data */
    void clear();

}
