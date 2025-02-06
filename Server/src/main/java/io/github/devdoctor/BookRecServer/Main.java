/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecServer;

import Utils.CMDInputUtils;
import Utils.StringUtils;
import io.github.devdoctor.BookRecServer.Console.ConsoleThread;
import io.github.devdoctor.BookRecServer.Database.DBConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static final int PORT = 8080;
    private static ServerSocket serverSocket;
    private static ArrayList<ClientHandler> connectedClients;

    public static void main(String[] args) {
        // initialize scanner
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        connectedClients = new ArrayList<ClientHandler>();

        // welcome the admin
        System.out.println("Welcome to BookRec Server!");

        // create the connection to the database
        DBConnectionHandler dbConnectionHandler = InizializeConnection(scanner, args);

        // prepare the console
        ConsoleThread consoleThread = new ConsoleThread(scanner);

        // inizialize the server
        try {
            // create the server socket
            serverSocket = new ServerSocket(PORT);
            // listen to clients connections
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    public static DBConnectionHandler InizializeConnection(Scanner scanner, String[] args) {
        boolean quit = false;
        String ip, username, password, database;
        DBConnectionHandler dbConnectionHandler = null;

        if(checkArgs(args)) {
            try {
                dbConnectionHandler = new DBConnectionHandler("localhost", "root", "R3st3ll1", "bookrecommender");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return dbConnectionHandler;
        }

        do {
            ip = CMDInputUtils.askStringWithDefault(scanner, "Insert the database ip address", "localhost");
            username = CMDInputUtils.askStringWithDefault(scanner, "Insert the username", "postgres");
            password = CMDInputUtils.askPassword();
            database = CMDInputUtils.askStringWithDefault(scanner, "Insert the database", "bookrecommender");

            System.out.printf("Summary: \nIP: %s \nDatabase: %s \nUsername: %s \nPassword: %s \nCorrect? [y/n]%n", ip, database, username, StringUtils.replaceWithChar(password, '*'));
            quit = CMDInputUtils.mapYesNo(scanner);

            if (quit) {
                try {
                    dbConnectionHandler = new DBConnectionHandler(ip, username, password, database);
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

        // ask user if wants to save configuration

        return dbConnectionHandler;
    }

    public static boolean checkArgs(String[] args) {
        if(args.length == 0) {
            return false;
        }

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(args));
        if(list.contains("full")) {
            return true;
        }
        return false;
    }
}
