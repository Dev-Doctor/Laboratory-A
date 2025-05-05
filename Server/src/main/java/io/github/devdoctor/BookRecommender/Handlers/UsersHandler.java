package io.github.devdoctor.BookRecommender.Handlers;

import Utils.UrlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.Responses;
import io.github.devdoctor.BookRecommender.Database.UserDAO;

import java.io.IOException;
import java.util.Map;

public class UsersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (RequestMethods.GET.check(httpExchange.getRequestMethod())) {
            Map<String, String> params = UrlUtils.parseQuery(httpExchange.getRequestURI());

            if (params.containsKey("e")) {
                String email = params.get("e");

                boolean exists = UserDAO.checkIfUserExists(email);
                UrlUtils.sendJsonResponse(httpExchange, Responses.OK, "{\"exists\":\"" + exists + "\"}");
            } else {
                UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST);
            }
        } else {
            UrlUtils.sendResponse(httpExchange, Responses.NOT_ALLOWED);
        }
    }
}
