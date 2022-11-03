package DataAccess;

import Model.Person;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person person;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        person = new Person("1","bob","matt","pear","m","123","321","bob");
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

/*    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(person);
        Person compareTest = pDao.getPersonByID(person.getPersonID());
        Assertions.assertNotNull(compareTest);
        assertEquals(person, compareTest);
    }*/

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(person);
        Assertions.assertThrows(DataAccessException.class, () -> pDao.insert(person));
    }

/*    @Test
    public void retrievePass() throws DataAccessException {
        pDao.insert(person);
        Person retrieveTest = pDao.getPersonByID(person.getPersonID());
        Assertions.assertNotNull(retrieveTest);
        assertEquals(person, retrieveTest);
    }*/

/*    @Test
    public void retrieveFail() throws DataAccessException {
        pDao.insert(person);
        Person retrieveTest = pDao.getPersonByID("not a real id");
        assertNotEquals(person, retrieveTest);
    }*/

    @Test
    public void clearPass() throws DataAccessException {
        pDao.clear();
        List<Person> people = pDao.getAllPeople();
        assertEquals(people.size(), 0);
    }

    @Test
    public void deletePass() throws DataAccessException {
        pDao.clear();
        pDao.insert(person);
        Person b = new Person("yolo", "jake", "billy", "lovejoy", "f", "124", "421", "haha");
        pDao.insert(b);
        List<Person> people = pDao.getAllPeople();
        pDao.delete(b.getPersonID());
        people = pDao.getAllPeople();
        for (Person p: people) {
            assertNotEquals(p.getPersonID(), b.getPersonID());
        }
    }

    @Test
    public void deleteFail() throws DataAccessException {
        pDao.clear();
        pDao.insert(person);
        Person b = new Person("yolo", "jake", "billy", "lovejoy", "f", "124", "421", "haha");
        pDao.insert(b);
        List<Person> people = pDao.getAllPeople();
        int sizeBeforeDelete = people.size();
        pDao.delete("not an id");
        people.clear();
        people = pDao.getAllPeople();
        assertEquals(sizeBeforeDelete, people.size());
    }
}
