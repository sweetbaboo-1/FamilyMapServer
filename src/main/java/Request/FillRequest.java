package Request;

import Result.FillResult;
import jdk.jfr.Unsigned;

/**
 * Populates the database with people
 */
public class FillRequest {

    private String username;
    private int generations = 4;

    public FillRequest(String username, int generations) {
        this.username = username;
        if (generations != Integer.MIN_VALUE) {
            this.generations = generations;
        }
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
}
