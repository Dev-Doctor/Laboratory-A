package io.github.devdoctor.BookRecommender.utility;

import com.google.gson.Gson;
import io.github.devdoctor.BookRecommender.CommonObjects.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

            int status = conn.getResponseCode();

            System.out.println(status);

            if(status == 200) {
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

}
