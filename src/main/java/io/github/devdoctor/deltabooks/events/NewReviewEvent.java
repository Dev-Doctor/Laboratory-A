/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.deltabooks.events;

/**
 * The {@code Event} that handles the new review in the program.
 *
 * @author DevDoctor
 */
public class NewReviewEvent extends Event {
    public NewReviewEvent() {
        super(NewReviewEventListener.class);
    }

    /**
     * This is run when a new {@code Review} is created.
     */
    @Override
    public void fire() {

    }
}
