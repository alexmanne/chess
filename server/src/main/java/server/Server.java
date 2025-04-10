package server;

import com.google.gson.Gson;
import dataaccess.*;
import exception.DataAccessException;
import model.request.*;
import model.result.CreateResult;
import model.result.ListResult;
import server.websocket.WebSocketHandler;
import service.*;
import spark.*;

public class Server {

    private final UserService userService;
    private final GameService gameService;
    private final AuthDao authDB;
    private final GameDao gameDB;

    public Server() {
        UserDao userDB = new MySQLUserDAO();
        authDB = new MySQLAuthDao();
        gameDB = new MySQLGameDao();

        this.userService = new UserService(userDB, authDB, gameDB);
        this.gameService = new GameService(authDB, gameDB);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        WebSocketHandler handler = new WebSocketHandler(authDB, gameDB, gameService);

        Spark.webSocket("/ws", handler);

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.post("/game", this::createGame);
        Spark.get("/game", this::listGames);
        Spark.put("/game", this::joinGame);
        Spark.delete("/session", this::logout);
        Spark.delete("/db", this::clear);
        Spark.exception(DataAccessException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object register(Request req, Response res) throws DataAccessException {
        var request = new Gson().fromJson(req.body(), RegisterRequest.class);
        var result = userService.register(request);
        return new Gson().toJson(result);
    }

    private Object login(Request req, Response res) throws DataAccessException {
        var request = new Gson().fromJson(req.body(), LoginRequest.class);
        var result = userService.login(request);
        return new Gson().toJson(result);
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        return userService.logout(authToken);
    }

    private Object createGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        var httpRequest = new Gson().fromJson(req.body(), CreateJSON.class);
        CreateRequest request = new CreateRequest(httpRequest.gameName(), authToken);
        CreateResult result = gameService.createGame(request);
        return new Gson().toJson(result);
    }

    private Object listGames(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        ListResult result = gameService.listGames(authToken);
        return new Gson().toJson(result);
    }

    private Object joinGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("Authorization");
        var httpRequest = new Gson().fromJson(req.body(), JoinJSON.class);
        JoinRequest request = new JoinRequest(authToken, httpRequest.playerColor(), httpRequest.gameID());
        return gameService.joinGame(request);
    }

    private Object clear(Request req, Response res) throws DataAccessException {
        return userService.clear();
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        res.status(ex.getStatusCode());
        res.body(ex.toJson());
    }
}

