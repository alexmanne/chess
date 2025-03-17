package server;

import model.request.LoginRequest;
import model.request.RegisterRequest;
import model.result.LoginResult;
import model.result.RegisterResult;

import dataaccess.DataAccessException;

import com.google.gson.Gson;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public RegisterResult register(RegisterRequest request) {
        // serialize to json
        // create an http request and put the json in the body
        // call the http server
        // receive the http result
        // convert back into register request

        return null;
    }

    public LoginResult login(LoginRequest request) {
        return null;
    }

//    public Pet addPet(Pet pet) throws ResponseException {
//        var path = "/pet";
//        // Make request does stuff to turn it to http
//        // pet is the java object to go to the body of the http request
//        // the class is the class it is expecting to receive so it can serialize in json
//        return this.makeRequest("POST", path, pet, Pet.class);
//    }

    // Code from petshop/shared/src/main/server/client.ServerFacade.java

    private <T> T makeRequest(String method, String path, Object request,
                              Class<T> responseClass) throws DataAccessException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

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
