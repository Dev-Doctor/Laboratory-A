/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecServer;

import Utils.CMDInputUtils;
import Utils.StringUtils;
import io.github.devdoctor.BookRecServer.Console.ConsoleThread;
import io.github.devdoctor.BookRecServer.Database.ConnCredentials;
import io.github.devdoctor.BookRecServer.Database.DBConnectionHandler;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        DBConnectionHandler dbConnectionHandler = InitializeConnection(scanner, args);

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

    /**
     * Gets the data for the connection from the console arguments before asking the user if they are not found.
     * Tries to connect to the database, if it wasn't successful try again.
     * @param scanner the current scanner to get input from user
     * @param args the arguments of the java application
     * @return the {@code connectionHandler} if the connection was successful
     */
    public static DBConnectionHandler InitializeConnection(Scanner scanner, String[] args) {
        // quit: is the flag for exiting.
        // skip: for skipping confirmation (dictates from the S argument).
        // reset: flag for asking the user input after provided a not valid one.
        boolean quit, skip = false, reset = false;

        String ip, username, password, database;
        int port;
        DBConnectionHandler dbConnectionHandler = null;

        Options options = SetCmdOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            ip = cmd.getOptionValue("s");
            if (cmd.getParsedOptionValue("p") != null) {
                port = (Integer) cmd.getParsedOptionValue("p");
            }
            username = cmd.getOptionValue("u");
            password = cmd.getOptionValue("w");
            database = cmd.getOptionValue("d");

            if(cmd.hasOption("S")) {
                skip = true;
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        do {
            // this hurts to look at but is necessary to prevent null values
            if (ip == null || reset) {
                ip = CMDInputUtils.askStringWithDefault(scanner, "Insert the database ip address", "localhost");
            }
            if (username == null || reset) {
                username = CMDInputUtils.askStringWithDefault(scanner, "Insert the username", "postgres");
            }
            if (password == null || reset) {
                password = CMDInputUtils.askPassword();
            }
            if (database == null || reset) {
                database = CMDInputUtils.askStringWithDefault(scanner, "Insert the database", "bookrecommender");
            }

            // store the connection credentials
            ConnCredentials credentials = ConnCredentials.builder()
                    .ip(ip).username(username)
                    .password(password).database(database)
                    .build();

            // if the argument (S) is set skip the confirmation
            quit = skip || PrintConfiguration(scanner, credentials);

            if (quit) {
                try {
                    // try to connect
                    dbConnectionHandler = new DBConnectionHandler(credentials);
                    // wait some time to prevent cmd visual bug
                    TimeUnit.SECONDS.sleep(3);
                } catch (SQLException e) {
                    quit = false;
                    if (e.getCause() instanceof UnknownHostException) {
                        System.err.println("Unkown host: " + e.getCause().getMessage());
                    } else {
                        System.err.println(e.getMessage());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            skip = false;
            reset = true;
        } while (!quit);

        // ask user if wants to save configuration

        return dbConnectionHandler;
    }


    /**
     * Prints the current tested configuration to the user and asks if it's valid.
     * @param scanner the scanner to read from console.
     * @param credentials the credentials to print out.
     * @return if the user accepted or not the configuration.
     */
    private static boolean PrintConfiguration(Scanner scanner, ConnCredentials credentials) {
        System.out.printf("Summary: \nIP: %s:%s \nDatabase: %s \nUsername: %s \nPassword: %s \nCorrect? [y/n]%n",
                credentials.getIp(),
                credentials.getPort(),
                credentials.getDatabase(),
                credentials.getUsername(),
                StringUtils.replaceWithChar(credentials.getPassword(), '*'));
        return CMDInputUtils.mapYesNo(scanner);
    }

    /**
     * Creates the options to check in the arguments passed at the start of the application.
     * @return the initialized options
     */
    private static Options SetCmdOptions() {
        Options options = new Options();
        // The server address to connect to. Usage: -s <ip> or -server <ip>
        options.addOption(Option.builder("s").longOpt("server").hasArg().desc("The server address to connect to.").build());
        // The server port (default: 5432) to connect to. Usage: -p <port> or -port <port>
        options.addOption(Option.builder("p").longOpt("port").hasArg().desc("The server port (default: 5432) to connect to.").type(Number.class).build());
        // The database user nickname. Usage: -u <user> or -user <user>
        options.addOption(Option.builder("u").longOpt("user").hasArg().desc("The database user nickname.").build());
        // The database user password. Usage: -w <password> or -password <password>
        options.addOption(Option.builder("w").longOpt("password").hasArg().desc("The database user password.").build());
        // The target database. Usage: -d <database> or -database <database>
        options.addOption(Option.builder("d").longOpt("database").hasArg().desc("The target database.").build());
        // Add this option if the connection confirmation should be skipped. Usage: -S or -skip
        options.addOption(Option.builder("S").longOpt("skip").desc("Skip confirmation.").build());

        return options;
    }

    public static boolean checkArgs(String[] args) {
        if (args.length == 0) {
            return false;
        }

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(args));
        if (list.contains("full")) {
            return true;
        }
        return false;
    }
}
