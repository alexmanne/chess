package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MySQLGameDao implements GameDao {


    @Override
    public int createGame(String whiteUsername, String blackUsername, String gameName, ChessGame game) {
        return 0;
    }

    @Override
    public HashMap<String, Collection<GameData>> listGames() {
        return null;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public void updateGame(GameData game) {

    }

    @Override
    public void clear() {

    }
}
