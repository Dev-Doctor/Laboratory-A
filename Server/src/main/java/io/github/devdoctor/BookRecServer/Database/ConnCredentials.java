package io.github.devdoctor.BookRecServer.Database;

import io.github.devdoctor.BookRecServer.Main;
import org.apache.commons.cli.Option;

public class ConnCredentials {
    private String ip;
    private int port;
    private String username;
    private String password;
    private String database;

    private ConnCredentials(Builder builder) {
        this.ip = builder.ip;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.database = builder.database;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String ip;
        private int port;
        private String username;
        private String password;
        private String database;

        private Builder() {
            ip = null;
            port = DBConnectionHandler.DEFAULT_PORT;
            username = null;
            password = null;
            database = null;
        }

        public Builder defaults() {
            ip = "localhost";
            port = DBConnectionHandler.DEFAULT_PORT;
            username = "root";
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public ConnCredentials build() {
            return new ConnCredentials(this);
        }
    }
}
