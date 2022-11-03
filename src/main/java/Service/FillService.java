package Service;

import DataAccess.*;
import Model.*;
import Request.FillRequest;
import Result.FillResult;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * A service that attempts to fill the database
 */
public class FillService {

    private String username;
    private Database database;
    private Connection connection;
    private int peopleAdded;
    private int eventsAdded;
    FillRequest fillRequest;

    public FillService(FillRequest request) throws DataAccessException {
        fillRequest = request;
        try {
            database = new Database();
            connection = database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataAccessException("Error connecting to database");
        }
    }

    public FillResult fill() {
        peopleAdded = 0;
        eventsAdded = 0;
        FillResult fillResult;
        try {
            Database database = new Database();
            username = fillRequest.getUsername();
            UserDao userDao = new UserDao(database.getConnection());
            if (userDao.validateUsername(username)) {
                database.getConnection().close();
                return new FillResult("Error: user does not exist", false);
            }
            String gender = userDao.getUserByID(userDao.getUserID(username)).getGender();
            generatePerson(gender, fillRequest.getGenerations());
             fillResult = new FillResult("Successfully added " + peopleAdded + " persons and " + eventsAdded + " events to the database", true);
        } catch (Exception e) {
            fillResult = new FillResult("Error: was unable to fill data for " + username, false);
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fillResult;
    }

    private Person generate(String gender, int generations) {

        return null;
    }

    private Person generatePerson(String gender, int generations) throws FileNotFoundException, DataAccessException {
        // create null parents
        Person mother = null;
        Person father = null;

        // if we need to generate parents
        if (generations > 0) {
            mother = generatePerson("f", generations - 1);
            father = generatePerson("m", generations - 1);

            // make sure the parents are married to each other
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            // create the marriage event
            // people get married after they are 13 years old, and before their child is born
            EventDao eventDao = new EventDao(connection);

            // get the birth event of the mother and father
            Event motherBirth = eventDao.getEventByType(mother.getPersonID(), "birth");
            Event fatherBirth = eventDao.getEventByType(father.getPersonID(), "birth");

            // choose the youngest parent (the largest birth year)
            int parentalBirth = Math.max(motherBirth.getYear(), fatherBirth.getYear());

            // get a list of their children (only 1 person but whatever)
            List<Person> people = new PersonDao(connection).getAllChildren(mother.getPersonID());

            // get the birth year of the oldest child
            int max = Integer.MIN_VALUE;
            for (Person p: people) {
                max = Math.max(max, eventDao.getEventByType(p.getPersonID(), "birth").getYear());
            }

            // the marriageYear must be between the youngest spouses 13th birthday, and the birth of their first child
            Random random = new Random();
            System.out.println(parentalBirth);
            System.out.println(max);
            int marriageYear = random.nextInt(parentalBirth + 13, max);

            // get a random location for the marriage (lucky)
            Location location = getRandomLocation();

            // insert the marriage event into the database for each parent
            eventDao.insert(new Event(UUID.randomUUID().toString(), username, mother.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "marriage", marriageYear));
            eventDao.insert(new Event(UUID.randomUUID().toString(), username, father.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "marriage", marriageYear));
            eventsAdded += 2;
        }

        // create the base person
        Person person;
        String personID;
        String associatedUsername;
        String fatherID = null;
        String motherID = null;
        personID = UUID.randomUUID().toString();
        associatedUsername = username;

        // if this isn't the last generation
        if (father != null && mother != null) {
            fatherID = father.getPersonID();
            motherID = mother.getPersonID();
        }

        // create a new person
        person = new Person(personID, associatedUsername, getRandomName(true), getRandomName(false), gender, fatherID, motherID, null);

        // generate birth/death events for this person
        Event[] events = generateBirthAndDeathEvents(person);

        // insert the events into the database
        EventDao eventDao = new EventDao(connection);
        for (Event event: events) {
            eventDao.insert(event);
            eventsAdded++;
        }

        // insert the person into the database
        PersonDao personDao = new PersonDao(connection);
        personDao.insert(person);
        peopleAdded++;
        return person;
    }

    private Event[] generateBirthAndDeathEvents(Person person) throws FileNotFoundException, DataAccessException {
        // gather relevant data
        EventDao eventDao = new EventDao(connection);

        // for generating random numbers
        Random random = new Random();

        int parentalMarriageYear;
        // TODO: SOMETHING IS WRONG HERE
        if (eventDao.getEventByType(person.getMotherID(), "marriage") != null) {
            parentalMarriageYear = eventDao.getEventByType(person.getMotherID(), "marriage").getYear();
        } else {
            parentalMarriageYear = random.nextInt(13, 50);
        }

        int parentalDeathYear;
        if (eventDao.getEventByType(person.getMotherID(), "death") != null) {
            parentalDeathYear = eventDao.getEventByType(person.getMotherID(), "death").getYear();
        } else {
            parentalDeathYear = random.nextInt(50,120);
        }
        // TODO: END TODO


        // generate a random valid birth year
        // birth years must be after their parents got married, and before their parents died
        int birthYear = random.nextInt(parentalMarriageYear, parentalDeathYear);
        Location location = getRandomLocation();
        Event birth = new Event(UUID.randomUUID().toString(), username, person.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "birth", birthYear);

        // generate a random death year
        // death years are after their youngest child is born, and before 120

        // get the youngest child's birth year
        PersonDao personDao = new PersonDao(connection);
        List<Person> people = personDao.getAllChildren(person.getPersonID());
        int youngestChildBirthYear = Integer.MAX_VALUE;
        for (Person p: people) {
            youngestChildBirthYear = Math.min(youngestChildBirthYear, eventDao.getEventByType(p.getPersonID(), "birth").getYear()); // TODO: THIS ISN'T WORKING
        }
        location = getRandomLocation();
        if (youngestChildBirthYear == Integer.MAX_VALUE) {
            youngestChildBirthYear = 2022;
        }
        int deathYear = random.nextInt(youngestChildBirthYear, youngestChildBirthYear + 120);
        Event death = new Event(UUID.randomUUID().toString(), username, person.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "death", deathYear);
        return new Event[]{birth, death};
    }

    /**
     * Gets a random location
     * @param first true if a firstname is requested, false if a lastname is requested
     * @return a random Name object
     * @throws FileNotFoundException if the file wasn't found
     */
    private String getRandomName(boolean first) throws FileNotFoundException {
        Reader reader;
        if (first) {
            reader = new FileReader("json/fnames.json");
        } else {
            reader = new FileReader("json/snames.json");
        }
            Gson gson = new Gson();
            NameData nameData = gson.fromJson(reader, NameData.class);
            return nameData.getRandomName();
    }

    /**
     * Gets a random location from the hardcoded file
     * @return a random Location object
     * @throws FileNotFoundException if the file wasn't found
     */
    private Location getRandomLocation() throws FileNotFoundException {
        Reader reader = new FileReader("json/locations.json");
        Gson gson = new Gson();
        LocationData locationData = gson.fromJson(reader, LocationData.class);
        return locationData.getRandomLocation();
    }
}
