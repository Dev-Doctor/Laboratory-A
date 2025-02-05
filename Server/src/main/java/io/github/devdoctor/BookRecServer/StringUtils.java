package io.github.devdoctor.BookRecServer;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String replaceWithChar(String str, char character) {
        return str.replaceAll(".", String.valueOf(character));
    }
}