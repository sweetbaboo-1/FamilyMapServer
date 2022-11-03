package Result;

import Model.Person;

import java.util.List;

/**
 * An object containing the result of the person request attempt
 */
public class PersonResult {
    private String msg;
    private boolean success;
    private List<Person> data;

    /**
     * Constructor
     * @param msg the person returned from the database
     * @param success if the person request succeeded or failed
     */
    public PersonResult(String msg, boolean success, List<Person> data) {
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Person> getData() {
        return data;
    }
}
