package io.github.devdoctor.deltabooks;


import java.io.File;

/**
 * The type Config.
 * The application settings.
 * @author Davide Restelli
 */
public class Config {
    // The location of the users dataset
    private String users_dataset_location;
    // The location of the books dataset
    private String books_dataset_location;
    // Debug mode
    private Boolean debug_mode;
    /**
     * Instantiates a new Config.
     * This is called only if no config file is found.
     * @see io.github.devdoctor.deltabooks.FileUtils#createEmptyConfigs(File)
     */
    public Config() {
        this.users_dataset_location = "";
        this.books_dataset_location = "";
        this.debug_mode = false;
    }

    public String getUsers_dataset_location() {
        return users_dataset_location;
    }

    public void setUsers_dataset_location(String users_dataset_location) {
        this.users_dataset_location = users_dataset_location;
    }

    public String getBooks_dataset_location() {
        return books_dataset_location;
    }

    public void setBooks_dataset_location(String books_dataset_location) {
        this.books_dataset_location = books_dataset_location;
    }
}