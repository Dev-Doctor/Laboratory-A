package io.github.devdoctor.BookRecommender.Handlers;

import Utils.UrlUtils;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.Database.UserDAO;
import io.github.devdoctor.BookRecommender.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.enums.Responses;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (!RequestMethods.POST.check(httpExchange.getRequestMethod())) {
            UrlUtils.sendResponse(httpExchange, Responses.NOT_ALLOWED); // Method Not Allowed
        }

        JsonObject jsonBody = UrlUtils.parseJsonBody(httpExchange);

        if (!jsonBody.has("email") || !jsonBody.has("password")) {
            String response = UrlUtils.generateError("Invalid params", "params", "missing required params");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

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
