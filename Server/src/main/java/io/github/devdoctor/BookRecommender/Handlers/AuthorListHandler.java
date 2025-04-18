package io.github.devdoctor.BookRecommender.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.Database.UserDAO;
import io.github.devdoctor.BookRecommender.enums.RequestMethods;

import java.io.IOException;
import java.io.OutputStream;

public class AuthorListHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (RequestMethods.GET.check(httpExchange.getRequestMethod())) {

            String response = UserDAO.getAllAuthors();

            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.getBytes().length);

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            httpExchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
}
