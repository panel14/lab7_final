package utils;

import java.io.Serializable;

/**
 * class for store user data - login and password
 */
public class User implements Serializable {
    private static final long serialVersionUID = 37L;

    public String login;
    public String password;

    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
