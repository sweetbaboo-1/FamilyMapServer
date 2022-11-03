package DataAccess;
import Model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User user;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        user = new User("SweetBaboo", "Peart4", "matthewpeart@hotmail.com","Matthew", "Peart", "m", "3141");

        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(user);
        User compareTest = uDao.getUserByID(user.getPersonID());
        Assertions.assertNotNull(compareTest);
        assertEquals(user, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(user);
        Assertions.assertThrows(DataAccessException.class, () -> uDao.insert(user));
    }

    @Test
    public void retrievePass() throws DataAccessException {
        uDao.insert(user);
        User retrieveTest = uDao.getUserByID(user.getPersonID());
        Assertions.assertNotNull(retrieveTest);
        assertEquals(user, retrieveTest);
    }

    @Test
    public void retrieveFail() throws DataAccessException {
        uDao.insert(user);
        User retrieveTest = uDao.getUserByID("not a real id");
        assertNotEquals(user, retrieveTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        uDao.clear();
        List<User> users = uDao.getAllUsers();
        assertEquals(users.size(), 0);
    }

    @Test
    public void deletePass() throws DataAccessException {
        uDao.clear();
        uDao.insert(user);
        List<User> users = new ArrayList<>();
        users = uDao.getAllUsers();
        assertEquals(1, users.size());
        uDao.deleteUser(user);
        users.clear();
        users = uDao.getAllUsers();
        assertEquals(0, users.size());
    }

    @Test
    public void deleteFail() throws DataAccessException {
        User user1 = new User("asdf", "sdfsd","asss","afdsff","fsdf","werc","qqq");
        uDao.clear();
        uDao.insert(user);
        List<User> users = new ArrayList<>();
        users = uDao.getAllUsers();
        assertEquals(1, users.size());
        uDao.deleteUser(user1);
        users.clear();
        users = uDao.getAllUsers();
        assertNotEquals(0, users.size());
    }
}
