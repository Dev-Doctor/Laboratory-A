/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package Utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String replaceWithChar(String str, char character) {
        return str.replaceAll(".", String.valueOf(character));
    }
}