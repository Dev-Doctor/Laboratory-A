package io.github.devdoctor.deltabooks.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class Event<T> {
    final Class<T> typeParameterClass;

    /** List of the listeners of the {@code Event} */
    protected List<T> listeners = new ArrayList<T>();

    @SuppressWarnings("unchecked")
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
