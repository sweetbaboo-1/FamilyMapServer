package DataAccess;

import Model.Event;
import java.sql.Connection;
import java.util.List;

/**
 * A data access object for Events
 */
public class EventDao {
    private final Connection connection;

    /**
     * Constructor
     * @param connection the connection to the database
     */
    public EventDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a given event into the database
     * @param event the event to insert
     */
    public void insert(Event event) {

    }

    /**
     * Finds (if any) an event that has a given ID
     * @param eventID the ID of the event to find
     * @return the event whose id was given
     */
    public Event find(String eventID) {
        return null;
    }

    /**
     * Gets the list of events associated with a username
     * @param username the username of the person for whom to find the events
     * @return the list of all events associated with this username
     */
    public List<Event> findForUser(String username) {
        return null;
    }
}
