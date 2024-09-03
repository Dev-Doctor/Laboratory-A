/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks;

/**
 * Enumeration representing different windows or scenes in the application.
 * <p>
 * This enum defines various windows with their associated properties such as title,
 * FXML resource file, height, and width. It provides a convenient way to reference
 * and manage different views within the application.
 * </p>
 *
 * @author DevDoctor
 * @since 1.0
 */
public enum Windows {
    /**
     * Represents the login window.
     */
    LOGIN(DeltaBooks.PROGRAM_NAME + " - Login", "login", 250, 500),

    /**
     * Represents the registration window.
     */
    REGISTER(DeltaBooks.PROGRAM_NAME + " - Register", "register", 500, 500),

    /**
     * Represents the home window.
     */
    HOME(DeltaBooks.PROGRAM_NAME + " - Home", "master", 1000, 1000),

    /**
     * Represents the book overview window.
     */
    BOOK_OVERVIEW(DeltaBooks.PROGRAM_NAME, "bookpage", 750, 750),

    /**
     * Represents the book review window.
     */
    BOOK_REVIEW(DeltaBooks.PROGRAM_NAME + " - Write Review", "bookReview", 500, 750),

    /**
     * Represents a modal dialog for selecting a library.
     */
    LIBRARY_MODAL(DeltaBooks.PROGRAM_NAME + " - Scegli una libreria", "library", 600, 200),

    /**
     * Represents the add library window.
     */
    ADD_LIBRARY(DeltaBooks.PROGRAM_NAME + " Aggiungi Libreria", "addLibrary", 150, 250),

    /**
     * Represents the window for inspecting a review.
     */
    INSPECT_REVIEW(DeltaBooks.PROGRAM_NAME + " - Inspect Review", "inspectReview", 600, 550),

    /**
     * Represents a dialog for selecting a book.
     */
    BOOK_DIALOG("Segli un libro", "bookDialog", 600, 300);

    /**
     * The title of the window.
     */
    final public String title;

    /**
     * The FXML resource file associated with the window.
     */
    final public String resource;

    /**
     * The height of the window in pixels.
     */
    final public Integer height;

    /**
     * The width of the window in pixels.
     */
    final public Integer width;

    /**
     * Constructs a new {@code Windows} enum constant with the specified properties.
     *
     * @param title    the title of the window
     * @param resource the FXML resource file for the window
     * @param height   the height of the window in pixels
     * @param width    the width of the window in pixels
     */
    Windows(String title, String resource, Integer height, Integer width) {
        this.title = title;
        this.resource = resource;
        this.height = height;
        this.width = width;
    }
}
