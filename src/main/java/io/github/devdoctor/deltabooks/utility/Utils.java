package io.github.devdoctor.deltabooks.utility;

public class Utils {

    public static String capitalize(String str) {
        if(str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static Float calculateAverage(int[] i) {
        float result = 0f;
        for (int j : i) {
            result += j;
        }
        return result / i.length;
    }

    public static String cutStringSize(String str) {
        try {
            str = str.substring(0, 10);
            str += "..";
        } catch (Exception ignored) {
        }
        return str;
    }
}
