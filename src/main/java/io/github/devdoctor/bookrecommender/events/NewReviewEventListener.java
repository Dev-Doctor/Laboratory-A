/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.bookrecommender.events;

/**
 * The interface to implement for handling the {@code NewReviewEvent}
 *
 * @author DevDoctor
 * @see NewReviewEvent
 */
public interface NewReviewEventListener {
    void onNewReview();
}
