package io.github.devdoctor.deltabooks.events;


/**
 * The interface to implement for handling the {@code LoginEvent}
 *
 * @see LoginEvent
 */
public interface LoginEventListener {
    void onLogin();

    // not yet implemented
    // void onLogout();
}