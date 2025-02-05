/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecServer;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        String ip = "", username = "", password = "", database = "";

        System.out.println("Welcome to BookRec Server!");

        ConnectionHandler connectionHandler = InizializeConnection(scanner);


        scanner.close();
    }

    public static ConnectionHandler InizializeConnection(Scanner scanner) {
        boolean quit = false;
        String ip, username, password, database;
        ConnectionHandler connectionHandler = null;

        do {
            ip = CMDInputUtils.askStringWithDefault(scanner, "Insert the database ip address", "localhost");
            username = CMDInputUtils.askStringWithDefault(scanner, "Insert the username", "postgres");
            password = CMDInputUtils.askPassword();
            database = CMDInputUtils.askStringWithDefault(scanner, "Insert the database", "bookrecommender");

            System.out.printf("Summary: \nIP: %s \nDatabase: %s \nUsername: %s \nPassword: %s \nCorrect? [y/n]%n", ip, database, username, StringUtils.replaceWithChar(password, '*'));
            quit = CMDInputUtils.mapYesNo(scanner);

            if (quit) {
                try {
                    connectionHandler = new ConnectionHandler(ip, username, password, database);
                } catch (SQLException e) {
                    quit = false;
                    if (e.getCause() instanceof UnknownHostException) {
                        System.err.println("Unkown host: " + e.getCause().getMessage());
                    } else {
                        System.err.println(e.getMessage());
                    }
                }
            }

        } while (!quit);

        return connectionHandler;
    }
}
