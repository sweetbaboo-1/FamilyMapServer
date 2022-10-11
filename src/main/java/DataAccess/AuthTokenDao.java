package DataAccess;

import java.sql.Connection;

/**
 * A data access object for AuthTokens
 */
public class AuthTokenDao {
    private final Connection connection;

    /**
     * Constructor
     * @param connection the connection to the database
     */
    public AuthTokenDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Generates an auth token
     * @return the generated token
     */
    String generateToken() {
        return null;
    }

    /**
     * Validates a username and token
     * @param username the username to validate against the token
     * @param token the token to validate against the username
     * @return true if the username and token combination exists in the database, else false
     */
    boolean validate(String username, String token) {
        return false;
    }
}
