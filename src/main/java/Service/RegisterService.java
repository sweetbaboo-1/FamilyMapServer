package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A service that attempts to register a user to the database
 */
public class RegisterService {
    /**
     * Attempts to register
     * @param r the registration request
     * @return a RegisterResult object containing the result of the registration attempt
     */
    public RegisterResult register(RegisterRequest r) {
        /*
        1. validate username
        2. make user - generate the two uuid's
        3. insert into the database - Database db = new Database
        4. if works set the register result to success
        5. else result = false - populate the msg
        6. return the result
         */
        Database database = new Database();
        try {
            System.out.println("Attempting to register a new user");
            // create the database
            Connection connection = database.getConnection();

            // create the userDao to validate the username
            UserDao userDao = new UserDao(connection);

            // if the username is available
            if (userDao.validateUsername(r.getUsername())) {
                // proceed with registration

                // create the new token
                AuthToken token = new AuthToken(r.getUsername());
                AuthTokenDao authTokenDao = new AuthTokenDao(connection);

                // create the new personID
                String personID = UUID.randomUUID().toString();

                // insert the user into the database
                userDao.insert(r.getUsername(), r.getPassword(), personID, r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender());

                // insert the token into the database
                authTokenDao.insert(token);
                database.closeConnection(true);
                return new RegisterResult(token.getAuthtoken(), r.getUsername(), personID, null, true);
            } else { // username wasn't available
                database.closeConnection(false);
                return new RegisterResult(null, null, null, "Error: That username is not available", false);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new RegisterResult(null,null,null,"Server Error", false);
    }
}
