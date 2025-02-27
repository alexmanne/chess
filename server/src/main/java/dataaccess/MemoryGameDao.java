package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class MemoryGameDao implements GameDao {

    private int nextId = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void createGame(GameData game) {
        GameData newGame = new GameData(nextId++,
                                        game.whiteUsername(),
                                        game.blackUsername(),
                                        game.gameName(),
                                        game.game());
        games.put(newGame.gameID(), newGame);
    }

    @Override
    public HashMap<String, Collection<GameData>> listGames() {
        HashMap<String, Collection<GameData>> gameList = new HashMap<>();
        gameList.put("games", games.values());
        return gameList;
    }

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public void updateGame(GameData game) {
        int thisID = game.gameID();
        games.put(thisID, game);
    }

    @Override
    public void clear() {
        games.clear();
    }
}
