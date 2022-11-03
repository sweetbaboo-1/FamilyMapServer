package DataAccess;

import Model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO Event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    public Event find(String eventID) throws DataAccessException {
        ResultSet resultSet;
        String sql = "SELECT * FROM Event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return new Event(resultSet.getString("eventID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"), resultSet.getFloat("latitude"), resultSet.getFloat("longitude"),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("eventType"),
                        resultSet.getInt("year"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    public Event getEventByType(String personID, String eventType) throws DataAccessException {
        String sql = "SELECT * FROM EVENT WHERE personID = ? AND eventType = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet;
            stmt.setString(1, personID);
            stmt.setString(2, eventType);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return new Event(resultSet.getString("eventID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"), resultSet.getFloat("latitude"), resultSet.getFloat("longitude"),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("eventType"),
                        resultSet.getInt("year"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event by type");
        }
    }

    public List<Event> getAllEventsRelatedToUsername(String username) throws DataAccessException {
        String sql = "SELECT * FROM EVENT WHERE associatedUsername = ?;";
        List<Event> events = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet;
            stmt.setString(1, username);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                events.add(new Event(resultSet.getString("eventID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"), resultSet.getFloat("latitude"), resultSet.getFloat("longitude"),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("eventType"),
                        resultSet.getInt("year")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while fetching all events associated with " + username);
        }
        return events;
    }

    public Event getEventByID(String username, String eventID) throws DataAccessException {
        String sql = "SELECT * FROM EVENT WHERE associatedUsername = ? and eventID = ?;";
        Event event;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet;
            stmt.setString(1,username);
            stmt.setString(2,eventID);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return new Event(resultSet.getString("eventID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("personID"), resultSet.getFloat("latitude"), resultSet.getFloat("longitude"),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("eventType"),
                        resultSet.getInt("year"));
            }
            throw new DataAccessException("Error: No Event matching description found");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while fetching Event by ID");
        }
    }
}
