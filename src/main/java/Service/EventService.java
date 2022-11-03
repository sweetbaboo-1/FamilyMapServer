package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import Request.EventRequest;
import Result.EventResult;

import java.sql.Connection;
import java.util.List;

/**
 * A service that requests an event from the database
 */
public class EventService {
    EventRequest request;

    public EventService(EventRequest request) {
        this.request = request;
    }

    public EventRequest getRequest() {
        return request;
    }

    public void setRequest(EventRequest request) {
        this.request = request;
    }

    public EventResult execute() {
        Database database = new Database();
        try {
            Connection connection = database.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(connection);
            String username = authTokenDao.getUsernameFromAuthToken(request.getToken());
            EventDao eventDao = new EventDao(database.getConnection());
            List<Event> events = eventDao.getAllEventsRelatedToUsername(username);
            database.closeConnection(false);
            if (events.size() > 0) {
                Event[] eventAry = new Event[events.size()];
                int i = 0;
                for (Event event : events) {
                    eventAry[i] = event;
                    i++;
                }
                return new EventResult(eventAry, null, true);
            } else {
                return new EventResult(null , "Error: No events found", false);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            database.closeConnection(false);
            return new EventResult(null, "Error: failed to execute EventService", false);
        }
    }
}
