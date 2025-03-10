package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDao implements GameDao {

    private int nextId = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public int createGame(String whiteUsername, String blackUsername,
                           String gameName, ChessGame game) {
        GameData newGame = new GameData(nextId++,
                                        whiteUsername,
                                        blackUsername,
                                        gameName,
                                        game);
        games.put(newGame.gameID(), newGame);
        return newGame.gameID();
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
