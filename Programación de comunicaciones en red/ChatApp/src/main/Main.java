package main;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import controllers.Controller;
import controllers.PanelsController;
import controllers.UserController;
import java.awt.GraphicsEnvironment;
import models.Server;
import ui.ChatApp;
import ui.ConsoleMode;

public class Main {

    private static final Controller CTRLR = new Controller();
    private static UserController uc;
    private static PanelsController pc;

    private static String serverIP;
    private static int port;
    private static Server server = new Server(Main.serverIP, Main.port);

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public Main(ChatApp app) {

        Main.uc = new UserController(app);
        Main.pc = new PanelsController(app);

    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public static void main(String args[]) {

        ConsoleMode.runCLI(); // Ejecutar en modo CLI

    }

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public static Controller getCTRLR() {
        return CTRLR;
    }

    public static UserController getUc() {
        return uc;
    }

    public static void setUc(UserController uc) {
        Main.uc = uc;
    }

    public static PanelsController getPc() {
        return pc;
    }

    public static void setPc(PanelsController pc) {
        Main.pc = pc;
    }

    public static String getServerIP() {
        return serverIP;
    }

    public static void setServerIP(String serverIP) {
        Main.serverIP = serverIP;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Main.port = port;
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        Main.server = server;
    }

}
