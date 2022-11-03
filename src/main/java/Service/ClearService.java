package Service;

import DataAccess.*;
import Model.User;
import Result.ClearResult;

import java.sql.SQLException;
import java.util.List;

/**
 * A service that tries to clear the database
 */
public class ClearService {
    /**
     * Attempts to clear the database
     * @return A ClearResult object containing the result of the attempt
     */
    public ClearResult clear() throws DataAccessException {
        try {
            System.out.println("Attempting to clear the database");
            ClearResult result;
            Database database = new Database();
            EventDao eventDao = new EventDao(database.getConnection());
            PersonDao personDao = new PersonDao(database.getConnection());
            UserDao userDao = new UserDao(database.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());

            personDao.clear();
            eventDao.clear();
            userDao.clear();
            authTokenDao.clear();


            if (userDao.getAllUsers().size() == 0 && authTokenDao.getAllTokens().size() == 0 && personDao.getAllPeople().size() == 0 && userDao.getAllUsers().size() == 0) {
                result = new ClearResult("Clear succeeded", true);
                database.getConnection().commit();
            } else {
                result = new ClearResult("Error: was not able to clear all data from the database", false);
                database.getConnection().rollback();
            }
            database.getConnection().close();
            return result;
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
        return new ClearResult("Error: server error", false);
    }
}
