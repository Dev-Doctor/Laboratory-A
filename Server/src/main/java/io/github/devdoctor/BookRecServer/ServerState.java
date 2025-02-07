/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecServer;

public enum ServerState {
    STOPPING(getDefaultState()),
    ACCEPTS_CLIENTS(STOPPING),
    CONNECTED(ACCEPTS_CLIENTS),
    IDLE(CONNECTED);

    private final ServerState nextServerState;

    private ServerState(ServerState nextServerState) {
        this.nextServerState = nextServerState;
    }

    public ServerState getNextStatus() {
        return nextServerState;
    }

    public static ServerState getDefaultState() {
        return ServerState.IDLE;
    }
}
