package io.github.devdoctor.BookRecServer;

import org.postgresql.util.PSQLException;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSingleton {

    private static DatabaseConnectionSingleton instance;

    private final Connection connection;
    private static boolean connected;

    // Private constructor to prevent instantiation
    private DatabaseConnectionSingleton(String url, String user, String password) throws SQLException {
        Connection temp;
//        try {
        temp = DriverManager.getConnection(url, user, password);
//        connected = true;
//        } catch (SQLException e) {
//            if(e.getCause() instanceof UnknownHostException) {
//                System.err.println("Unkown host: " + e.getCause().getMessage());
//            } else {
//                System.err.println(e.getMessage());
//            }

//        temp = null;
//        connected = false;
//        }
        this.connection = temp;
    }

    // Static method to initialize the singleton with parameters
    public static synchronized DatabaseConnectionSingleton getInstance(String url, String user, String password) throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionSingleton(url, user, password);
        }
        return instance;
    }

    // Getter for the connection
    public Connection getConnection() {
        return connection;
    }

    // Close the connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connected = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
