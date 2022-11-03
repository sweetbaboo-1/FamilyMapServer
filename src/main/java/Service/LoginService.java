package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Request.LoginRequest;
import Result.LoginResult;

/**
 * A service that attempts to log a user into the database
 */
public class LoginService {
    /**
     * Attempts to log in to the server
     * @param request the LoginRequest object
     * @return a LoginResult Object containing the result of the attempt
     */
    private LoginRequest request;

    public LoginService(LoginRequest request) {
        this.request = request;
    }

    public LoginResult login() {
        LoginResult loginResult;
        Database database = new Database();
        try {
           // login
           UserDao userDao = new UserDao(database.getConnection());
           boolean exists = userDao.validate(request.username, request.password);

           if (exists) {
               AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
               AuthToken token = new AuthToken(request.username);
               authTokenDao.insert(token);
               loginResult = new LoginResult(token.getAuthtoken(), request.username, authTokenDao.getPersonIDFromToken(token.getAuthtoken()), null, true );
           } else {
               loginResult = new LoginResult(null, null, null, "Error: Invalid username and password combination", false);
           }
           database.closeConnection(true);
           return loginResult;
        } catch (DataAccessException e) {
            e.printStackTrace();
            database.closeConnection(false);
            return new LoginResult(null, null, null, "Error: server error occurred while attempting to log in", false);
        }
    }
}
