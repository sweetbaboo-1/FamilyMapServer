package Result;

/**
 * An object containing the results of a registration attempt
 */
public class RegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success;

    /**
     * Constructor
     * @param authtoken the authtoken for the attempt
     * @param username the username of the user trying to register
     * @param personID the ID of the user
     * @param success if the registration attempt succeeded or failed
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success) {
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
