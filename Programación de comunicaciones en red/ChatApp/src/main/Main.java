package main;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import controllers.Controller;
import controllers.PanelsController;
import controllers.UserController;
import java.awt.GraphicsEnvironment;
import models.Client;
import models.Server;
import ui.ChatApp;
import ui.ConsoleMode;

public class Main {

    private static String avatarPathSelected;
    private static final String APP_ICON_URL = "src/img/chatAppIcon.png";
    private static final String USER_ICON_URL = "src/img/userIcon.png";
    private static final String FILE_ICON_PATH = System.getProperty("user.dir") + Controller.getSeparator() + "img" + Controller.getSeparator() + "userIcon.png";
    private static final String CLIENTS_FOLDER_PATH = System.getProperty("user.dir") + Controller.getSeparator() + "clients";
    private static final String CLIENT_JSON_NAME = "client_data.json";

    private static String serverIP;
    private static int port;
    private static Server server = new Server(Main.serverIP, Main.port);
    private static Client currentUser;

    private static Controller ctrlr;
    private static PanelsController pc;
    private static UserController uc;

    public Main(ChatApp app) {
        ctrlr = new Controller();
        pc = app.getPc();
        uc = app.getUc();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        if (GraphicsEnvironment.isHeadless()) {
            ConsoleMode.runCLI(); // Ejecutar en modo CLI
        } else {
            // Ejecutar en modo GUI
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ChatApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            java.awt.EventQueue.invokeLater(() -> {
                new ChatApp().setVisible(true);
            });
        }
    }

    public static String getAvatarPathSelected() {
        return avatarPathSelected;
    }

    public static void setAvatarPathSelected(String avatarPathSelected) {
        Main.avatarPathSelected = avatarPathSelected;
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

    public static Client getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Client currentUser) {
        Main.currentUser = currentUser;
    }

    public static Controller getCtrlr() {
        return ctrlr;
    }

    public static void setCtrlr(Controller ctrlr) {
        Main.ctrlr = ctrlr;
    }

    public static PanelsController getPc() {
        return pc;
    }

    public static void setPc(PanelsController pc) {
        Main.pc = pc;
    }

    public static UserController getUc() {
        return uc;
    }

    public static void setUc(UserController uc) {
        Main.uc = uc;
    }

    public static String getAPP_ICON_URL() {
        return APP_ICON_URL;
    }

    public static String getUSER_ICON_URL() {
        return USER_ICON_URL;
    }

    public static String getFILE_ICON_PATH() {
        return FILE_ICON_PATH;
    }

    public static String getCLIENTS_FOLDER_PATH() {
        return CLIENTS_FOLDER_PATH;
    }

    public static String getCLIENT_JSON_NAME() {
        return CLIENT_JSON_NAME;
    }

}
