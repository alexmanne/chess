package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class MemoryGameDao implements GameDao {

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }
}
