package io.github.devdoctor.BookRecServer;

import java.sql.SQLException;

public class ConnectionHandler {
    private static String DEFAULT_PORT = "5432";
    private DatabaseConnectionSingleton dbConnSingleton;
    private ServerState serverState;

    public ConnectionHandler() throws SQLException {
        serverState = ServerState.getDefaultState();
    }

    public ConnectionHandler(String ip, String username, String password, String database) throws SQLException {
        serverState = ServerState.getDefaultState();
        this.createConnection(ip, database, username, password);
    }

    public void createConnection(String host, String database, String username, String password) throws SQLException {
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
