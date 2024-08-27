package io.github.devdoctor.deltabooks;

import io.github.devdoctor.deltabooks.events.LoginEvent;
import io.github.devdoctor.deltabooks.events.UpdateUserEvent;

import java.util.Collection;
import java.util.List;


/**
 * This is the data saved in runtime.
 * Everything here is lost after turning off the program.
 *
 * @author Davide Restelli
 */
public class LoadedData {
    // Initialization Variables
    public static Collection<User> users;
    public static Collection<Book> books;
    public static Config config;

    // Books Variables
    public static Book current_looked_book = null;
    public static Review last_review = null;

    // Events Variables
    public static LoginEvent loginEvent;
    public static UpdateUserEvent userEvent;

    // Logged Variables
    public static List<Library> logged_user_libraries = null;
    public static User logged_user = null;
}
