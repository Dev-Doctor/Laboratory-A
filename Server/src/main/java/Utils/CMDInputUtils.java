package Utils;

import javax.swing.*;
import java.io.Console;
import java.util.Scanner;

public class CMDInputUtils {

    public static String askStringWithDefault(Scanner scanner, String prompt, String defaultValue) {
        System.out.printf("%s (enter for: %s): ", prompt, defaultValue);

        String input = scanner.nextLine();
        return StringUtils.isNullOrEmpty(input) ? defaultValue : input;
    }

    public static String askValidString(Scanner scanner, String prompt) {
        boolean quit = false;

        System.out.print(prompt + ": ");

        String result = null;

        do {
            result = scanner.nextLine();
            if(result == null || result.isEmpty()) {
                System.out.println("Invalid input");
                System.out.print(prompt + ": ");
            } else {
                quit = true;
            }
        } while (!quit);

        return result;
    }

    public static boolean mapYesNo(Scanner scanner) {
        scanner.hasNext();
        boolean result = false, quit = false;
        do {
            char choice = scanner.next().charAt(0);
            switch (Character.toLowerCase(choice)) {
                case 'y':
                    result = true;
                    quit = true;
                    break;
                case 'n':
                    result = false;
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input, please write 'y' or 'n'.");
            }

            if(scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } while (!quit);
        return result;
    }

    public static String askPassword() {
        return askPassword("Enter password");
    }

    public static String askPassword(String prompt) {
        Console console = System.console();
        String password;

        if (console == null) {
            do {
                password = getPasswordWithoutConsole(prompt);
            } while (StringUtils.isNullOrEmpty(password));
        } else {
            password = String.valueOf(console.readPassword(prompt));
        }
        return password;
    }

    /* https://stackoverflow.com/a/74740615 */
    private static String getPasswordWithoutConsole(String prompt) {

        final JPasswordField passwordField = new JPasswordField();
        return JOptionPane.showConfirmDialog(
                null,
                passwordField,
                prompt,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION ? new String(passwordField.getPassword()) : "";
    }
}
