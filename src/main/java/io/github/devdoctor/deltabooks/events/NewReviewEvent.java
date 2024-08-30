package io.github.devdoctor.deltabooks.events;

public class NewReviewEvent extends Event {
    public NewReviewEvent() {
        super(NewReviewEventListener.class);
    }

    @Override
    public void fire() {

    }
}
