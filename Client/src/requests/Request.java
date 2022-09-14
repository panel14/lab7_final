package requests;

import utils.User;

import java.io.Serializable;

/**
 * serializable class to for client requests to server
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 17L;
    /**
     * command type
     */
    private CommandType type;
    /**
     * command arguments
     */
    private String[] commandArguments;

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private RequestType requestType;
    private User user;

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String[] getCommandInfo() {
        return commandArguments;
    }

    public void setCommandInfo(String[] commandArguments) {
        this.commandArguments = commandArguments;
    }

    /**
     * constructor
     * @param type
     * @param commandArguments
     */
    public Request(CommandType type, String[] commandArguments, RequestType requestType, User user) {
        this.type = type;
        this.commandArguments = commandArguments;
        this.requestType = requestType;
        this.user = user;
    }

    public Request() {}
}
