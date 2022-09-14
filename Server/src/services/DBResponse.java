package services;

/**
 * service class for authentication requests
 */
public class DBResponse {
    public boolean isDone;
    public String comment;

    public DBResponse(boolean isDone, String comment) {
        this.comment = comment;
        this.isDone = isDone;
    }
}
