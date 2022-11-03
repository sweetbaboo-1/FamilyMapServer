package DataAccess;

import Model.User;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A data access object for Users
 */
public class UserDao {
    /**
     * The connection to the server
     */
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
    public boolean insert(User user) throws DataAccessException {
        String sql = "INSERT INTO User (username, password, personID, firstName, lastName, email, gender) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getPersonID());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getGender());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Creates and inserts a user into the database
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @throws DataAccessException
     */
    public void insert(String username, String password, String personID, String email, String firstName, String lastName, String gender) throws DataAccessException {
        String sql = "INSERT INTO User (username, password, personID, firstName, lastName, email, gender) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String id = UUID.randomUUID().toString();
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, personID);
            stmt.setString(4, firstName);
            stmt.setString(5, lastName);
            stmt.setString(6, email);
            stmt.setString(7, gender);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Determines if a given username and password are legit
     * @param username the username to validate
     * @param password the password associated with the username
     * @return true if the user + password combo is stored in the database
     */
    public boolean validate(String username, String password) throws DataAccessException {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while validating a user");
        }

    }

    /**
     * Searches the database to see if a username already exists in the database
     * @param username the name to serach for
     * @return true if the given username is available, else false
     */
    public boolean validateUsername(String username) throws DataAccessException {
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.getString("username") == null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while validating a username");
        }
    }

    /**
     * Serches the database for a user that has a given user ID
     * @param id the ID of the user to search for
     * @return The user whose ID matches the given one
     */
    public User getUserByID(String id) throws DataAccessException {
        User user;
        ResultSet resultSet;
        String sql = "SELECT * FROM User WHERE PersonID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Returns a list of ALL users in the database
     * @return The list of users in the database
     */
    public List<User> getAllUsers() throws DataAccessException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet;
        String sql = "SELECT * FROM User;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                users.add(new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"),
                        resultSet.getString("personID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while finding a user in the database");
        }
        return users;
    }

    /**
     * Deletes a given user from the database
     * @param user the user to delete
     */
    public void deleteUser(@NotNull User user) throws DataAccessException {
        String sql = "DELETE FROM User WHERE username = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while deleting a user from the database");
        }
    }

    /**
     * Clears all users from the table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM User";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while clearing the User table");
        }
    }

    public String getUserID(String username) throws DataAccessException {
        String sql = "SELECT * FROM User where username = ?";
        ResultSet resultSet;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            resultSet = stmt.executeQuery();
            return resultSet.getString("personID");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a personID by username");
        }
    }
}
