package Result;

/**
 * An object containing information about the success of a ClearRequest
 */
public class ClearResult {
    private String msg;
    private boolean success;

    /**
     * Constructor
     * @param msg the message from the server
     * @param success if the clear attempt succeeded or failed
     */
    public ClearResult(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
