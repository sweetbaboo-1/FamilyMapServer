package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.List;

/**
 * A service that fetches a single person from the database
 */
public class PersonService {
    PersonRequest request;

    public PersonService(PersonRequest request) {
        this.request = request;
    }

    public PersonRequest getRequest() {
        return request;
    }

    public void setRequest(PersonRequest request) {
        this.request = request;
    }

    /**
     * Attempts to fetch all people associated with a person
     * @return the result of the request
     */
    public PersonResult execute() {
        try {
            // set up
            Database database = new Database();
            Connection connection = database.getConnection();
            PersonDao personDao = new PersonDao(connection);
            AuthTokenDao authTokenDao = new AuthTokenDao(connection);

            // get the personID using the authToken
            String personID;
            try {
                personID = authTokenDao.getPersonIDFromToken(request.getToken());
            } catch (DataAccessException e) {
                e.printStackTrace();
                database.closeConnection(false);
                return new PersonResult("Error: Bad authtoken", false , null);
            }

            // get all the associates of this personID
            List<Person> associates = personDao.getAllKnownAssociates(authTokenDao.getUsernameFromAuthToken(request.getToken()));

            // close the connection
            database.closeConnection(true);

            // write all the associates to the result
            return new PersonResult(null, true, associates);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new PersonResult("Error: Failed to execute PersonService", false, null);
    }
}
