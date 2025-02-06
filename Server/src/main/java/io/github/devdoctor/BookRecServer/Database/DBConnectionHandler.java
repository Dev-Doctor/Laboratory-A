/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecServer.Database;

import io.github.devdoctor.BookRecServer.ServerState;

import java.sql.SQLException;

public class DBConnectionHandler {
    private static String DEFAULT_PORT = "5432";
    private DatabaseConnectionSingleton dbConnSingleton;
    private ServerState serverState;

    public DBConnectionHandler() throws SQLException {
        serverState = ServerState.getDefaultState();
    }

    public DBConnectionHandler(String ip, String username, String password, String database) throws SQLException {
        serverState = ServerState.getDefaultState();
        this.createConnection(ip, database, username, password);
    }

    private void createConnection(String host, String database, String username, String password) throws SQLException {
        String port = DEFAULT_PORT;

        if (host.contains(":")) {
            host = host.split(":")[0];
            port = host.split(":")[1];
        }

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        dbConnSingleton = DatabaseConnectionSingleton.getInstance(url, username, password);

        if (dbConnSingleton.isConnected()) {
            serverState = serverState.getNextStatus();
        }
    }

    public ServerState getCurrentState() {
        return serverState;
    }
}
