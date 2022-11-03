package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import Request.EventIDRequest;
import Result.EventIDResult;

import java.sql.Connection;

public class EventIDService {
    EventIDRequest request;

    public EventIDService(EventIDRequest request) {
        this.request = request;
    }

    public EventIDRequest getService() {
        return request;
    }

    public void setService(EventIDRequest request) {
        this.request = request;
    }

    public EventIDResult execute() {
        Database database = new Database();
        try {
            Connection connection = database.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(connection);
            EventDao eventDao = new EventDao(connection);
            String username = authTokenDao.getUsernameFromAuthToken(request.getToken());
            Event event = eventDao.getEventByID(username, request.getEventID());
            database.closeConnection(false);
            if (event != null) {
                return new EventIDResult(event, null, true);
            }
            return new EventIDResult(null, "Error: No event found", false);
        } catch (DataAccessException e) {
            e.printStackTrace();
            database.closeConnection(false);
            return new EventIDResult(null, "Error: Failed to execute EventIDService", false);
        }
    }
}
