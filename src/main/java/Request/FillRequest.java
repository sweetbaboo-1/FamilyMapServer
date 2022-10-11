package Request;

import Result.FillResult;
import jdk.jfr.Unsigned;

/**
 * Populates the database with people
 */
public class FillRequest {
    /**
     *
     * @param username the username of the person to fill data for
     * @param gens OPTIONAL number of generations default is 4 must be positive
     * @return an object containing the result of the fill request
     */
    public FillResult makeFillRequest(String username, int... gens) {
        return null;
    }
}
