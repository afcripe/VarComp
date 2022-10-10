package net.dahliasolutions.varcomp;

import javafx.event.Event;
import javafx.event.EventType;

public class CustomEvent extends Event {

    private static final long serialVersionUID = 3808615559898349350L;
    private final Object object;
    public static final EventType<CustomEvent> OBJECT = new EventType<>(Event.ANY, "ANY");
    public static final EventType<CustomEvent> ANY = OBJECT;

    public CustomEvent(EventType<? extends Event> eventType, Object object) {
        super(eventType);
        this.object = object;
    }

    public Object getObject() {
        return this.object;
    }
}
