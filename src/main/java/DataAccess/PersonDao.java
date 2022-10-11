package DataAccess;

import Model.Person;

import java.sql.Connection;

/**
 * A data access object for Persons
 */
public class PersonDao {
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
     */
    void insert(Person person) {

    }

    /**
     * Finds and returns (if any) a person whose ID is the same as given
     * @param id the ID of the person to find
     * @return the found Person
     */
    Person getPersonByID(String id) {
        return null;
    }

    /**
     * Deletes a given person from the database
     * @param person the person to delete
     */
    void delete(Person person) {

    }
}
