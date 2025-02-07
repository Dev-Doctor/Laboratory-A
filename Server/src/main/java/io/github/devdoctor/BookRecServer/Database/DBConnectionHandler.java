/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecServer.Database;

import io.github.devdoctor.BookRecServer.ServerState;

import java.sql.SQLException;

public class DBConnectionHandler {
    public static int DEFAULT_PORT = 5432;
    private DatabaseConnection dbConnSingleton;
    private ServerState serverState;

    public DBConnectionHandler(ConnCredentials credentials) throws SQLException {
        createConnection(credentials);
    }

    private void createConnection(ConnCredentials credentials) throws SQLException {
        String url = "jdbc:postgresql://" + credentials.getIp() + ":" + credentials.getPort() + "/" + credentials.getDatabase();
        dbConnSingleton = DatabaseConnection.getInstance(url, credentials.getUsername(), credentials.getPassword());

        if (dbConnSingleton.isConnected()) {
            serverState = serverState.getNextStatus();
        }
    }

    public ServerState getCurrentState() {
        return serverState;
    }
}
