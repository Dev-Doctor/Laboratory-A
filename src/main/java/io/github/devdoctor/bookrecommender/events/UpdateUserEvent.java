/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.events;

public class UpdateUserEvent extends Event {


    public UpdateUserEvent() {
        super(UpdateUserEventListener.class);
    }

    /**
     * This is run when the {@code User} data is changed.
     */
    @Override
    public void fire() {
        System.out.println("Event fired -> " + this.getClass());
        for (Object listener : listeners) {
            ((UpdateUserEventListener) listener).onUpdate();
        }
    }
}
