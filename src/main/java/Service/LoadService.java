package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;

/**
 * A service that tries to load a new database from a given dataset
 */
public class LoadService {
    LoadRequest request;

    public LoadService(LoadRequest request) {
        this.request = request;
    }

    public LoadRequest getRequest() {
        return request;
    }

    public void setRequest(LoadRequest request) {
        this.request = request;
    }

    /**
     * Attempts to load the database from a given dataset
     * @return the result of the attempt
     */
    public LoadResult execute() {
        Database database = new Database();
        try {
            // init
            Connection connection = database.getConnection();
            UserDao userDao = new UserDao(connection);
            EventDao eventDao = new EventDao(connection);
            PersonDao personDao = new PersonDao(connection);
            AuthTokenDao authTokenDao = new AuthTokenDao(connection);

            // clear everything
            personDao.clear();
            eventDao.clear();
            userDao.clear();
            authTokenDao.clear();

            // keep track of things we insert
            int x = 0, y = 0, z = 0;

            // insert everything
            for (User user: request.getUsers()) {
                if(userDao.insert(user)) {
                    x++;
                }
            }
            for (Person person: request.getPersons()) {
                personDao.insert(person);
                y++;
            }
            for (Event event: request.getEvents()) {
                eventDao.insert(event);
                z++;
            }

            // close what we open
            database.closeConnection(true);

            // let them know we succeeded
            return new LoadResult("Successfully added " + x + " users, " + y + " persons, and " + z + " events to the database", true);
        } catch (DataAccessException e) {
            e.printStackTrace();

            // don't keep any changes we made
            database.closeConnection(false);

            // let them know we failed
            return new LoadResult("Error: failed to execute LoadService", false);
        }
    }
}
