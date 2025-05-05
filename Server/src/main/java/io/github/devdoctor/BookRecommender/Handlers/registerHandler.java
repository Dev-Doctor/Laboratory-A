/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Handlers;

import Utils.UrlUtils;
import Utils.UserUtils;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.devdoctor.BookRecommender.Database.Queries;
import io.github.devdoctor.BookRecommender.Database.UserDAO;
import io.github.devdoctor.BookRecommender.Main;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.Responses;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class registerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(!RequestMethods.POST.check(httpExchange.getRequestMethod())) {
            httpExchange.sendResponseHeaders(Responses.NOT_ALLOWED.code(), -1);
        }

        JsonObject requestBody = UrlUtils.parseJsonBody(httpExchange);

        if(!requestBody.has("first_name") || !requestBody.has("last_name") || !requestBody.has("password")
            || !requestBody.has("fiscal_code") || !requestBody.has("email")) {
            String response = UrlUtils.generateError("Invalid params", "params", "missing required params");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

        String name = requestBody.get("first_name").getAsString();
        String lastname = requestBody.get("last_name").getAsString();
        String fiscal_code = requestBody.get("fiscal_code").getAsString();
        String password = requestBody.get("password").getAsString();
        String email = requestBody.get("email").getAsString();

        if(name == null || name.isEmpty()) {
            String response = UrlUtils.generateError("Ivalid input", "name", "invalid");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

        if(lastname == null || lastname.isEmpty()) {
            String response = UrlUtils.generateError("Ivalid input", "lastname", "invalid");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

        if(password == null || password.isEmpty()) {
            String response = UrlUtils.generateError("Ivalid input", "password", "invalid");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

        if(email == null || email.isEmpty() || !UserUtils.checkEmail(email)) {
            String response = UrlUtils.generateError("Invalid input", "email", "The email provided is not registered.");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

        if(fiscal_code == null || fiscal_code.isEmpty() || !UserUtils.checkFiscalCode(fiscal_code)) {
            String response = UrlUtils.generateError("Invalid input", "fiscal_code", "The fiscal code provided is not registered.");
            UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
            return;
        }

        try(Connection conn = Main.dbConnectionHandler.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.USER_BY_EMAIL);

            // hash the password
            String hashed_pw = UserUtils.hashPassword(password);

            stmt.setString(1, email);
            ResultSet rs =stmt.executeQuery();

            // se esiste una mail dai errore
            if(rs.next()) {
                String response = UrlUtils.generateError("Invalid Input", "email", "Email already in use");
                UrlUtils.sendResponse(httpExchange, Responses.BAD_REQUEST, response);
                return;
            }

            stmt = conn.prepareStatement(Queries.ADD_USER);

            stmt.setString(1, name.toLowerCase());
            stmt.setString(2, lastname.toLowerCase());
            stmt.setString(3, fiscal_code);
            stmt.setString(4, email.toLowerCase());
            stmt.setString(5, hashed_pw);

            rs = stmt.executeQuery();
            if(rs.next()) {
                int user_id = rs.getInt("user_id");

                String response = "{\"token\": \"" + UserDAO.loginToken(conn, user_id) + "\"}";
                UrlUtils.sendResponse(httpExchange, Responses.CREATED, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            UrlUtils.sendResponse(httpExchange, Responses.INTERNAL_SERVER_ERROR, null);
        }
    }
}
