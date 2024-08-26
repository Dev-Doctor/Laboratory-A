package io.github.devdoctor.deltabooks;

import java.util.Collection;


/**
 * This is the data saved in runtime.
 * Everything here is lost after turning off the program.
 *
 * @author Davide Restelli
 */
public class LoadedData {
    public static Collection<User> users;
    public static Collection<Book> books;
    public static Config config;
    public static User logged_user = null;
    public static Book current_looked_book = null;
    public static Review last_review = null;
    public static LoginEvent loginEvent;
}
