package models;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.Serializable;

public class ServerMessage implements Serializable {

    private final String username;
    private final String message;

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public ServerMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

}
