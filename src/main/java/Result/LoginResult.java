package Result;


import com.google.gson.JsonObject;

/**
 * An object containing the result of the login attempt
 */
public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private  boolean success;

    /**
     * Constructor
     * @param authtoken the authtoken for the login
     * @param username the username of the user trying to log in
     * @param personID the personID of the user
     * @param success if the login attempt succeeded or failed
     */
    public LoginResult(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean isSuccess() {
        return success;
    }
}
