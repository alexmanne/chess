package service;

import dataaccess.GameDao;

public class GameService {

    public GameDao gameDB;

    public GameService(GameDao gameDB) {
        this.gameDB = gameDB;
    }


}
