package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Request.PersonIDRequest;
import Result.PersonIDResult;

import java.sql.Connection;
import java.util.List;

public class PersonIDService {
    private PersonIDRequest request;

    public PersonIDService(PersonIDRequest request) {
        this.request = request;
    }

    public PersonIDResult execute() {
        Database database = new Database();
        try {
            // init
            Connection connection = database.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(connection);
            PersonDao personDao = new PersonDao(connection);

            // get the username associated with the token
            String username;
            try {
                username = authTokenDao.getUsernameFromAuthToken(request.getToken());
            } catch (DataAccessException e) {
                e.printStackTrace();
                database.closeConnection(false);
                return new PersonIDResult(null, "Error: Bad authtoken", false);
            }
            Person person;
            try {
                person = personDao.getPersonByID(username, request.getId());
            } catch (DataAccessException e) {
                e.printStackTrace();
                database.closeConnection(false);
                return new PersonIDResult(null, "Error: personID not found", false);
            }
            database.closeConnection(false);
            if (person != null) {
                return new PersonIDResult(person, null, true);
            } else {
                return new PersonIDResult(null, "Error: No person found", false);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            database.closeConnection(false);
            return new PersonIDResult(null, "Error: Server error", false);
        }
    }

    public PersonIDRequest getRequest() {
        return request;
    }

    public void setRequest(PersonIDRequest request) {
        this.request = request;
    }
}
