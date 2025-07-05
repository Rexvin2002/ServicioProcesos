
package models;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private String username;
    private String message;

    public ServerMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}

