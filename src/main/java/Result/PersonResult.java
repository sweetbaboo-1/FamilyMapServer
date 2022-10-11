package Result;

import Model.Person;

/**
 * An object containing the result of the person request attempt
 */
public class PersonResult {
    private Person person;
    private boolean success;

    /**
     * Constructor
     * @param person the person returned from the database
     * @param success if the person request succeeded or failed
     */
    public PersonResult(Person person, boolean success) {
        this.person = person;
        this.success = success;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isSuccess() {
        return success;
    }
}
