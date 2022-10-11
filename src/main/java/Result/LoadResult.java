package Result;

/**
 * An object containing the result of the load attempt
 */
public class LoadResult {
    private String msg;
    private boolean success;

    /**
     * Constructor
     * @param msg the message from the server
     * @param success if the load attempted succeeded or failed
     */
    public LoadResult(String msg, boolean success) {
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
