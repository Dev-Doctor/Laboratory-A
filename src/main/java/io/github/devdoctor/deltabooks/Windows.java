package io.github.devdoctor.deltabooks;

/**
 * The enum of Windows.
 * It holds the usable windows and their data.
 *
 * @author Davide Restelli
 */
public enum Windows {
    LOGIN(DeltaBooks.PROGRAM_NAME + " - Login", "login", 250, 500),
    REGISTER(DeltaBooks.PROGRAM_NAME + " - Register", "register", 500, 500),
    HOME(DeltaBooks.PROGRAM_NAME + " - Home", "master", 1000, 1000),
    BOOK_OVERVIEW(DeltaBooks.PROGRAM_NAME, "bookpage", 750, 750),
    BOOK_REVIEW(DeltaBooks.PROGRAM_NAME + " - Write Review", "bookReview", 500, 750),
    LIBRARY_MODAL(DeltaBooks.PROGRAM_NAME + " - Scegli una libreria", "library", 600, 200),
    ADD_LIBRARY(DeltaBooks.PROGRAM_NAME + " Aggiungi Libreria", "addLibrary", 150, 250),
    DEBUG("test", "debug", 500, 1000);

    final public String title;
    final public String resource;
    final public Integer height;
    final public Integer width;

    Windows(String title, String resource, Integer height, Integer width) {
        this.title = title;
        this.resource = resource;
        this.height = height;
        this.width = width;
    }
}
