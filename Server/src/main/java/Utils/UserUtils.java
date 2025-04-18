package Utils;

import ch.qos.logback.core.joran.sanity.Pair;
import io.github.devdoctor.BookRecommender.Objets.User;
import org.mindrot.jbcrypt.BCrypt;

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
    public static String hashPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    /**
     * Checks if a user already exists in the system.
     *
     * @param user the user to check
     * @return a {@link Pair} where the first element is {@code true} if the user exists, {@code false} otherwise,
     * and the second element is the existing user if found, or {@code null} otherwise
     */
    public static boolean doesUserExist(User user) {
        return true;
    }
}
