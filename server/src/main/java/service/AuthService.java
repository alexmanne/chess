package service;

import dataaccess.MemoryAuthDao;

public class AuthService {

    public MemoryAuthDao authDB;

    public AuthService(MemoryAuthDao authDB) {
        this.authDB = authDB;
    }

}
