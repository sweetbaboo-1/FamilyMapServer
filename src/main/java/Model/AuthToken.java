package Model;

/**
 * An object representing a token used to authorize an individual
 */
public class AuthToken {
    private String authtoken;
    private String username;

    /**
     * Constructor
     * @param authtoken the token used to authorize
     * @param username the username of owner of this token
     */
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
}
