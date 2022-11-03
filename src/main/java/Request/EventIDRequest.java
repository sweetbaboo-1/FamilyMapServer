package Request;

public class EventIDRequest {
    String eventID;
    String token;

    public EventIDRequest(String eventID, String token) {
        this.eventID = eventID;
        this.token = token;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
