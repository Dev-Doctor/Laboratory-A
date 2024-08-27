package io.github.devdoctor.deltabooks.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.devdoctor.deltabooks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileUtils {
    final static TypeToken<Collection<Book>> BOOK_COLLECTION_TYPE = new TypeToken<Collection<Book>>(){};
    final static TypeToken<Collection<User>> USER_COLLECTION_TYPE = new TypeToken<Collection<User>>(){};
    final static TypeToken<Collection<Review>> REVIEW_COLLECTION_TYPE = new TypeToken<Collection<Review>>(){};
    final static TypeToken<List<Library>> LIBRARY_COLLECTION_TYPE = new TypeToken<List<Library>>(){};

    final static String CURRENT_DIR = System.getProperty("user.dir");
    final static String CONF_FOLDER = CURRENT_DIR + "/.config";
    final static String DATA_FOLDER = CURRENT_DIR + "/data";
    final static String BOOK_REVIEW_FOLDER = CONF_FOLDER + "/book_reviews";
    final static String LIBRARY_FOLDER = CONF_FOLDER + "/user_libraries";

    final static String CONF_FILE = "app-configs";
    final static String CATALOG_FILE = "book-catalog";
    final static String USER_FILE = "registered-users";

    // need to move to its utility file
    public static void loadBooks() {
        LoadedData.books = loadBooksFromFile();
    }

    /**
     * Loads the {@code Users} dataset from the location saved in {@link Config#users_dataset_location}
     * @return The {@code Collection} of the Users.
     * @see Book
     * @see Config
     */
    public static Collection<Book> loadBooksFromFile() {
        String PATH = LoadedData.config.getBooks_dataset_location();
        Collection<Book> empty_coll = new ArrayList<Book>();

        return safeLoadJsonFile(PATH, CATALOG_FILE, BOOK_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Need moving to its own Utility File
     */
    public static void loadUsers() {
        LoadedData.users = loadUsersFromFile();
    }

    /**
     * Loads the {@code Libraries} dataset of the logged user
     * @return The {@code Collection} of the Libraries or an empty collection.
     * @see Library
     * @see Config
     */
    public static List<Library> loadLibrariesFromFile()  {
        return loadLibrariesFromFile(LoadedData.logged_user);
    }

    /**
     * Loads the {@code Libraries} of a specified User
     * @return The {@code Collection} of the Libraries or an empty collection.
     * @see Library
     * @see Config
     */
    public static List<Library> loadLibrariesFromFile(User user) {
        List<Library> empty_coll = new ArrayList<Library>();
        return safeLoadJsonFile(LIBRARY_FOLDER, user.getUUID(), LIBRARY_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Loads the {@code Users} dataset from the location saved in {@link Config#users_dataset_location}
     * @return The {@code Collection} of the Users.
     * @see User
     * @see Config
     */
    public static Collection<User> loadUsersFromFile() {
        String path = LoadedData.config.getUsers_dataset_location();
        Collection<User> empty_coll = new ArrayList<User>();

        return safeLoadJsonFile(path, USER_FILE, USER_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Loads the {@code Reviews} dataset from the passed book UIDD.
     * If the Review file is not yet created, returns an empty collection
     * @return The {@code Collection} of the Reviews.
     * @see Review
     */
    public static Collection<Review> loadReviewsForBook(Book book) {
        Collection<Review> empty_coll = new ArrayList<Review>();

        return safeLoadJsonFile(BOOK_REVIEW_FOLDER, book.getUuid(), REVIEW_COLLECTION_TYPE, empty_coll);
    }

    /**
     * Tries to load the {@code Config} file.
     * If the path to the file is not found, it makes it.
     * If the file is not found, it makes an empty {@code Config} file.
     * An {@code IOException} is thrown if the file cannot be open/edited.
     * @see #createEmptyConfigs(File)
     * @see Config
     * @see LoadedData
     */
    public static void loadConfig() {
        File f = new File(CONF_FOLDER + "/" + CONF_FILE + ".json");
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
     * @see Config
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
    public static Boolean writeUserListToFile(Collection<User> users) {
        String path = LoadedData.config.getUsers_dataset_location();

        String json = new Gson().toJson(users, USER_COLLECTION_TYPE.getType());
        return writeJsonFile(path, USER_FILE, json);
    }

    /**
     * Write the {@code Libraries} collection to a file.
     * The file name used is the {@code UUID} of the book.
     * Gives an error message if it is unable to modify the file.
     *
     * @param libraries  the collection holding the reviews
     * @param user owner of the reviews
     * @see Config
     * @see UUID
     */
    public static Boolean writeLibraryToFile(List<Library> libraries, User user) {
        String json = new Gson().toJson(libraries,  LIBRARY_COLLECTION_TYPE.getType());
        return writeJsonFile(LIBRARY_FOLDER, user.getUUID(), json);
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
        String json = new Gson().toJson(reviews, REVIEW_COLLECTION_TYPE.getType());
        return writeJsonFile(BOOK_REVIEW_FOLDER, book.getUuid(), json);
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

    public static boolean writeJsonFile(String PATH, String filename, String data){
        String full_path = PATH + "/" + filename + ".json";
        File directory = new File(PATH);
        /// check if directory exist
        if (! directory.exists()){
            // makes the directory and its parents
            directory.mkdirs();
        }

        System.out.println("Writing File -> " + full_path);

        File file = new File(full_path);
        // tries to write the passed data to file
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            return true;
        }
        catch (IOException e){
            System.err.println("Smoething went wrong wile trying to create the file: " + filename);
        }
        return false;
    }

    public static <T extends Collection> T loadJsonFile(String path, String filename, TypeToken data_token) {
        Gson gson = new Gson();
        File f = new File(path + "/" + filename + ".json");
        T result = null;
        if(f.exists()) {
            try {
                result = gson.fromJson(new FileReader(f), data_token.getType());
            } catch (FileNotFoundException e) {
                System.err.println("There was a problem trying to load the JSON file: " + filename);
            }
        }
        return result;
    }

    public static <T extends Collection> T safeLoadJsonFile(String path, String filename, TypeToken data_token, T empty_data) {
        String FULL_PATH = path + "/" + filename + ".json";
        File file = new File(FULL_PATH);
        System.out.println("Loading File -> " + FULL_PATH);
        if(!file.exists()) {
            System.out.println("file doesn't exist");
            String json = new Gson().toJson(empty_data, data_token.getType());
            writeJsonFile(path, filename, json);
        }
        return loadJsonFile(path, filename, data_token);
    }
}
