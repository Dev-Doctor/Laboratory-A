/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.utility;

import io.github.devdoctor.bookrecommender.Book;
import io.github.devdoctor.bookrecommender.LoadedData;
import io.github.devdoctor.bookrecommender.Review;

import java.util.*;

/**
 * The {@code BookUtils} class provides a collection of static utility methods
 * for searching and managing {@link Book} objects, including operations like
 * searching by title, author, year, or UUID, and handling book reviews.
 *
 * <p>This class cannot be instantiated.</p>
 * <p><b>Important:</b> The constructor is private to prevent instantiation.</p>
 *
 * @author DevDoctor
 * @version 1.0
 */
public class BookUtils {

    // Private constructor to prevent instantiation
    private BookUtils() {
        throw new UnsupportedOperationException("BookUtils cannot be instantiated");
    }

    /**
     * Loads all books into memory from the file system using the default path.
     */
    public static void loadBooks() {
        LoadedData.books = FileUtils.loadBooksFromFile();
    }

    /**
     * Searches for books by title, author, and year.
     *
     * @param title  the title of the book to search for
     * @param author the author of the book to search for
     * @param year   the publication year of the book to search for
     * @return a collection of books that match the search criteria, or an empty collection if no matches are found
     */
    public static Collection<Book> searchBook(String title, String author, String year) {
        Collection<Book> result = searchBookByTitle(LoadedData.books, title);
        result = searchBookByAuthor(result, author);
        result = searchBookByYear(result, year);

        return result;
    }

    /**
     * Searches the entire book collection by title.
     * Returns a list of {@link Book} objects that match the title,
     * or an empty collection if no matches are found.
     *
     * @param title the title of the book to search for
     * @return a collection of books that match the title, or an empty collection if no matches are found
     * @see Book
     */
    public static Collection<Book> searchBookByTitle(String title) {
        return searchBookByTitle(LoadedData.books, title);
    }

    /**
     * Searches the given book collection by title.
     * Returns a list of {@link Book} objects that match the title,
     * or an empty collection if no matches are found.
     *
     * @param books the collection to search within
     * @param title the title of the book to search for
     * @return a collection of books that match the title, or an empty collection if no matches are found
     * @see Book
     */
    public static Collection<Book> searchBookByTitle(Collection<Book> books, String title) {
        if (books.isEmpty() || title.isEmpty()) {
            return books;
        }

        Collection<Book> result = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Searches the entire book collection by author name.
     * Returns a list of {@link Book} objects that match the author,
     * or an empty collection if no matches are found.
     *
     * @param author the author of the book to search for
     * @return a collection of books that match the author, or an empty collection if no matches are found
     * @see Book
     */
    public static Collection<Book> searchBookByAuthor(String author) {
        return searchBookByAuthor(LoadedData.books, author);
    }

    /**
     * Searches the given book collection by author name.
     * Returns a list of {@link Book} objects that match the author,
     * or an empty collection if no matches are found.
     *
     * @param books  the collection to search within
     * @param author the author of the book to search for
     * @return a collection of books that match the author, or an empty collection if no matches are found
     * @see Book
     */
    public static Collection<Book> searchBookByAuthor(Collection<Book> books, String author) {
        if (books.isEmpty() || author.isEmpty()) {
            return books;
        }

        Collection<Book> result = new ArrayList<Book>();
        for (Book book : books) {
            List<String> mapped = book.getAuthors().stream().map(String::toLowerCase).toList();
            for (String current : mapped) {
                if (current.contains(author.toLowerCase())) {
                    result.add(book);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Searches the entire book collection by publication year.
     * Returns a list of {@link Book} objects that match the year,
     * or an empty collection if no matches are found.
     *
     * @param year the publication year of the book to search for
     * @return a collection of books that match the year, or an empty collection if no matches are found
     * @see Book
     */
    public static Collection<Book> searchBookByYear(String year) {
        return searchBookByAuthor(LoadedData.books, year);
    }

    /**
     * Searches the given book collection by publication year.
     * Returns a list of {@link Book} objects that match the year,
     * or an empty collection if no matches are found.
     *
     * @param books the collection to search within
     * @param year  the publication year of the book to search for
     * @return a collection of books that match the year, or an empty collection if no matches are found
     * @see Book
     */
    public static Collection<Book> searchBookByYear(Collection<Book> books, String year) {
        if (books.isEmpty() || year.isEmpty()) {
            return books;
        }

        Collection<Book> result = new ArrayList<Book>();
        for (Book book : LoadedData.books) {
            if (book.getPublish_year().equals(year)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Searches for a book by its {@code UUID}.
     * If found, returns the book, otherwise returns {@code null}.
     *
     * @param uuid the UUID of the book to search for
     * @return the book if found, otherwise {@code null}
     * @see UUID
     */
    public static Book searchBookByUUID(UUID uuid) {
        for (Book book : LoadedData.books) {
            if (book.getRealUUID().equals(uuid)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Loads a list of reviews for the specified book.
     *
     * @param book the book to load reviews for
     * @return a collection of reviews for the specified book
     */
    private static Collection<Review> loadReview(Book book) {
        return FileUtils.loadReviewsForBook(book);
    }

    /**
     * Adds a review to a book.
     * If the review already exists, the operation will not be performed.
     *
     * @param book      the book to add the review to
     * @param candidate the review to add
     * @return {@code true} if the review was successfully added, {@code false} otherwise
     */
    public static Boolean addReview(Book book, Review candidate) {
        // load the books reviews
        Collection<Review> reviews = loadReview(book);
        // if the review doesn't exist
        if (!doesReviewExist(book, UUID.fromString(candidate.getCreator_uuid()))) {
            // add the review
            reviews.add(candidate);
            return FileUtils.writeBookReviewsToFile(reviews, book);
        }
        return false;
    }

    /**
     * Checks if a review by a specific user already exists for the given book.
     *
     * @param book    the book to check for existing reviews
     * @param creator the UUID of the user who created the review
     * @return {@code true} if the review exists, {@code false} otherwise
     */
    public static Boolean doesReviewExist(Book book, UUID creator) {
        Collection<Review> reviews = loadReview(book);
        for (Review current : reviews) {
            if (current.getCreator_uuid().equals(creator.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the average review scores for a given collection of reviews.
     *
     * <p><b>Note:</b> This method has performance issues and needs to be rewritten.</p>
     *
     * @param reviews the collection of reviews to calculate averages from
     * @return an array containing the average score for each category (style, content, niceness, originality, edition)
     */
    public static Float[] calculateAverageReviewVotes(Collection<Review> reviews) {
        float style = 0f;
        float content = 0f;
        float niceness = 0f;
        float originality = 0f;
        float edition = 0f;

        for (Review r : reviews) {
            style += r.getStyle().getValue();
            content += r.getContent().getValue();
            niceness += r.getNiceness().getValue();
            originality += r.getOriginality().getValue();
            edition += r.getEdition().getValue();
        }

        if (!reviews.isEmpty()) {
            style /= reviews.size();
            content /= reviews.size();
            niceness /= reviews.size();
            originality /= reviews.size();
            edition /= reviews.size();
        }

        return new Float[]{style, content, niceness, originality, edition};
    }

    /**
     * Generates a {@link HashMap} where each key is a {@link Book} and the value is
     * the number of times that book has been recommended in the provided list of reviews.
     *
     * <p>This method counts how many users have recommended each book by examining the
     * UUIDs of books mentioned in the {@code books_uuid} list within each {@link Review}.</p>
     *
     * <p><b>Note:</b> This method has performance issues and needs to be rewritten.</p>
     *
     * @param reviews the list of reviews to analyze for book recommendations
     * @return a {@link HashMap} where the key is a {@link Book} and the value is the number of recommendations it received
     */
    public static HashMap<Book, Integer> getAverageRecommendedBooks(ArrayList<Review> reviews) {
        HashMap<Book, Integer> result = new HashMap<>();
        for (Review review : reviews) {
            if (review.getBooks_uuid() == null) {
                continue;
            }
            for (String uuid : review.getBooks_uuid()) {
                Book book = searchBookByUUID(UUID.fromString(uuid));
                if (book != null) {
                    if (!result.containsKey(book)) {
                        result.put(book, 1);
                    } else {
                        result.put(book, result.get(book) + 1);
                    }
                }
            }
        }
        return result;
    }
}
