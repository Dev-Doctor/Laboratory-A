package io.github.devdoctor.deltabooks.utility;

public class Utils {

    public static String capitalize(String str) {
        if(str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
