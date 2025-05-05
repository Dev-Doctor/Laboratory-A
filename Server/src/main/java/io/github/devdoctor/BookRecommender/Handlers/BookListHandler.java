package io.github.devdoctor.BookRecommender.Handlers;

import Utils.NumberUtils;
import Utils.UrlUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.Database.UserDAO;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class BookListHandler implements HttpHandler {
    public static int MAX_RESULTS = 100;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (RequestMethods.GET.check(httpExchange.getRequestMethod())) {
            Map<String, String> params = UrlUtils.parseQuery(httpExchange.getRequestURI());

            int offset = 0;

            if (params.containsKey("p")) {
                if(NumberUtils.isInteger(params.get("p"))) {
                    offset = Integer.parseInt(params.get("p"));
                }
            }

            String response = UserDAO.getBooks(MAX_RESULTS, offset * MAX_RESULTS);

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
