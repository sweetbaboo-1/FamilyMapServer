package Result;

import Model.Event;

public class EventIDResult {
    private Event event;
    private String msg;
    private boolean success;

    public EventIDResult(Event event, String msg, boolean success) {
        this.event = event;
        this.msg = msg;
        this.success = success;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
