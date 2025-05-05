package io.github.devdoctor.BookRecommender.Handlers;

import Utils.UrlUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.Database.UserDAO;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.Responses;
import io.github.devdoctor.BookRecommender.Objets.User;

import java.io.IOException;
import java.io.OutputStream;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (!RequestMethods.POST.check(httpExchange.getRequestMethod())) {
            UrlUtils.sendResponse(httpExchange, Responses.NOT_ALLOWED); // Method Not Allowed
            return;
        }

        JsonObject jsonBody = UrlUtils.parseJsonBody(httpExchange);
        if (jsonBody.has("email") && jsonBody.has("password")) {
            emailLogin(httpExchange, jsonBody);
            return;
        }

        if (jsonBody.has("token")) {
            tokenFetchData(httpExchange, jsonBody);
            return;
        }
    }

    private void tokenFetchData(HttpExchange httpExchange, JsonObject jsonBody) throws IOException {
        String token = jsonBody.get("token").getAsString();
        User user = UserDAO.loginWithToken(token);

        if (user == null) {
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, UrlUtils.generateError("token expired", "token", "token expired"));
            return;
        }

        Gson gson = new Gson();

        UrlUtils.sendResponse(httpExchange, Responses.OK, gson.toJson(user, User.class));
    }

    public void emailLogin(HttpExchange httpExchange, JsonObject jsonBody) throws IOException {
        String email = jsonBody.get("email").getAsString();
        String password = jsonBody.get("password").getAsString();

        String token = UserDAO.login(email, password);

        if (token != null) {
            String response = "{\"token\":\"" + token + "\"}";

            httpExchange.sendResponseHeaders(Responses.OK.code(), response.getBytes().length);

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST);
        }
    }
}
