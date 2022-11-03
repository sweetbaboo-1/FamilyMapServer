package DataAccess;

import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object for Persons
 */
public class PersonDao {
    /**
     * The connection to the server
     */
    private final Connection connection;

    /**
     * Constructor
     * @param connection the connection to the database
     */
    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a given person to the database
     * @param person the person to add
     * @throws DataAccessException
     */
    public boolean insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * Finds and returns (if any) a person whose ID is the same as given
     * @param id the ID of the person to find
     * @return the found Person
     */
    public Person getPersonByID(String username, String id) throws DataAccessException {
        Person person;
        ResultSet resultSet;
        String sql = "SELECT * FROM Person WHERE personID = ? AND associatedUsername = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, username);
            resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                person = new Person(
                        resultSet.getString("personID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("fatherID"),
                        resultSet.getString("motherID"),
                        resultSet.getString("spouseID"));
                return person;
            } else {
                throw new DataAccessException("Error: No person found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Deletes a given person from the database
     * @param id the ID of the person to delete
     */
    void delete(String id) throws DataAccessException {
        String sql = "DELETE FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while deleting person from the Person table");
        }
    }

    /**
     * Clears all people from the table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while clearing the Person table");
        }
    }

    public List<Person> getAllPeople() throws DataAccessException {
        List<Person> people = new ArrayList<>();
        ResultSet resultSet;
        String sql = "SELECT * FROM Person;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                people.add(new Person(
                        resultSet.getString("personID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("fatherID"),
                        resultSet.getString("motherID"),
                        resultSet.getString("spouseID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while finding a person in the database");
        }
        return people;
    }

    public List<Person> getAllChildren(String personID) throws DataAccessException {
        List<Person> people = new ArrayList<>();
        ResultSet resultSet;
        String sql = "SELECT * FROM Person WHERE motherID = ? OR fatherID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.setString(2, personID);
            resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                people.add(new Person(
                        resultSet.getString("personID"),
                        resultSet.getString("associatedUsername"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("fatherID"),
                        resultSet.getString("motherID"),
                        resultSet.getString("spouseID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while collecting all children from a person in the database");
        }
        return people;
    }

    public List<Person> getAllKnownAssociates(String username) throws DataAccessException {
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?";
        List<Person> people = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                people.add(new Person(resultSet.getString("personID"), resultSet.getString("associatedUsername"),
                        resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("gender"),
                        resultSet.getString("fatherID"), resultSet.getString("motherID"), resultSet.getString("spouseID")));
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while fetching all known associates");
        }
    }
}
