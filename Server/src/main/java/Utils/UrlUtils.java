package Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import io.github.devdoctor.BookRecommender.Objets.Errors;
import io.github.devdoctor.BookRecommender.enums.Responses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class UrlUtils {
    public static Map<String, String> parseQuery(URI url) {
        String query = url.getQuery();
        Map<String, String> map = new HashMap<>();

        if (query == null) {
            return map;
        }

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }

    public static JsonObject parseJsonBody(HttpExchange httpExchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), "utf-8"));
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        reader.close();
        return new Gson().fromJson(requestBody.toString(), JsonObject.class);
    }

    public static void sendResponse(HttpExchange httpExchange, Responses response) throws IOException {
        sendResponse(httpExchange, response, null);
    }

    public static void sendJsonResponse(HttpExchange httpExchange, Responses response_code, String response) throws IOException {
        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(httpExchange, response_code, response);
    }

    public static void sendResponse(HttpExchange httpExchange, Responses response_code, String response) throws IOException {
        if(response == null || response.isEmpty()) {
            httpExchange.sendResponseHeaders(response_code.code(), -1);
        } else {
            httpExchange.sendResponseHeaders(response_code.code(), response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static String generateError(String errorType, String field, String s) {
        Errors.FieldError error = new Errors.FieldError(errorType, field, s);
        Gson gson = new Gson();
        return gson.toJson(error);
    }
}
