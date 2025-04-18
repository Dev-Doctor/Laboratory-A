package io.github.devdoctor.BookRecommender.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.enums.RequestMethods;

import java.io.IOException;

public class AuthenticationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (RequestMethods.PUT.check(httpExchange.getRequestMethod())) {
            // register the user
        }
    }
}
