package Model;

import java.util.UUID;

/**
 * An object representing a token used to authorize an individual
 */
public class AuthToken {
    /**
     * The token used to authenticate a user
     */
    private String authtoken;

    /**
     * The username of the owner of the token
     */
    private String username;

    /**
     * Constructor
     * @param username the username of owner of this token
     */
    public AuthToken(String username) {
        this.authtoken = UUID.randomUUID().toString();
        this.username = username;
    }

    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return authtoken;
    }
}
