package models;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.Serializable;

public class ServerMessage implements Serializable {

    private final String USERNAME;
    private final String MESSAGE;

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public ServerMessage(String username, String message) {
        this.USERNAME = username;
        this.MESSAGE = message;
    }

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public String getUSERNAME() {
        return USERNAME;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

}
