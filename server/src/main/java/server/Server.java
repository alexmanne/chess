package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.request.LoginRequest;
import model.request.RegisterRequest;
import service.*;
import spark.*;

public class Server {

    private final UserService userService;
    private final GameService gameService;

    public Server() {
        UserDao userDB = new MemoryUserDao();
        AuthDao authDB = new MemoryAuthDao();
        GameDao gameDB = new MemoryGameDao();

        this.userService = new UserService(userDB, authDB, gameDB);
        this.gameService = new GameService(gameDB);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
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

    private Object clear(Request req, Response res) throws DataAccessException {
        return userService.clear();
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        res.status(ex.StatusCode());
        res.body(ex.toJson());
    }
}

