/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.devdoctor.bookrecommender.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Utility class providing various file operations such as loading and saving
 * books, users, libraries, and reviews to and from JSON files. This class is
 * designed to handle file operations related to the application's data storage.
 * <p>
 * This class cannot be instantiated and is intended to be used in a static context.
 * </p>
 *
 * <p><b>Important:</b> The constructor is private to prevent instantiation.</p>
 *
 * @author DevDoctor
 * @since 1.0
 */
public class FileUtils {
    // Type tokens for deserializing collections from JSON
    final static TypeToken<Collection<Book>> BOOK_COLLECTION_TYPE = new TypeToken<Collection<Book>>() {
    };
    final static TypeToken<Collection<User>> USER_COLLECTION_TYPE = new TypeToken<Collection<User>>() {
    };
    final static TypeToken<Collection<Review>> REVIEW_COLLECTION_TYPE = new TypeToken<Collection<Review>>() {
    };
    final static TypeToken<List<Library>> LIBRARY_COLLECTION_TYPE = new TypeToken<List<Library>>() {
    };

    // Directory and file paths
    final static String CURRENT_DIR = System.getProperty("user.dir");
    final static String CONF_FOLDER = CURRENT_DIR + "/.config";
    final static String DATA_FOLDER = CURRENT_DIR + "/data";
    final static String BOOK_REVIEW_FOLDER = DATA_FOLDER + "/book_reviews";
    final static String LIBRARY_FOLDER = DATA_FOLDER + "/user_libraries";

    final static String CONF_FILE = "app-configs";
    final static String CATALOG_FILE = "book-catalog";
    final static String USER_FILE = "registered-users";

    // Private constructor to prevent instantiation
    private FileUtils() {
        throw new UnsupportedOperationException("FileUtils cannot be instantiated");
    }

    /**
     * Loads the {@code User} collection from the file specified in the application's configuration.
     *
     * @return the collection of users loaded from the file, or an empty collection if no users are found.
     * @see User
     * @see Config
     */
    public static Collection<Book> loadBooksFromFile() {
//        String PATH = LoadedData.config.getBooks_dataset_location();
        Collection<Book> empty_coll = new ArrayList<Book>();

        return safeLoadJsonFile(DATA_FOLDER, CATALOG_FILE, BOOK_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Loads the {@code Library} collection for the currently logged-in user from the file system.
     *
     * @return the collection of libraries, or an empty list if no libraries are found.
     * @see Library
     * @see Config
     */
    public static List<Library> loadLibrariesFromFile() {
        return loadLibrariesFromFile(LoadedData.logged_user);
    }

    /**
     * Loads the {@code Library} collection for a specified user from the file system.
     *
     * @param user the user whose libraries are to be loaded.
     * @return the collection of libraries, or an empty list if no libraries are found.
     * @see Library
     * @see Config
     */
    public static List<Library> loadLibrariesFromFile(User user) {
        List<Library> empty_coll = new ArrayList<Library>();
        return safeLoadJsonFile(LIBRARY_FOLDER, user.getUUID(), LIBRARY_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Loads the {@code Users} dataset
     *
     * @return The {@code Collection} of the Users.
     * @see User
     * @see Config
     */
    public static Collection<User> loadUsersFromFile() {
//        String path = LoadedData.config.getUsers_dataset_location();
        Collection<User> empty_coll = new ArrayList<User>();

        return safeLoadJsonFile(DATA_FOLDER, USER_FILE, USER_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Loads the {@code Review} collection for a specific book from the file system.
     *
     * @param book the book whose reviews are to be loaded.
     * @return the collection of reviews, or an empty collection if no reviews are found.
     * @see Review
     */
    public static Collection<Review> loadReviewsForBook(Book book) {
        Collection<Review> empty_coll = new ArrayList<Review>();

        return safeLoadJsonFile(BOOK_REVIEW_FOLDER, book.getUuid(), REVIEW_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Loads the application's configuration from a file. If the file or directory
     * does not exist, they are created, and a default configuration is saved.
     *
     * @see Config
     * @see LoadedData
     */
    public static void loadConfig() {
        File f = new File(DATA_FOLDER + "/" + CONF_FILE + ".json");
        if (!f.exists()) {
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
     * Creates an empty configuration file and its directory if they do not exist.
     *
     * @param f the file to create.
     * @throws IOException if the file cannot be created or written to.
     * @see Config
     */
    private static void createEmptyConfigs(File f) throws IOException {
        Files.createDirectories(Paths.get(DATA_FOLDER));

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
     * Writes the {@code User} collection to a file. The file location is specified in the application's configuration.
     *
     * @param users the collection of users to save.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     * @see Config
     */
    public static Boolean writeUserListToFile(Collection<User> users) {
//        String path = LoadedData.config.getUsers_dataset_location();

        String json = new Gson().toJson(users, USER_COLLECTION_TYPE.getType());
        return writeJsonFile(DATA_FOLDER, USER_FILE, json);
    }

    /**
     * Writes the {@code Library} collection to a file. The file name is the UUID of the user.
     *
     * @param libraries the collection of libraries to save.
     * @param user the user who owns the libraries.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     * @see Config
     * @see UUID
     */
    public static Boolean writeLibraryToFile(List<Library> libraries, User user) {
        String json = new Gson().toJson(libraries, LIBRARY_COLLECTION_TYPE.getType());
        return writeJsonFile(LIBRARY_FOLDER, user.getUUID(), json);
    }

    /**
     * Writes the {@code Review} collection to a file. The file name is the UUID of the book.
     *
     * @param reviews the collection of reviews to save.
     * @param book the book that owns the reviews.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     * @see Config
     * @see UUID
     */
    public static Boolean writeBookReviewsToFile(Collection<Review> reviews, Book book) {
        String json = new Gson().toJson(reviews, REVIEW_COLLECTION_TYPE.getType());
        return writeJsonFile(BOOK_REVIEW_FOLDER, book.getUuid(), json);
    }

    /**
     * Writes the given data to a specified file. If the file or directory does not exist,
     * it creates them. If {@code data} is {@code null}, it creates an empty file.
     *
     * @param data the data to write to the file, or {@code null} to create an empty file.
     * @param path the path of the file to write.
     * @throws IOException if the file cannot be created or written to.
     */
    private static void createFileWithData(String data, String path) throws IOException {
        Writer fileWriter = new FileWriter(path, false);
        if (data != null) {
            fileWriter.write(data);
        }
        fileWriter.close();
    }

    /**
     * Writes JSON data to a specified file. If the file or directory does not exist, it creates them.
     *
     * @param PATH the directory path where the file will be saved.
     * @param filename the name of the file (without extension).
     * @param data the JSON data to write to the file.
     * @return {@code true} if the operation was successful, {@code false} otherwise.
     */
    public static boolean writeJsonFile(String PATH, String filename, String data) {
        String full_path = PATH + "/" + filename + ".json";
        File directory = new File(PATH);
        /// check if directory exist
        if (!directory.exists()) {
            // makes the directory and its parents
            directory.mkdirs();
        }

        System.out.println("Writing File -> " + full_path);

        File file = new File(full_path);
        // tries to write the passed data to file
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println("Smoething went wrong wile trying to create the file: " + filename);
        }
        return false;
    }

    /**
     * Loads a JSON file and deserializes it into a collection of objects of the specified type.
     *
     * @param <T>        the type of the collection to return.
     * @param path       the directory path where the JSON file is located.
     * @param filename   the name of the JSON file (without extension).
     * @param data_token a {@code TypeToken} representing the collection type.
     * @return a collection of objects deserialized from the JSON file, or {@code null} if the file does not exist or cannot be read.
     */
    public static <T extends Collection> T loadJsonFile(String path, String filename, TypeToken data_token) {
        Gson gson = new Gson();
        File f = new File(path + "/" + filename + ".json");
        T result = null;
        if (f.exists()) {
            try {
                result = gson.fromJson(new FileReader(f), data_token.getType());
            } catch (FileNotFoundException e) {
                System.err.println("There was a problem trying to load the JSON file: " + filename);
            }
        }
        return result;
    }

    /**
     * Safely loads a JSON file and deserializes it into a collection of objects of the specified type.
     * If the file does not exist, it creates the file with an empty collection, then loads and returns that empty collection.
     *
     * @param <T>        the type of the collection to return.
     * @param path       the directory path where the JSON file is located.
     * @param filename   the name of the JSON file (without extension).
     * @param data_token a {@code TypeToken} representing the collection type.
     * @param empty_data an empty collection to use as the initial content if the file does not exist.
     * @return a collection of objects deserialized from the JSON file, or the provided empty collection if the file was created.
     */
    public static <T extends Collection> T safeLoadJsonFile(String path, String filename, TypeToken data_token, T empty_data) {
        String FULL_PATH = path + "/" + filename + ".json";
        File file = new File(FULL_PATH);
        System.out.println("Loading File -> " + FULL_PATH);
        if (!file.exists()) {
            System.out.println("file doesn't exist");
            String json = new Gson().toJson(empty_data, data_token.getType());
            writeJsonFile(path, filename, json);
        }
        return loadJsonFile(path, filename, data_token);
    }
}
