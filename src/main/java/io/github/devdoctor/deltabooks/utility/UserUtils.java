/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks.utility;

import io.github.devdoctor.deltabooks.Book;
import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.User;
import javafx.util.Pair;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for operations related to {@code User} objects.
 * This class provides static methods for user management, including user creation,
 * password checking, email validation, and more.
 * <p>
 * This class cannot be instantiated and is intended to be used in a static context.
 * </p>
 *
 * <p><b>Important:</b> The constructor is private to prevent instantiation.</p>
 *
 * @author DevDoctor
 * @since 1.0
 */
public class UserUtils {

    // Private constructor to prevent instantiation
    private UserUtils() {
        throw new UnsupportedOperationException("UserUtils cannot be instantiated");
    }

    /**
     * Loads all users into memory from the file system using the default path.
     * The loaded users are stored in {@link LoadedData#users}.
     */
    public static void loadUsers() {
        LoadedData.users = FileUtils.loadUsersFromFile();
    }

    /**
     * Checks if the provided plaintext password matches the hashed password.
     *
     * @param candidate       the plaintext password to check
     * @param hashed_password the hashed password to compare against
     * @return {@code true} if the passwords match, {@code false} otherwise
     * @see BCrypt#checkpw(String, String)
     */
    public static boolean checkPassword(String candidate, String hashed_password) {
        return BCrypt.checkpw(candidate, hashed_password);
    }

    /**
     * Checks if the given email address is valid using a simplified regex pattern.
     * <p>
     * This method currently uses a regex pattern to validate the email address format:
     * <pre>
     *     ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$
     * </pre>
     * which covers most common cases of valid email addresses. It ensures that the email has the
     * following structure: local part, `@` symbol, domain part, and a top-level domain.
     * </p>
     * <p>
     * <b>Important:</b><br>
     * The <a href="https://www.rfc-editor.org/info/rfc5322">RFC 5322</a> simplified regex was meant to be used as the email verification.
     * It was changed due to problems in the code. It will be reintroduced in a future version.
     * </p>
     *
     * @param email the email address to validate
     * @return {@code true} if the email address is valid according to the current pattern;
     * {@code false} otherwise
     */
    public static boolean checkEmail(String email) {
//        Pattern pattern = Pattern.compile("([!#-'*+/-9=?A-Z^-~-]+(\\.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \\t]|(\\\\[\\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\\.[!#-'*+/-9=?A-Z^-~-]+)*|\\[[\t -Z^-~]*)");
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Checks if the provided fiscal code is valid according to a specific regex pattern.
     *
     * @param fc the fiscal code to validate
     * @return {@code true} if the fiscal code is valid, {@code false} otherwise
     * @see <a href="http://blog.marketto.it/2016/01/regex-validazione-codice-fiscale-con-omocodia/">Regexp di validazione suprema</a>
     */
    public static boolean checkFiscalCode(String fc) {
        return fc.matches("^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$");
    }

    /**
     * Generates a hashed password using {@link BCrypt} hashing method.
     *
     * @param plaintext the plaintext password to hash
     * @return the hashed password
     * @see BCrypt#hashpw(String, String)
     */
    public static String generatePassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    /**
     * Changes the password of the specified user if the provided old password is correct.
     * <p>
     * If the old password is valid, the new password is hashed and set for the user.
     * </p>
     *
     * @param new_password the new password to set
     * @param user         the user whose password is to be changed
     * @return {@code true} if the password change was successful, {@code false} otherwise
     */
    public static Boolean changePassword(String new_password, User user) {
        if (checkPassword(new_password, user.getPassword())) {
            user.setPassword(generatePassword(new_password));
            return true;
        }
        return false;
    }

    /**
     * Retrieves a {@code User} by their UUID.
     *
     * @param uuid the UUID of the user
     * @return the {@code User} with the specified UUID, or {@code null} if no such user exists
     */
    public static User getUserFromUUID(UUID uuid) {
        for (User current : LoadedData.users) {
            if (Objects.equals(current.getUUID(), uuid.toString())) {
                return current;
            }
        }
        return null;
    }

    /**
     * Checks if a user already exists in the system.
     *
     * @param user the user to check
     * @return a {@link Pair} where the first element is {@code true} if the user exists, {@code false} otherwise,
     * and the second element is the existing user if found, or {@code null} otherwise
     */
    public static Pair<Boolean, User> doesUserExist(User user) {
        for (User current : LoadedData.users) {
            if (user.equals(current)) {
                return new Pair<Boolean, User>(true, current);
            }
        }
        return new Pair<Boolean, User>(false, null);
    }

    /**
     * Creates a new user and adds it to the system if no existing user with the same details is found.
     * The new user is saved to the file system.
     *
     * @param name        the first name of the new user
     * @param lastname    the last name of the new user
     * @param fiscal_code the fiscal code of the new user
     * @param email       the email address of the new user
     * @param password    the plaintext password for the new user
     * @return a {@link Pair} where the first element is {@code true} if the user was created successfully,
     * {@code false} otherwise, and the second element is the created user if successful, or {@code null} otherwise
     */
    public static Pair<Boolean, User> createUser(String name, String lastname, String fiscal_code,
                                                 String email, String password) {
        UUID uuid = UUID.randomUUID();
        User new_user = new User(name, lastname, fiscal_code, email, "", uuid.toString());
        if (!doesUserExist(new_user).getKey()) {
            String hashed_password = generatePassword(password);
            new_user.setPassword(hashed_password);
            ArrayList<User> users_temp = new ArrayList<>(LoadedData.users);
            users_temp.add(new_user);
            LoadedData.users = users_temp;
            FileUtils.writeUserListToFile(LoadedData.users);
            return new Pair<Boolean, User>(true, new_user);
        }
        return new Pair<Boolean, User>(false, null);
    }
}
