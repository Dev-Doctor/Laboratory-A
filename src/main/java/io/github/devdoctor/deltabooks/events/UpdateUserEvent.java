package io.github.devdoctor.deltabooks.events;

public class UpdateUserEvent extends Event {


    public UpdateUserEvent() {
        super(UpdateUserEventListener.class);
    }

    @Override
    public void fire() {
        System.out.println("Event fired -> " + this.getClass());
        for (Object listener : listeners) {
            ((UpdateUserEventListener) listener).onUpdate();
        }
    }
}
