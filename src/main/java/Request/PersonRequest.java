package Request;

/**
 * Handles requests for a single given person
 */
public class PersonRequest {
    String token;

    public PersonRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
