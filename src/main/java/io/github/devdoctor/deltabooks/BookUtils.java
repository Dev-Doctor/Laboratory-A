package io.github.devdoctor.deltabooks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * This class contains Utility code for Book stuff.
 *
 * @author Davide Restelli
 */
public class BookUtils {

    /**
     * Search book by title book. Returns the {@code Book} if found,
     * otherwise it returns {@code Null}
     *
     * @param title the title
     * @return the book if found or {@code Null}
     * @see Book
     */
    public static Collection<Book> searchBookByTitle(String title) {
        Collection<Book> result = new ArrayList<Book>();
        for (Book book : LoadedData.books) {
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Search book collection by the author name. Returns a list of {@code Books} that match,
     * otherwise it returns an empty {@code Collection}
     *
     * @param author the author
     * @return the found books as a collection or an empty one
     * @see Book
     */
    public static Collection<Book> searchBook(String author) {
        Collection<Book> result = new ArrayList<Book>();
        for (Book book : LoadedData.books) {
            if(book.getAuthors().contains(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Search book collection.by the author name and year. Returns a list of {@code Books} that match,
     * otherwise it returns an empty {@code Collection}
     *
     * @param author the author
     * @param year   the year
     * @return the found books as a collection or an empty one
     * @see Book
     */
    public static Collection<Book> searchBook(String author, String year) {
        Collection<Book> result = new ArrayList<Book>();
        for (Book book : LoadedData.books) {
            if(book.getAuthors().contains(author) && book.getPublish_year().equals(year)) {
                result.add(book);
            }
        }
        return result;
    }

    private static Collection<Review> loadReview(Book book) {
        return FileUtils.loadReviewsForBook(book);
    }

    public static Boolean addReview(Book book, Review candidate) {
        Collection<Review> reviews = loadReview(book);
        if(!doesReviewExist(book, UUID.fromString(candidate.getCreator_uuid()))) {
            reviews.add(candidate);
            return FileUtils.writeBookReviewsToFile(reviews, book);
        }
        return false;
    }

    public static Boolean doesReviewExist(Book book, UUID creator) {
        Collection<Review> reviews = loadReview(book);
        for(Review current : reviews) {
            if(current.getCreator_uuid().equals(creator.toString())) {
                return true;
            }
        }
        return false;
    }
}
