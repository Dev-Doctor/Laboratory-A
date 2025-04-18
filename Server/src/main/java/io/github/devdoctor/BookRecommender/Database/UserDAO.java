/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Database;

import Utils.QueriesUtils;
import Utils.UserUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.devdoctor.BookRecommender.CommonObjects.Author;
import io.github.devdoctor.BookRecommender.CommonObjects.Book;
import io.github.devdoctor.BookRecommender.Main;
import io.github.devdoctor.BookRecommender.Objets.BookSearchOptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDAO {
    /**
     * @param limit
     * @param offset
     * @return
     */
    public static String getBooks(int limit, int offset) {
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.ALL_BOOKS_LIMIT)) {
            List<Book> books = new ArrayList<>();

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("editor_name"),
                        rs.getFloat("price"),
                        rs.getDate("publish_date")
                ));
            }

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            return gson.toJson(books);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Book getBookDetails(int bookId) {
        Book book = new Book();

        try (Connection conn = Main.dbConnectionHandler.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.BOOK_BY_ID);
            stmt.setInt(1, bookId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                book.setId(bookId);
                book.setTitle(rs.getString("title"));
                book.setPublisher(rs.getString("editor_name"));
                book.setPrice(rs.getFloat("price"));
                book.setDescription(rs.getString("description"));
                book.setPublish_date(rs.getDate("publish_date"));

                book.setAuthors(getAuthorsByBookId(bookId));
                book.setCategories(getCategoriesByBookId(bookId));
            }

            return book;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getCategoriesByBookId(int bookId) {
        ArrayList<String> categories = new ArrayList<>();
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.CATEGORIES_BY_BOOK_ID)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static ArrayList<Author> getAuthorsByBookId(int bookId) {
        ArrayList<Author> authors = new ArrayList<>();
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.AUTHORS_BY_BOOK_ID)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    //    ############### NOT COMPLETE !!!! ###############
    public static String getBook(BookSearchOptions options) {
        StringBuilder query = new StringBuilder("SELECT b.book_id, e.editor_name, b.title, b.price, b.publish_date FROM books b JOIN editors e on b.editor = e.editor_id WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = Main.dbConnectionHandler.getConnection()) {
            if (options.hasNothing()) {
                return "{\"error\":\"Missing a parameter\"}";
            }

            if (options.hasId()) {
                query.append(" AND b.book_id = ?");
                params.add(options.getId());
            }

            if (options.hasTitle()) {
                query.append(Queries.QueriesPieces.SEARCH_BOOKS_TITLE);
                params.add("%" + options.getTitle() + "%");
            }

            System.out.println(query.toString());

            PreparedStatement stmt = QueriesUtils.prepareStatement(conn, query.toString(), params);

            System.out.println(stmt.toString());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("editor_name"),
                        rs.getFloat("price"),
                        rs.getDate("publish_date")
                ));
            }

            Gson gson = new Gson();
            return gson.toJson(books);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAllAuthors() {
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.ALL_AUTHORS)) {

            ResultSet rs = stmt.executeQuery();
            ArrayList<Author> authors = new ArrayList<>();

            while (rs.next()) {
                authors.add(new Author(
                        rs.getInt("author_id"),
                        rs.getString("author_name")
                ));
            }

            Gson gson = new Gson();
            return gson.toJson(authors);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getAllCategories() {
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.ALL_CATEGORIES)) {

            ResultSet rs = stmt.executeQuery();
            ArrayList<String> categories = new ArrayList<>();

            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }

            Gson gson = new Gson();
            return gson.toJson(categories);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Si occupa di validare la richiesta di login dell'utente.
     *
     * @param email    l'email dell'utente che sta facendo l'accesso.
     * @param password la password dell'utente che sta facendo l'accesso.
     * @return il login token se l'accesso e' stato un successo o null se e' stato incontrato un problema.
     */
    public static String login(String email, String password) {
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.USER_BY_EMAIL)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (!UserUtils.checkPassword(password, rs.getString("password_hash"))) {
                    return null;
                }
                int userId = rs.getInt("user_id");
                return loginToken(conn, userId);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //  ####################    INCOMPLETE      ###################
    public static String loginWithToken(String token) {
        try (Connection conn = Main.dbConnectionHandler.getConnection();
             PreparedStatement stmt = conn.prepareStatement(Queries.USER_BY_TOKEN)) {
            stmt.setString(1, token);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date date = rs.getDate("expires_at");
                if (date.before(new Date())) {
                    return null;
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Si occupa di generare un login token che viene aggiunto al database per un determinato utente.
     *
     * @param conn    la connessione al database.
     * @param user_id l'id dell'utente che ha richiesto il token.
     * @return il token generato o null se c'e' stato un errore.
     */
    public static String loginToken(Connection conn, int user_id) {
        String token = generateToken();
        try (PreparedStatement stmt = conn.prepareStatement(Queries.CREATE_LOGIN_TOKEN);) {
            stmt.setString(1, token);
            stmt.setInt(2, user_id);
            stmt.executeUpdate();
            return token;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generates a login token.
     *
     * @return the generated token
     */
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
