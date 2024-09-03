/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks;

import io.github.devdoctor.deltabooks.events.LoginEvent;
import io.github.devdoctor.deltabooks.events.UpdateUserEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class holds runtime data for the application.
 * <p>
 * All data stored in this class is transient and will be lost when the application is closed. This class
 * is used to store information that needs to be maintained during the runtime of the application, such as
 * user data, book information, configuration settings, and event-related information.
 * </p>
 *
 * @author DevDoctor
 * @since 1.0
 */
public class LoadedData {
    /**
     * The maximum number of book recommendations to display.
     */
    public static final int MAX_BOOK_RECOMMENDATIONS = 3;

    // Initialization Variables

    /**
     * Collection of all users in the system.
     * <p>
     * This collection is populated with user data during the application's runtime.
     * </p>
     */
    public static Collection<User> users;

    /**
     * Collection of all books in the system.
     * <p>
     * This collection is populated with book data during the application's runtime.
     * </p>
     */
    public static Collection<Book> books;

    /**
     * Configuration settings for the application.
     * <p>
     * This object holds various configuration settings that are used throughout the application.
     * </p>
     */
    public static Config config;

    // Books Variables

    /**
     * The currently looked-at book.
     * <p>
     * This variable holds the book that is currently being viewed or interacted with by the user.
     * </p>
     */
    public static Book current_looked_book = null;

    /**
     * The last review submitted by the user.
     * <p>
     * This variable holds the most recent review that has been submitted.
     * </p>
     */
    public static Review last_review = null;

    /**
     * List of IDs or names of book tabs that have been loaded.
     * <p>
     * This list keeps track of the book tabs that have been loaded into the application.
     * </p>
     */
    public static List<String> loaded_book_tabs = new ArrayList<String>();

    // Events Variables

    /**
     * The login event information.
     * <p>
     * This variable holds details related to user login events.
     * </p>
     */
    public static LoginEvent loginEvent;

    /**
     * The user update event information.
     * <p>
     * This variable holds details related to user update events.
     * </p>
     */
    public static UpdateUserEvent userEvent;

    // Logged Variables

    /**
     * List of libraries associated with the logged-in user.
     * <p>
     * This list holds the libraries that are linked to the currently logged-in user.
     * </p>
     */
    public static List<Library> logged_user_libraries = null;

    /**
     * The currently logged-in user.
     * <p>
     * This variable holds the user information for the currently logged-in user.
     * </p>
     */
    public static User logged_user = null;

}
