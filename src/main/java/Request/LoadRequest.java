package Request;

import Model.Event;
import Model.Person;
import Model.User;
import Result.LoadResult;

/**
 * Clears all data from the database and loads the given information
 */
public class LoadRequest {

    /**
     * Clears the database and loads the given information into the database
     * @param users the users
     * @param people the person objects used to populate the database
     * @param events the events that relate to the person objects
     * @return an object containing the result of the load request
     */
    public LoadResult makeLoadRequest(User[] users, Person[] people, Event[] events) {
        return null;
    }
}
