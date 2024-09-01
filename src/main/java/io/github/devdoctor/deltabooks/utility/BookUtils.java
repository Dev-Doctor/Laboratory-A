package io.github.devdoctor.deltabooks.utility;

import io.github.devdoctor.deltabooks.Book;
import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.Review;

import java.util.*;

/**
 * This class contains Utility code for Book stuff.
 *
 * @author Davide Restelli
 */
public class BookUtils {


    public static Collection<Book> searchBook(String title, String author, String year) {
        Collection<Book> result = searchBookByTitle(title);
        result = searchBookByAuthor(result, author);
        result = searchBookByYear(result, year);

        return result;
    }

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
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public static Collection<Book> searchBookByTitle(Collection<Book> books, String title) {
        if(books.isEmpty() || title.isEmpty()) {
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
     * Search book collection by the author name. Returns a list of {@code Books} that match,
     * otherwise it returns an empty {@code Collection}
     *
     * @param author the author
     * @return the found books as a collection or an empty one
     * @see Book
     */
    public static Collection<Book> searchBookByAuthor(String author) {
        return searchBookByAuthor(LoadedData.books, author);
    }

    /**
     * Search book collection by the author name. Returns a list of {@code Books} that match,
     * otherwise it returns an empty {@code Collection}
     *
     * @param author the author
     * @param books the collection to search in
     * @return the found books as a collection or an empty one
     * @see Book
     */
    public static Collection<Book> searchBookByAuthor(Collection<Book> books, String author){
        if(books.isEmpty() || author.isEmpty()) {
            return books;
        }

        Collection<Book> result = new ArrayList<Book>();
        for (Book book : books) {
            List<String> mapped = book.getAuthors().stream().map(String::toLowerCase).toList();
            for (String current : mapped) {
                if(current.contains(author.toLowerCase())) {
                    result.add(book);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Search the book collection by the year. Returns a list of {@code Books} that match,
     * otherwise it returns an empty {@code Collection}
     *
     * @param year   the year
     * @return the found books as a collection or an empty one
     * @see Book
     */
    public static Collection<Book> searchBookByYear(String year) {
        return searchBookByAuthor(LoadedData.books, year);
    }

    /**
     * Search the book collection by the year. Returns a list of {@code Books} that match,
     * otherwise it returns an empty {@code Collection}
     *
     * @param year   the year
     * @param books the collection to search in
     * @return the found books as a collection or an empty one
     * @see Book
     */
    public static Collection<Book> searchBookByYear(Collection<Book> books, String year) {
        if(books.isEmpty() || year.isEmpty()) {
            return books;
        }

        Collection<Book> result = new ArrayList<Book>();
        for (Book book : LoadedData.books) {
            if(book.getPublish_year().equals(year)) {
                result.add(book);
            }
        }
        return result;
    }

    public static Book searchBookByUUID(UUID uuid) {
        for (Book book : LoadedData.books) {
            if(book.getRealUUID().equals(uuid)) {
                return book;
            }
        }
        return null;
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

    public static Float[] calculateAverageReviewVotes(Collection<Review> reviews) {
        float style = 0f;
        float content = 0f;
        float niceness = 0f;
        float originality = 0f;
        float edition = 0f;

        for(Review r : reviews) {
            style += r.getStyle().getValue();
            content += r.getContent().getValue();
            niceness += r.getNiceness().getValue();
            originality += r.getOriginality().getValue();
            edition += r.getEdition().getValue();
        }

        if(!reviews.isEmpty()) {
            style /= reviews.size();
            content /= reviews.size();
            niceness /= reviews.size();
            originality /= reviews.size();
            edition /= reviews.size();
        }

        return new Float[]{style, content, niceness, originality, edition};
    }

    public static HashMap<Book, Integer> getAverageRecommmendedBooks(ArrayList<Review> reviews) {
        HashMap<Book, Integer> result = new HashMap<>();
        for (Review review : reviews) {
            if(review.getBooks_uuid() == null) {
                continue;
            }
            for (String uuid : review.getBooks_uuid()) {
                Book book = searchBookByUUID(UUID.fromString(uuid));
                if(book != null) {
                    if(!result.containsKey(book)) {
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
