/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender;

import com.sun.net.httpserver.HttpServer;
import io.github.devdoctor.BookRecommender.Handlers.*;
import io.github.devdoctor.BookRecommender.Handlers.LoginHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 */
public class HttpServerManager {
    // The httpServer that handles the API.
    private HttpServer httpServer;
    private final String API_ROOT = "/api";
    private final String API_VERSION = "/v1";
    private final String API_ROOT_PATH = API_ROOT + API_VERSION;

    public HttpServerManager() {
        // create the http server
        try {
            httpServer = HttpServer.create(new InetSocketAddress(Main.API_PORT), 0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        httpServer.setExecutor(null);

        httpServer.createContext(API_ROOT_PATH + "/books", new BookListHandler());

        httpServer.createContext(API_ROOT_PATH + "/book/", new BookHandler());

        httpServer.createContext(API_ROOT_PATH + "/authors", new AuthorListHandler());
        httpServer.createContext(API_ROOT_PATH + "/categories", new CategoryListHandler());

        // register a new user
        httpServer.createContext(API_ROOT_PATH + "/auth", new AuthenticationHandler());

        // login a user
        httpServer.createContext(API_ROOT_PATH + "/login", new LoginHandler());

        // for authentication
        httpServer.createContext(API_ROOT_PATH + "/register", new registerHandler());

        // Avvia il servizio
        httpServer.start();
        // LOG
        System.out.println("API Initiated Successfully on port: " + Main.API_PORT);
    }
}
