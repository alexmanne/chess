package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public interface GameDao {

    /** Creates and stores GameData */
    int createGame(String whiteUsername, String blackUsername,
                    String gameName, ChessGame game);

    /** Gets and lists all GameData. */
    HashMap<String, Collection<GameData>> listGames();

    /** Retrieve a specified game with the given game ID. */
    GameData getGame(int gameID);

    /** Replace the game with the same gameID */
    void updateGame(GameData game);

    /** Clears all data */
    void clear();

}
