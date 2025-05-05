package io.github.devdoctor.BookRecommender.utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.devdoctor.BookRecommender.CommonObjects.Book;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.Responses;
import io.github.devdoctor.BookRecommender.CommonObjects.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;

/**
 *
 * @author DevDoctor
 * @since 2.0
 */
public class APIUtils {
    public static final String BASE_URL = "http://localhost:8080/api";
    public static final String API_VERSION = "v1";
    public static final String BOOKS_ENDPOINT = "/books";
    public static final String BOOK_ENDPOINT = "/book/%s";
    public static final String USERS_ENDPOINT = "/users";
    public static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";

    private static String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        return content.toString();
    }

    public static Collection<Book> requestBooks(int page) {
        try {
            URL url = new URL(BASE_URL + "/" + API_VERSION + BOOKS_ENDPOINT + "?p=" + page);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            System.out.println(url.toString());

            if(Responses.OK.check(conn.getResponseCode())) {
                String response = readResponse(conn);

                Gson gson = new Gson();

                Collection<Book> books = gson.fromJson(response, FileUtils.BOOK_COLLECTION_TYPE);

                System.out.println(response);

                return books;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static boolean doesUserExist(User user) {
        if(user == null) {
            return false;
        }

        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(BASE_URL + "/" + API_VERSION + USERS_ENDPOINT + "?e=" + user.getEmail());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(RequestMethods.GET.getValue());
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();

            System.out.println(status);

            if(Responses.OK.check(status)) {
                String response = readResponse(conn);

                JsonElement jsonElement = JsonParser.parseString(response);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                return jsonObject.get("exists").getAsBoolean();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static Book fetchBookData(int book_id) {
        try {
            URL url = new URL(BASE_URL + "/" + API_VERSION + String.format(BOOK_ENDPOINT, String.valueOf(book_id)));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int status = conn.getResponseCode();

            System.out.println(status);

            if(status == 200) {
                String response = readResponse(conn);

                Gson gson = new Gson();
                System.out.println(response);
                return gson.fromJson(response, Book.class);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static String getUserToken(String email, String password) {
        try {
            URL url = new URL(BASE_URL + "/" + API_VERSION + LOGIN_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(RequestMethods.POST.getValue());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", email);
            jsonObject.addProperty("password", password);

            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes());

            os.flush();
            int status = conn.getResponseCode();
            System.out.println(status);
            if(Responses.OK.check(status)) {
                String response = readResponse(conn);
                JsonElement element = JsonParser.parseString(response);
                jsonObject = element.getAsJsonObject();

                return jsonObject.get("token").getAsString();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static User fetchUserData(String token) {
        try {
            URL url = new URL(BASE_URL + "/" + API_VERSION + LOGIN_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(RequestMethods.POST.getValue());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", token);

            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes());

            os.flush();
            int status = conn.getResponseCode();
            System.out.println(status);
            if(Responses.OK.check(status)) {
                String response = readResponse(conn);

                Gson gson = new Gson();

                return gson.fromJson(response, User.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean registerUser(User user) {
        try {
            URL url = new URL(BASE_URL + "/" + API_VERSION + REGISTER_ENDPOINT);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // set the headers
            conn.setRequestMethod(RequestMethods.POST.getValue());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setUseCaches(false);

            // prepare the body
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", user.getEmail());
            jsonObject.addProperty("first_name", user.getName());
            jsonObject.addProperty("last_name", user.getLastname());
            jsonObject.addProperty("fiscal_code", user.getFiscal_code());
            jsonObject.addProperty("password", user.getPassword());

            // write the body
            OutputStream os = conn.getOutputStream();
            os.write(jsonObject.toString().getBytes());
            os.flush();

            // get the status
            int status = conn.getResponseCode();
            System.out.println(status);

            // if server said OK
            if(Responses.OK.check(status)) {
                String response = readResponse(conn);
                JsonElement element = JsonParser.parseString(response);
                jsonObject = element.getAsJsonObject();
                String token = jsonObject.get("token").getAsString();
                if (token != null) {
                    // ############################## MISSING NIGGEr ########################################
                    return true;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
