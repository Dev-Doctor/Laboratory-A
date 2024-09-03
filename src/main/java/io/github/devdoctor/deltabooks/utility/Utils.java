package io.github.devdoctor.deltabooks.utility;

import java.awt.*;
import java.net.URI;

/**
 * Utility class providing various helper methods for common operations.
 * <p>
 * This class cannot be instantiated and is intended to be used in a static context.
 * </p>
 *
 * <p><b>Important:</b> The constructor is private to prevent instantiation.</p>
 *
 * @author DevDoctor
 * @since 1.0
 */
public class Utils {

    // Private constructor to prevent instantiation
    private Utils() {
        throw new UnsupportedOperationException("Utils cannot be instantiated");
    }

    /**
     * Capitalizes the first character of the given string.
     *
     * @param str the string to capitalize.
     * @return the input string with the first character capitalized.
     * Returns the original string if it is empty.
     */
    public static String capitalize(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * Calculates the average of an array of integers.
     *
     * @param i the array of integers.
     * @return the average of the integer values as a {@code Float}.
     */
    public static Float calculateAverage(int[] i) {
        float result = 0f;
        for (int j : i) {
            result += j;
        }
        return result / i.length;
    }

    /**
     * Trims the input string to a maximum of 10 characters and appends ".." to indicate truncation.
     *
     * @param str the string to be truncated.
     * @return the truncated string with a length of up to 12 characters,
     * or the original string if it is shorter than 10 characters.
     */
    public static String cutStringSize(String str) {
        try {
            str = str.substring(0, 10);
            str += "..";
        } catch (Exception ignored) {
        }
        return str;
    }

    /**
     * Opens the specified URL in the default web browser.
     *
     * @param url the URL of the website to open.
     */
    public static void openWebsite(String url) {
        try {
            // Open the website in the default browser
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
