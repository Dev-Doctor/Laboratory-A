/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Database;

import io.github.devdoctor.BookRecommender.enums.ServerState;
import org.apache.catalina.startup.ConnectorCreateRule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHandler {
    public static int DEFAULT_PORT = 5432;
    private ConnCredentials connCredentials;
    private String url;
    private ServerState serverState;

    public DBConnectionHandler(ConnCredentials credentials) throws SQLException {
        createConnection(credentials);
    }

    private void createConnection(ConnCredentials credentials) throws SQLException {
        this.url = "jdbc:postgresql://" + credentials.getIp() + ":" + credentials.getPort() + "/" + credentials.getDatabase();
        connCredentials = credentials;

        // check if connection is valid
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, connCredentials.getUsername(), connCredentials.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ServerState getCurrentState() {
        return serverState;
    }
}
