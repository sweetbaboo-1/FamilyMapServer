package Result;

/**
 * An object containing the result of the fill attempt
 */
public class FillResult {
    private String msg;
    private boolean success;

    /**
     * Constructor
     * @param msg the message from the server
     * @param success if the fill request succeeded or failed
     */
    public FillResult(String msg, boolean success) {
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
