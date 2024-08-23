package io.github.devdoctor.deltabooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

public class FileUtils {
    final static TypeToken<Collection<Book>> BOOK_COLLECTION_TYPE = new TypeToken<Collection<Book>>(){};
    final static TypeToken<Collection<User>> USER_COLLECTION_TYPE = new TypeToken<Collection<User>>(){};

    final static String CURRENT_DIR = System.getProperty("user.dir");
    final static String CONF_FOLDER = CURRENT_DIR + "/.config";
    final static String CONF_FILE = "/app-configs.json";
    final static String CATALOG_FILE = "/book-catalog.json";
    final static String USER_FILE = "/users.json";

    /**
     * Loads the {@code Users} dataset from the passed file path.
     * @param path The path to the file
     * @throws FileNotFoundException if the file is not found
     * @return The {@code Collection} of the Users.
     * @see io.github.devdoctor.deltabooks.User
     */
    public static Collection<Book> loadBooks(String path) throws FileNotFoundException {
        File f = new File(path + CATALOG_FILE);

        Gson gson = new Gson();
        return gson.fromJson(new FileReader(f), BOOK_COLLECTION_TYPE);
    }

    /**
     * Loads the {@code Users} dataset from the passed file path.
     * @param path The path to the file
     * @throws FileNotFoundException if the file is not found
     * @return The {@code Collection} of the Users.
     * @see io.github.devdoctor.deltabooks.User
     */
    public static Collection<User> loadUsers(String path) throws FileNotFoundException {
        File f = new File(path + USER_FILE);

        Gson gson = new Gson();
        return gson.fromJson(new FileReader(f), USER_COLLECTION_TYPE);
    }

    /**
     * Tries to load the {@code Config} file.
     * If the path to the file is not found, it makes it.
     * If the file is not found, it makes an empty {@code Config} file.
     * An {@code IOException} is thrown if the file cannot be open/edited.
     * @return Loads the {@code Config} file
     * @see #createEmptyConfigs(File)
     * @see io.github.devdoctor.deltabooks.Config
     */
    public static Config loadConfig() {
        File f = new File(CONF_FOLDER + CONF_FILE);
        if(!f.exists()) {
            try {
                createEmptyConfigs(f);
            } catch (IOException ioe) {
                System.err.print("The config file cannot be open or modified in any way.");
            }
        }
        Config result = new Config();
        try {
            result = new Gson().fromJson(new FileReader(f), Config.class);
        } catch (Exception e) {}

        System.out.println(result.getUsers_dataset_location());
        return result;
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
}
