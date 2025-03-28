package ui;

import model.request.*;
import model.result.*;

import exception.DataAccessException;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(int port) {
        this.serverUrl = "http://localhost:" + port;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        String path = "/user";
        return makeRequest("POST", path, request, null, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        String path = "/session";
        return makeRequest("POST", path, request, null, LoginResult.class);
    }

    public String logout(String authToken) throws DataAccessException {
        String path = "/session";
        return makeRequest("DELETE", path, null, authToken, String.class);
    }

    public ListResult listGames(String authToken) throws DataAccessException {
        String path = "/game";
        return makeRequest("GET", path, null, authToken, ListResult.class);
    }

    public CreateResult createGame(CreateRequest request) throws DataAccessException {
        String path = "/game";
        CreateJSON jsonBody = new CreateJSON(request.gameName());
        String authToken = request.authToken();
        return makeRequest("POST", path, jsonBody, authToken, CreateResult.class);
    }

    public String joinGame(JoinRequest request) throws DataAccessException {
        String path = "/game";
        String authToken = request.authToken();
        JoinJSON jsonBody = new JoinJSON(request.playerColor(), request.gameID());
        return makeRequest("PUT", path, jsonBody, authToken, String.class);
    }

    public String clear() throws DataAccessException {
        String path = "/db";
        return makeRequest("DELETE", path, null, null, String.class);
    }

    private <T> T makeRequest(String method, String path, Object request, String header,
                              Class<T> responseClass) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeHeader(header, http);
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (DataAccessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static void writeHeader(String header, HttpURLConnection http) {
        if (header != null) {
            http.addRequestProperty("Authorization", header);
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, DataAccessException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw DataAccessException.fromJson(respErr);
                }
            }

            throw new DataAccessException(status, "other failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
