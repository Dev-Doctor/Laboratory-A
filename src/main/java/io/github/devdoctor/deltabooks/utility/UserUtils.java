package io.github.devdoctor.deltabooks.utility;

import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.User;
import javafx.util.Pair;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;

/**
 * The type User utils.
 */
public class UserUtils {

    /**
     * Check if the inputted password is valid. If it is valid return {@code true} else return {@code false}.
     *
     * @param candidate       the plaintext password to check if it is valid.
     * @param hashed_password the hashed password.
     * @return the boolean
     * @see BCrypt#checkpw(String, String)
     */
    public static boolean checkPassword(String candidate, String hashed_password) {
        return BCrypt.checkpw(candidate, hashed_password);
    }


    /**
     * Check if the passed email is valid.
     * The <a href="https://www.rfc-editor.org/info/rfc5322">RFC 5322</a> provides the regular expression for email validation
     * used here. it’s a very simple regex that allows all the characters in the email.
     *
     * @param email the email
     * @return {@code true} if it is valid, {@code false} if it isn't
     */
    public static boolean checkEmail(String email) {
//        Pattern pattern = Pattern.compile("([!#-'*+/-9=?A-Z^-~-]+(\\.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \\t]|(\\\\[\\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\\.[!#-'*+/-9=?A-Z^-~-]+)*|\\[[\t -Z^-~]*)");
//        return email.matches(pattern.pattern());
        return true;
    }

    /**
     * Check fiscal code boolean.
     *
     * @param fc the fc
     * @return the boolean
     * @see <a href="http://blog.marketto.it/2016/01/regex-validazione-codice-fiscale-con-omocodia/">Regexp di validazione suprema</a>
     */
    public static boolean checkFiscalCode(String fc) {
        return fc.matches("^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$");
    }

    /**
     * Generate the password using {@link BCrypt} gen method.
     *
     * @param plaintext the password to hash
     * @return the hashed string
     * @see BCrypt#hashpw(String, String) 
     */
    public static String generatePassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    /**
     * Changes the password of a user, if the old password is wrong
     * it doesn't and returns {@code true}
     *
     * @param new_password the new password of the user
     * @param user         the user to change the password
     * @return if the password change was successfully
     */
    public static Boolean changePassword(String new_password, User user) {
        if(checkPassword(new_password, user.getPassword())) {
            user.setPassword(generatePassword(new_password));
            return true;
        }
        return false;
    }

    public static User getUserFromUUID(UUID uuid) {
        for(User current : LoadedData.users) {
            if(Objects.equals(current.getUUID(), uuid.toString())) {
                return current;
            }
        }
        return null;
    }

    public static Pair<Boolean, User> doesUserExist(User user) {
        for(User current : LoadedData.users) {
            if(user.equals(current)) {
                return new Pair<Boolean, User>(true, current);
            }
        }
        return new Pair<Boolean, User>(false, null);
    }

    public static Pair<Boolean, User> createUser(String name, String lastname, String fiscal_code,
                                     String email, String password){
        UUID uuid = UUID.randomUUID();
        User new_user = new User(name, lastname, fiscal_code, email, "", uuid.toString());
        if(!doesUserExist(new_user).getKey()) {
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
