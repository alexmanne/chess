package service;

import dataaccess.MemoryGameDao;

public class GameService {

    public MemoryGameDao gameDB;

    public GameService(MemoryGameDao gameDB) {
        this.gameDB = gameDB;
    }


}
