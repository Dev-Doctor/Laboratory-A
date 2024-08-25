package io.github.devdoctor.deltabooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class FileUtils {
    final static TypeToken<Collection<Book>> BOOK_COLLECTION_TYPE = new TypeToken<Collection<Book>>(){};
    final static TypeToken<Collection<User>> USER_COLLECTION_TYPE = new TypeToken<Collection<User>>(){};
    final static TypeToken<Collection<Review>> REVIEW_COLLECTION_TYPE = new TypeToken<Collection<Review>>(){};

    final static String CURRENT_DIR = System.getProperty("user.dir");
    final static String CONF_FOLDER = CURRENT_DIR + "/.config";
    final static String DATA_FOLDER = CURRENT_DIR + "/data";
    final static String BOOK_REVIEW_FOLDER = CONF_FOLDER + "/book_reviews";
    final static String CONF_FILE = "/app-configs.json";
    final static String CATALOG_FILE = "/book-catalog.json";
    final static String USER_FILE = "/registered-users.json";

    public static void loadBooks() {
        try {
            LoadedData.books = loadBooksFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the {@code Users} dataset from the location saved in {@link Config#users_dataset_location}
     * @throws FileNotFoundException if the file is not found
     * @return The {@code Collection} of the Users.
     * @see io.github.devdoctor.deltabooks.Book
     * @see io.github.devdoctor.deltabooks.Config
     */
    public static Collection<Book> loadBooksFromFile() throws FileNotFoundException {
        String path = LoadedData.config.getBooks_dataset_location() + CATALOG_FILE;
        File f = new File(path);

        if(!f.exists()) {
            try {
                createFileWithData(null, path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Collection<Book> books = new Gson().fromJson(new FileReader(f), BOOK_COLLECTION_TYPE);
        return (books == null) ? Collections.emptyList() : books;
    }

    public static void loadUsers() {
        try {
            LoadedData.users = loadUsersFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the {@code Users} dataset from the location saved in {@link Config#users_dataset_location}
     * @throws FileNotFoundException if the file is not found
     * @return The {@code Collection} of the Users.
     * @see io.github.devdoctor.deltabooks.User
     * @see io.github.devdoctor.deltabooks.Config
     */
    public static Collection<User> loadUsersFromFile() throws FileNotFoundException {
        String path = LoadedData.config.getUsers_dataset_location();
        File f = new File(path + USER_FILE);

        if(!f.exists()) {
            try {
                createFileWithData(null, path + USER_FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Collection<User> users = new Gson().fromJson(new FileReader(f), USER_COLLECTION_TYPE);

        return (users == null) ? Collections.emptyList() : users;
    }

    /**
     * Loads the {@code Reviews} dataset from the passed book UIDD.
     * If the Review file is not yet created, returns an empty collection
     * @return The {@code Collection} of the Reviews.
     * @see io.github.devdoctor.deltabooks.Review
     */
    public static Collection<Review> loadReviewsForBook(Book book) {
        File f = new File(BOOK_REVIEW_FOLDER + "/" +  book.getUUID() + ".json");
        try {
            if(f.exists()) {
                return new Gson().fromJson(new FileReader(f), REVIEW_COLLECTION_TYPE);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

    /**
     * Tries to load the {@code Config} file.
     * If the path to the file is not found, it makes it.
     * If the file is not found, it makes an empty {@code Config} file.
     * An {@code IOException} is thrown if the file cannot be open/edited.
     * @see #createEmptyConfigs(File)
     * @see io.github.devdoctor.deltabooks.Config
     * @see io.github.devdoctor.deltabooks.LoadedData
     */
    public static void loadConfig() {
        File f = new File(CONF_FOLDER + CONF_FILE);
        if(!f.exists()) {
            try {
                createEmptyConfigs(f);
            } catch (IOException ioe) {
                System.err.println("The config file cannot be open or modified in any way.");
            }
        }

        try {
            LoadedData.config = new Gson().fromJson(new FileReader(f), Config.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Generates an empty {@code Config} file and the path to it.
     * @param f the file location
     * @throws IOException if the file cannot be open/edited.
     * @see io.github.devdoctor.deltabooks.Config
     */
    private static void createEmptyConfigs(File f) throws IOException {
            Files.createDirectories(Paths.get(CONF_FOLDER));

            FileWriter fileWriter = new FileWriter(f);

            // generate an empty config object
            Config config = new Config();

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            // write it to file as json
            fileWriter.write(gson.toJson(config));
            fileWriter.close();
    }

    /**
     * Write the {@code User} collection to file.
     * The chosen path from where to load the file is saved in the {@code config} file
     * Gives an error message if it is unable to modify the file.
     *
     * @param users  the users collection
     * @return a boolean if the task was successfully executed or not
     * @see Config
     */
    public static boolean writeUserList(Collection<User> users) {
        String path = LoadedData.config.getUsers_dataset_location() + USER_FILE;
        File f = new File(path);
        if(f.exists()) {
            String json = new Gson().toJson(users, USER_COLLECTION_TYPE.getType());
            try {
                createFileWithData(json, path);
            } catch (IOException e) {
                System.err.println("Unable to write/edit the file. Check permissions!");
            }
            return true;
        }
        return false;
    }

    /**
     * Write the {@code Reviews} collection to a file.
     * The file name used is the {@code UUID} of the book.
     * Gives an error message if it is unable to modify the file.
     *
     * @param reviews  the collection holding the reviews
     * @param book owner of the reviews
     * @see Config
     * @see UUID
     */
    public static Boolean writeBookReviewsToFile(Collection<Review> reviews, Book book) {
        String path = BOOK_REVIEW_FOLDER + "/" + book.getUUID() + ".json";
        File f = new File(path);
        System.out.println(path);

        String json = new Gson().toJson(reviews, REVIEW_COLLECTION_TYPE.getType());
        System.out.println(json);
        try {
            createFileWithData(json, path);
        } catch (IOException e) {
            System.err.println("Unable to write/edit the file. Check permissions!");
            return false;
        }
        return true;
    }

    /**
     * Writes to fle the passed data. Throws an {@code IOException}
     * if it is unable to modify or write the file. If {@code data} is {@code null}
     * create an empty file.
     *
     * @param data the data to write to the file, if it is {@code null} then create an empty file.
     * @param path the path where the file is
     * @throws IOException if file is not editable for missing permissions
     */
    private static void createFileWithData(String data, String path) throws IOException {
        Writer fileWriter = new FileWriter(path, false);
        if(data != null) {
            fileWriter.write(data);
        }
        fileWriter.close();
    }
}
