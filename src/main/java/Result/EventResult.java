package Result;

import Model.Event;

import java.util.List;

/**
 * An object containing the result of the clear attempt
 */
public class EventResult {
    private Event[] events;
    private boolean success;
    private String msg;

    public EventResult(Event[] events, String msg, boolean success) {
        this.events = events;
        this.success = success;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
