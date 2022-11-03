package DataAccess;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object for AuthTokens
 */
public class AuthTokenDao {
    /**
     * The connection to the server
     */
    private final Connection connection;

    /**
     * Constructor
     * @param connection the connection to the database
     */
    public AuthTokenDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserts a token and it's associated username into the database
     * @param token the token to add
     * @throws DataAccessException
     */
    public void insert(AuthToken token) throws DataAccessException {
        String sql = "INSERT INTO AuthToken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, token.toString());
            statement.setString(2, token.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an AuthToken into the database");
        }
    }

    /**
     * Validates a username and token
     * @param token the token to validate
     * @return true if the username and token combination exists in the database, else false
     */
    boolean validate(String token) {
        return false;
    }

    public List<AuthToken> getAllTokens() throws DataAccessException {
        List<AuthToken> tokens = new ArrayList<>();
        ResultSet resultSet;
        String sql = "SELECT * FROM AuthToken;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            resultSet = stmt.executeQuery();
            while(resultSet.next()) {
                tokens.add(new AuthToken(
                        resultSet.getString("authtoken"),
                        resultSet.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while getting all tokens from the database");
        }
        return tokens;
    }


    /**
     * Clears everything from the AuthToken table
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM AuthToken";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccess.DataAccessException("Error encountered while clearing the AuthToken table");
        }
    }

    public String getPersonIDFromToken(String token) throws DataAccessException {
        String sql = "SELECT username FROM AuthToken WHERE authtoken = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                sql = "SELECT personID FROM User WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getString("personID");
                    } else {
                        throw new DataAccessException("Error: no person associated with this token ...wtf?");
                    }
                }
            } else {
                throw new DataAccessException("Error: invalid AuthToken");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while retrieving personID using AuthToken");
        }
    }

    public String getUsernameFromAuthToken(String token) throws DataAccessException {
        String sql = "SELECT * FROM AuthToken WHERE authtoken = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("username");
            } else {
                throw new DataAccessException("Error: Invalid AuthToken");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while retrieving personID using AuthToken");
        }
    }
}
