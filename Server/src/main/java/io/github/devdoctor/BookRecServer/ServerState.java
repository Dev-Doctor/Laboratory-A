package io.github.devdoctor.BookRecServer;

public enum ServerState {
    CONNECTED(null),
    IDLE(ServerState.CONNECTED);

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
