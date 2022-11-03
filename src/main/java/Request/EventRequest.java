package Request;

import Result.EventResult;

/**
 * Requests information about an event
 */
public class EventRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EventRequest(String token) {
        this.token = token;
    }
}
