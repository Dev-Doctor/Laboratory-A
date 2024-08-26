package io.github.devdoctor.deltabooks;

import java.util.ArrayList;
import java.util.List;

public class LoginEvent {
    private List<LoginListener> listeners = new ArrayList<LoginListener>();

    public void addListener(LoginListener toAdd) {
        listeners.add(toAdd);
    }

    public void removeListener(LoginListener toRemove) {
        listeners.remove(toRemove);
    }

    public void onLogin() {
        for(LoginListener listener : listeners) {
            listener.onLogin();
        }
    }
}
