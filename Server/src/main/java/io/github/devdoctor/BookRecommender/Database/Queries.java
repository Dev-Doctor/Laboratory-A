/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Database;

/**
 * A list of all the static queries used, for easy editing.
 *
 * @author DevDoctor
 * @since 1.0
 */
public class Queries {
    public static final String ALL_AUTHORS = "SELECT author_name, author_id FROM authors ORDER BY author_name";
    public static final String ALL_CATEGORIES = "SELECT category_name FROM categories";
    public static final String ALL_AUTHORS_LIMIT = "SELECT author_name, author_id FROM authors ORDER BY author_name LIMIT ? offset ?";
    public static final String ALL_BOOKS_LIMIT = "SELECT b.book_id, b.title, e.editor_name, b.price, b.publish_date  FROM books b full join editors e on b.editor = e.editor_id order by b.book_id limit ? offset ?";
    public static final String ALL_CATEGORIES_LIMIT = "SELECT category_name FROM categories LIMIT ? offset ?";
    public static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE mail = ? AND password_hash = ?";
    public static final String INSERT_USER = "INSERT INTO users (mail, password_hash, isAdmin) VALUES (?, ?, ?)";
    public static final String SELECT_BOOKS = "SELECT b.book_id, b.title, e.editor_name, b.price, b.publish_date  FROM books b full join editors e on b.editor = e.editor_id order by b.book_id";
    public static final String SEARCH_BOOKS_BY_TITLE_SUB = "SELECT * FROM books b WHERE LOWER(b.title) like LOWER('%?%')";
    public static final String SEARCH_BOOKS_BY_YEAR = "SELECT * FROM books b WHERE (select extract (year from b.publish_date)) = ?";
    public static final String SEARCH_BOOKS_BY_PUBLISHER_SUB = "SELECT * FROM books b WHERE b.editor in (select e.editor_id from editors e where e.editor_name like '%?%')";
    public static final String SEARCH_BOOKS_BY_AUTHOR_SUB = "SELECT * FROM books b JOIN book_authors ba ON b.book_id = ba.book_id WHERE ba.author_id IN (select a.author_id FROM authors a WHERE a.author_name LIKE '%?%')";
    public static final String CREATE_LOGIN_TOKEN = "INSERT INTO user_tokens (token, user_id) VALUES (?, ?)";

    public static final String USER_BY_TOKEN = "SELECT * FROM users u JOIN user_tokens ut ON ut.user_id = u.user_id WHERE ut.\"token\" = ? LIMIT 1";
    public static final String USER_BY_EMAIL = "SELECT * FROM users WHERE email = ? LIMIT 1";
    public static final String ADD_USER = "INSERT INTO users (first_name, last_name, fiscal_code, email, password_hash) VALUES(?, ?, ?, ?, ?) RETURNING user_id";

    public static final String BOOK_BY_ID = "SELECT b.title, b.editor, b.price, b.description, b.publish_date, e.editor_name FROM books b JOIN editors e ON e.editor_id = b.editor WHERE book_id = ?";

    public static final String AUTHORS_BY_BOOK_ID = "SELECT a.author_id, a.author_name FROM authors a JOIN book_authors ba ON a.author_id = ba.author_id WHERE ba.book_id = ?";

    public static final String CATEGORIES_BY_BOOK_ID = "SELECT b.category_name FROM book_categories b WHERE b.book_id = ?";

    public static final String DELETE_TOKEN = "DELETE FROM user_tokens WHERE token = ?";
    public static final String DELETE_ALL_TOKENS_FOR_USER = "DELETE FROM user_tokens WHERE user_id = ?";

    public class QueriesPieces {
        public static final String SEARCH_BOOKS_TITLE = " AND LOWER(b.title) like LOWER(?)";
        public static final String SEARCH_BOOKS_YEAR = " AND (select extract (year from b.publish_date)) = ?";
        public static final String SEARCH_BOOKS_AUTHOR = " AND (select extract (year from b.publish_date)) = ?";
    }
}