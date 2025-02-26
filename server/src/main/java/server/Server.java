package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import service.*;
import spark.*;

public class Server {

    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;

    public Server(UserService userService,
                  AuthService authService,
                  GameService gameService) {
        this.userService = userService;
        this.authService = authService;
        this.gameService = gameService;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object register(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        user = userService.register(user);
        return new Gson().toJson(user);
    }
}

