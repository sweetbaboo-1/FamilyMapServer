package Result;

import Model.Person;

/**
 * An object containing the result of the family request attempt
 */
public class FamilyResult {
    private Person[] people;
    private boolean success;

    /**
     * Constructor
     * @param people the family of people returned from the database
     * @param success if the family request succeeded or failed
     */
    public FamilyResult(Person[] people, boolean success) {
        this.people = people;
        this.success = success;
    }
}
