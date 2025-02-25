package model;

import java.util.UUID;

public record AuthData(String authToken, String username) {

    public static AuthData createAuth(String username) {
        String token = generateToken();
        return new AuthData(token, username);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
