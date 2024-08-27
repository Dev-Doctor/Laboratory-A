package io.github.devdoctor.deltabooks.events;

import io.github.devdoctor.deltabooks.LoadedData;
import io.github.devdoctor.deltabooks.User;
import io.github.devdoctor.deltabooks.utility.LibraryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Event} that handles login in the program.
 *
 * @author Davide Restelli
 */
public class LoginEvent {
    /** List of the listeners of the {@code Event} */
    private List<LoginEventListener> listeners = new ArrayList<LoginEventListener>();

    /**
     * Add a {@code listener} to the event.
     *
     * @param toAdd the listener to add
     */
    public void addListener(LoginEventListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Remove a {@code listener} from the event.
     *
     * @param toRemove the listener to remove
     */
    public void removeListener(LoginEventListener toRemove) {
        listeners.remove(toRemove);
    }

    /**
     * This is run when a {@code User} successfully logs in.
     *
     * @param user the user that logged in.
     */
    public void onLogin(User user) {
        LoadedData.logged_user = user;
        LoadedData.logged_user_libraries = LibraryUtils.loadLibraries();
        for(LoginEventListener listener : listeners) {
            listener.onLogin();
        }
    }
}
