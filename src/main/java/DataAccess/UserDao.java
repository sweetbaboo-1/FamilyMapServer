package DataAccess;

import Model.User;

import java.sql.Connection;

/**
 * A data access object for Users
 */
public class UserDao {
    private Connection connection;

    /**
     * Constructor
     * @param connection the connection to the database
     */
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a given user to the database
     * @param user the user to add
     */
    void insert(User user) {

    }

    /**
     * Determins if a given username and password are legit
     * @param username the username to validate
     * @param password the password associated with the username
     * @return true if the user + password combo is stored in the database
     */
    boolean validate(String username, String password) {
        return false;
    }

    /**
     * Serches the database for a user that has a given user ID
     * @param id the ID of the user to search for
     * @return The user whose ID matches the given one
     */
    User getUserByID(String id) {
        return null;
    }

    /**
     * Deletes a given user from the database
     * @param user the user to delete
     */
    void deleteUser(User user) {

    }
}
