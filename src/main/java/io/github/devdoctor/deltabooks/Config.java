/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks;


import io.github.devdoctor.deltabooks.utility.FileUtils;

import java.io.File;

/**
 * The type Config.
 * The application settings.
 *
 * @author DevDoctor
 * @since 1.0
 */
public class Config {
    // The location of the users dataset
//    private String users_dataset_location;
    // The location of the books_uuids dataset
//    private String books_dataset_location;
    // Debug mode
    private Boolean debug_mode;
    /**
     * Instantiates a new Config.
     * This is called only if no config file is found.
     * @see FileUtils#createEmptyConfigs(File)
     */
    public Config() {
//        this.users_dataset_location = "";
//        this.books_dataset_location = "";
        this.debug_mode = false;
    }

//    public String getUsers_dataset_location() {
//        return users_dataset_location;
//    }

//    public void setUsers_dataset_location(String users_dataset_location) {
//        this.users_dataset_location = users_dataset_location;
//    }

//    public String getBooks_dataset_location() {
//        return books_dataset_location;
//    }

//    public void setBooks_dataset_location(String books_dataset_location) {
//        this.books_dataset_location = books_dataset_location;
//    }

    public boolean isDebugOn() {
        return debug_mode;
    }
}