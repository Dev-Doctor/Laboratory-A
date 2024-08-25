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
    DEBUG("test", "debug", 500, 1000);

    final String title;
    final String resource;
    final Integer height;
    final Integer width;

    Windows(String title, String resource, Integer height, Integer width) {
        this.title = title;
        this.resource = resource;
        this.height = height;
        this.width = width;
    }
}
