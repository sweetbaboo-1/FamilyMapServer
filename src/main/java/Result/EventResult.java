package Result;

import Model.Event;

/**
 * An object containing the result of the clear attempt
 */
public class EventResult {
    private Event event;
    private boolean success;

    /**
     * Constructor
     * @param event the event returned from the database
     * @param success if the event request succeeded or failed
     */
    public EventResult(Event event, boolean success) {
        this.event = event;
        this.success = success;
    }

    public Event getEvent() {
        return event;
    }

    public boolean isSuccess() {
        return success;
    }
}
