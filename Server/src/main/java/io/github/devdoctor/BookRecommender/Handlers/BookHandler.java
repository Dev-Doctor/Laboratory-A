package io.github.devdoctor.BookRecommender.Handlers;

import Utils.NumberUtils;
import Utils.UrlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.Database.UserDAO;
import io.github.devdoctor.BookRecommender.Objets.Book;
import io.github.devdoctor.BookRecommender.Objets.BookSearchOptions;
import io.github.devdoctor.BookRecommender.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.enums.Responses;
import org.apache.catalina.util.ToStringUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class BookHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (RequestMethods.GET.check(httpExchange.getRequestMethod())) {
            String path = httpExchange.getRequestURI().getPath();
            
            String bookIdstr = path.substring(path.lastIndexOf('/') + 1);
            
            if(NumberUtils.isInteger(bookIdstr)) {
                Book book = UserDAO.getBookDetails(Integer.valueOf(bookIdstr));
                Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                String response = json.toJson(book);
                UrlUtils.sendJsonResponse(httpExchange, Responses.OK, response);
            } else {
                UrlUtils.sendJsonResponse(httpExchange, Responses.BAD_REQUEST, UrlUtils.generateError("Missing Input", "Book ID", "You must provide a book id"));
            }
        } else {
            UrlUtils.sendResponse(httpExchange, Responses.NOT_ALLOWED);
        }
    }

    public void haandle(HttpExchange httpExchange) throws IOException {
        if (RequestMethods.GET.check(httpExchange.getRequestMethod())) {

            Map<String, String> params = UrlUtils.parseQuery(httpExchange.getRequestURI());

            BookSearchOptions.Builder searchParams = BookSearchOptions.builder();

            if(params.containsKey("id") && NumberUtils.isInteger(params.get("id"))) {
                searchParams.id(Integer.parseInt(params.get("id")));
            }

            // set the title
            if(params.containsKey("title")) {
                searchParams.title(params.get("title"));
            }

            // set the author
            if(params.containsKey("author")) {
                searchParams.addAuthor(params.get("author"));
            }

            // set the category
            if(params.containsKey("category")) {
                searchParams.addCategory(params.get("category"));
            }

            // set the publisher
            if(params.containsKey("publisher")) {
                searchParams.publisher(params.get("publisher"));
            }

            BookSearchOptions searchOptions = searchParams.build();

            String response = UserDAO.getBook(searchOptions);

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
