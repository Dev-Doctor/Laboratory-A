/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.events;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles event
 * @param <T> the listener implementation
 */
public abstract class Event<T> {
    // the class that extends the listener implementation
    final Class<T> typeParameterClass;

    /** List of the listeners of the {@code Event} */
    protected List<T> listeners = new ArrayList<T>();

    public Event(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    /**
     * Add a {@code listener} to the event.
     *
     * @param toAdd the listener to add
     */
    public void addListener(T toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Remove a {@code listener} from the event.
     *
     * @param toRemove the listener to remove
     */
    public void removeListener(T toRemove) {
        listeners.remove(toRemove);
    }

    public abstract void fire();
}
