package Result;

import Model.Person;

public class PersonIDResult {
    private Person person;
    private String msg;
    private boolean success;

    public PersonIDResult(Person person, String msg, boolean success) {
        this.person = person;
        this.msg = msg;
        this.success = success;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
