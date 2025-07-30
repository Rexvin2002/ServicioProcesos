package ui;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import controllers.Controller;
import controllers.PanelsController;
import controllers.UserController;
import java.awt.CardLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.accessibility.AccessibleContext;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Main;
import models.Client;
import models.ServerMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatApp extends javax.swing.JFrame {

    private CardLayout cardLayout;
    private Controller ctrlr;
    private PanelsController pc;
    private UserController uc;
    private Main main;
    private String originalUsername;
    private String originalPassword;
    private String originalAvatar;

    /**
     * Creates new form ChatWindow
     */
    public ChatApp() {
        if (!GraphicsEnvironment.isHeadless()) {
            initComponents(); // Solo inicializar componentes gráficos si no estamos en modo headless
            setLocationRelativeTo(null);

            ctrlr = new Controller();
            pc = new PanelsController(this);
            uc = new UserController(this);
            main = new Main(this);

            System.out.println("\nAPP INICIADA\n");

            Image image = Toolkit.getDefaultToolkit().getImage(Main.getAPP_ICON_URL());
            setIconImage(image);
            ctrlr.escalarEstablecerImagenFromString(jLabelIconoApp, Main.getAPP_ICON_URL());
            System.out.println("Icono de App establecido: " + Main.getAPP_ICON_URL());

            Controller.applyHandCursorToAllButtons(rootPane);
            getRootPane().setDefaultButton(jButtonAcceder);
        }
    }

    public void cambiarLayout(String card) {
        setCardLayout((CardLayout) jPanelLayout.getLayout());
        getCardLayout().show(jPanelLayout, card);
    }

    /**
     * Existencia Cliente
     *
     * @param user
     * @return
     */
    public boolean existUser(String user) {
        File jsonFile = new File(Main.getCLIENTS_FOLDER_PATH() + Controller.getSeparator() + user + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());

        if (!jsonFile.exists()) {
            return false;
        }

        try {
            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            if (clientData.has("username") && clientData.getString("username").equalsIgnoreCase(user)) {
                Main.setCurrentUser(new Client(clientData.getString("username"),
                        clientData.getString("password").toCharArray(),
                        Main.getAvatarPathSelected()));
                return true;
            }
        } catch (IOException | JSONException e) {
            MensajeDialog.showMessageDialog(this, "Error al acceder a los datos del usuario: " + e.getMessage(), "Error");
        }

        return false;
    }

    /**
     * Establecer Perfil
     */
    private void setProfile() {
        Main.getCurrentUser().loadClientData();
        // Actualizar la interfaz de usuario
        Main.getPc().updateServerPanels();
        ctrlr.escalarEstablecerImagenFromString(this.getjLabelImagenPerfilCliente(), Main.getCurrentUser().getAvatar());
        this.getjTextFieldNombrePerfil().setText(Main.getCurrentUser().getUsername());
        this.getjPasswordFieldContraseñaPerfil().setText(Main.getCurrentUser().getPasswdAsString());
        this.getjPasswordFieldConfirmarContraseñaPerfil().setText(Main.getCurrentUser().getPasswdAsString());

    }

    public void saveClientsServers() {
        try {
            // Cargar los datos existentes del archivo JSON
            File jsonFile = new File(Main.getCurrentUser().getClientFolderPath() + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());
            if (!jsonFile.exists()) {
                MensajeDialog.showMessageDialog(this, "El archivo de datos del cliente no existe.", "Error");
                return;
            }

            // Leer el contenido actual del archivo JSON
            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            // Crear o actualizar la sección de servidores
            JSONObject serversJSON = clientData.optJSONObject("servers");
            if (serversJSON == null) {
                serversJSON = new JSONObject(); // Si no existe, crear el objeto
            }

            // Agregar los servidores al JSON
            for (Map.Entry<String, List<ServerMessage>> entry : Main.getCurrentUser().getServerList().entrySet()) {
                JSONArray serverArray = new JSONArray();
                for (ServerMessage srv : entry.getValue()) {
                    JSONObject messageJson = new JSONObject();
                    messageJson.put("username", srv.getUsername());
                    messageJson.put("servers", srv.getMessage());
                    serverArray.put(messageJson);
                }
                serversJSON.put(entry.getKey(), serverArray);
            }

            // Actualizar la sección "servers" en el JSON principal
            clientData.put("servers", serversJSON);

            // Guardar el JSON completo en el archivo client_data.json
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(clientData.toString(4));  // Escribir con indentación
            }

            System.out.println("Datos de los servidores guardados exitosamente en client_data.json");

        } catch (IOException e) {
            System.err.println("Error al guardar los datos de los servidores: " + e.getMessage());
            MensajeDialog.showMessageDialog(this, "Error al guardar los datos de los servidores: " + e.getMessage(), "Error");
        } catch (JSONException e) {
            System.err.println("Error inesperado: " + e.getMessage());
            MensajeDialog.showMessageDialog(this, "Error inesperado al guardar los datos de los servidores.", "Error");
        }
    }

    /**
     * Conectar Cliente a Servidor
     */
    public void connectUserToServer() {
        if (Main.getCurrentUser().isConnected()) {
            MensajeDialog.showMessageDialog(this, "Ya estás conectado al servidor.", "Advertencia");

            this.getjLabelServidorPuerto().setText("Servidor: " + Main.getServerIP() + " | Puerto: " + Main.getPort());

            cambiarLayout("cardChat");

            return;  // Salir del método si ya está conectado
        }

        Main.setServerIP(this.getjTextFieldServerIP().getText());  // Obtener IP desde el campo de texto
        String puertoText = this.getjTextFieldPuerto().getText(); // Obtener puerto como String

        if (Main.getServerIP().isEmpty() || puertoText.isEmpty()) {
            MensajeDialog.showMessageDialog(this, "Por favor, ingresa una IP y puerto válidos.", "Advertencia");
            return;
        }

        try {
            Main.setPort(Integer.parseInt(puertoText));  // Convertir el puerto a entero
        } catch (NumberFormatException e) {
            MensajeDialog.showMessageDialog(this, "Puerto inválido. Debe ser un número.", "Error");
            return;
        }

        Main.getCurrentUser().setPort(Main.getPort());
        Main.getCurrentUser().setServerIP(Main.getServerIP());

        try {
            Main.getCurrentUser().connect();  // Conectar al servidor

            if (Main.getCurrentUser().isConnected()) {
                MensajeDialog.showMessageDialog(this, "Conectado al servidor en " + Main.getServerIP() + ":" + Main.getPort(), "Información");
                this.getjLabelServidorPuerto().setText(Main.getCurrentUser().getUsername() + ": " + Main.getServerIP() + " | Puerto: " + Main.getPort());

                cambiarLayout("cardChat");

                // Agregar mensaje de conexión al chat
                String message = Main.getServerIP() + ":" + Main.getPort();
                String currentUserName = Main.getCurrentUser().getUsername();

                // Evitar mostrar el mensaje duplicado si el servidor lo reenvía
                if (!existServer(message)) {
                    Main.getPc().updateServersPanel(currentUserName + ": " + message);
                }
                Main.getCurrentUser().sendMessage(Main.getCurrentUser().getUsername() + ": Conectado a " + Main.getCurrentUser().getServerIP() + ":" + Main.getCurrentUser().getPort());

            }

        } catch (Exception e) {
            // Mostrar error detallado
            MensajeDialog.showMessageDialog(this, "Error al conectar: " + e.getMessage() + "\n" + "Por favor, verifica la IP y el puerto.", "Error");
        }

    }

    /**
     * Existencia Servidor
     *
     * @param serverAddress
     * @return
     */
    public boolean existServer(String serverAddress) {
        // Obtener los datos del archivo JSON
        File jsonFile = new File(Main.getCurrentUser().getClientFolderPath() + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());
        if (!jsonFile.exists()) {
            MensajeDialog.showMessageDialog(this, "El archivo de datos del cliente no existe.", "Error");
            return false;
        }

        try {
            // Leer el contenido actual del archivo JSON
            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            // Obtener la sección de servidores
            JSONObject serversJSON = clientData.optJSONObject("servers");
            if (serversJSON == null) {
                return false;  // No hay servidores registrados
            }

            // Comprobar cada servidor registrado
            for (String serverName : serversJSON.keySet()) {
                JSONArray serverArray = serversJSON.getJSONArray(serverName);

                // Buscar en cada servidor si el address coincide
                for (int i = 0; i < serverArray.length(); i++) {
                    JSONObject server = serverArray.getJSONObject(i);
                    String serverAddressInJson = server.getString("servers");

                    // Comparar la dirección y puerto del servidor
                    if (serverAddressInJson.equals(serverAddress)) {
                        return true; // El servidor ya existe
                    }
                }
            }

        } catch (IOException | JSONException e) {
            MensajeDialog.showMessageDialog(this, "Error al comprobar los servidores: " + e.getMessage(), "Error");
        }

        return false; // No se encontró el servidor
    }

    public JScrollPane getjScrollPaneChat() {
        return jScrollPaneChat;
    }

    public void setjScrollPaneChat(JScrollPane jScrollPaneChat) {
        this.jScrollPaneChat = jScrollPaneChat;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLayout = new javax.swing.JPanel();
        jPanelInicioSesion = new javax.swing.JPanel();
        jPanelImagen = new javax.swing.JPanel();
        jLabelIconoApp = new javax.swing.JLabel();
        jPanelNombre = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelNombreInicioSesion = new javax.swing.JLabel();
        jTextFieldNombreInicioSesion = new javax.swing.JTextField();
        jPanelContraseña = new javax.swing.JPanel();
        jLabelContraseñaInicioSesion = new javax.swing.JLabel();
        jPasswordFieldContraseñaInicioSesion = new javax.swing.JPasswordField();
        jPanelBotones = new javax.swing.JPanel();
        jButtonAcceder = new javax.swing.JButton();
        jButtonCrearCuenta = new javax.swing.JButton();
        jPanelCrearCuenta = new javax.swing.JPanel();
        jPanelTitulo = new javax.swing.JPanel();
        jLabelCrearCuenta = new javax.swing.JLabel();
        jPanelImagen1 = new javax.swing.JPanel();
        jLabelImagenCrearCliente = new javax.swing.JLabel();
        jPanelImagen2 = new javax.swing.JPanel();
        jButtonCrearAvatar = new javax.swing.JButton();
        jPanelNombre1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelCrearNombre = new javax.swing.JLabel();
        jTextFieldCrearNombre = new javax.swing.JTextField();
        jPanelContraseña1 = new javax.swing.JPanel();
        jLabelCrearContraseña = new javax.swing.JLabel();
        jPasswordFieldCrearContraseña = new javax.swing.JPasswordField();
        jPanelContraseña2 = new javax.swing.JPanel();
        jLabelConfirmarContraseña = new javax.swing.JLabel();
        jPasswordFieldConfirmarContraseña = new javax.swing.JPasswordField();
        jPanelBotones1 = new javax.swing.JPanel();
        jButtonVolverCrearCuentaToInicioSesion = new javax.swing.JButton();
        jButtonConfirmarCrearCuenta = new javax.swing.JButton();
        jPanelInicio = new javax.swing.JPanel();
        jPanelMenuSuperiorChats = new javax.swing.JPanel();
        jPanelMenuSuperiorChats2 = new javax.swing.JPanel();
        jTextFieldPuerto = new javax.swing.JTextField();
        jTextFieldServerIP = new javax.swing.JTextField();
        jPanelMenuSuperiorChats3 = new javax.swing.JPanel();
        jButtonConectarServidor = new javax.swing.JButton();
        jButtonConectarCliente = new javax.swing.JButton();
        jButtonEliminarServidor = new javax.swing.JButton();
        jPanelScrollServidores = new javax.swing.JPanel();
        jScrollPaneServidores = new javax.swing.JScrollPane();
        jPanelServidores = new javax.swing.JPanel();
        jPanelMenuInferiorChats = new javax.swing.JPanel();
        jButtonCerrarSesion = new javax.swing.JButton();
        jButtonVerPerfil = new javax.swing.JButton();
        jPanelChat = new javax.swing.JPanel();
        jPanelMenuSuperior1 = new javax.swing.JPanel();
        jLabelServidorPuerto = new javax.swing.JLabel();
        jPanelScrollChat = new javax.swing.JPanel();
        jScrollPaneChat = new javax.swing.JScrollPane();
        jPanelMensajes = new javax.swing.JPanel();
        jPanelMenuInferior1 = new javax.swing.JPanel();
        jButtonEnviarArchivo = new javax.swing.JButton();
        jTextFieldMensaje = new javax.swing.JTextField();
        jButtonEnviar = new javax.swing.JButton();
        jPanelMenuInferior2 = new javax.swing.JPanel();
        jButtonVolverChatToChats = new javax.swing.JButton();
        jPanelPerfil = new javax.swing.JPanel();
        jPanelTitulo1 = new javax.swing.JPanel();
        jLabelPerfil = new javax.swing.JLabel();
        jPanelImagen3 = new javax.swing.JPanel();
        jLabelImagenPerfilCliente = new javax.swing.JLabel();
        jPanelImagen4 = new javax.swing.JPanel();
        jButtonCambiarAvatar = new javax.swing.JButton();
        jPanelNombre2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabelNombrePerfil = new javax.swing.JLabel();
        jTextFieldNombrePerfil = new javax.swing.JTextField();
        jPanelContraseña3 = new javax.swing.JPanel();
        jLabelContraseñaPerfil = new javax.swing.JLabel();
        jPasswordFieldContraseñaPerfil = new javax.swing.JPasswordField();
        jPanelContraseña4 = new javax.swing.JPanel();
        jLabelConfirmarContraseñaPerfil = new javax.swing.JLabel();
        jPasswordFieldConfirmarContraseñaPerfil = new javax.swing.JPasswordField();
        jPanelBotones2 = new javax.swing.JPanel();
        jButtonVolverPerfilToChats = new javax.swing.JButton();
        jButtonEditarPerfil = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ChatApp");
        setBackground(new java.awt.Color(51, 51, 51));
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(700, 850));

        jPanelLayout.setBackground(new java.awt.Color(51, 51, 51));
        jPanelLayout.setMinimumSize(new java.awt.Dimension(700, 850));
        jPanelLayout.setPreferredSize(new java.awt.Dimension(700, 850));
        jPanelLayout.setLayout(new java.awt.CardLayout());

        jPanelInicioSesion.setBackground(new java.awt.Color(51, 51, 51));
        jPanelInicioSesion.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelInicioSesion.setMinimumSize(new java.awt.Dimension(700, 850));
        jPanelInicioSesion.setPreferredSize(new java.awt.Dimension(700, 850));
        jPanelInicioSesion.setLayout(new javax.swing.BoxLayout(jPanelInicioSesion, javax.swing.BoxLayout.Y_AXIS));

        jPanelImagen.setMaximumSize(new java.awt.Dimension(700, 350));
        jPanelImagen.setMinimumSize(new java.awt.Dimension(700, 350));
        jPanelImagen.setOpaque(false);
        jPanelImagen.setPreferredSize(new java.awt.Dimension(700, 350));
        jPanelImagen.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        jLabelIconoApp.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabelIconoApp.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabelIconoApp.setPreferredSize(new java.awt.Dimension(300, 300));
        jPanelImagen.add(jLabelIconoApp);

        jPanelInicioSesion.add(jPanelImagen);

        jPanelNombre.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelNombre.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelNombre.setOpaque(false);
        jPanelNombre.setPreferredSize(new java.awt.Dimension(700, 100));

        jPanel1.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelNombreInicioSesion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelNombreInicioSesion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreInicioSesion.setText("Nombre:");
        jLabelNombreInicioSesion.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelNombreInicioSesion.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelNombreInicioSesion.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanel1.add(jLabelNombreInicioSesion);

        jTextFieldNombreInicioSesion.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNombreInicioSesion.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldNombreInicioSesion.setText("Kevin");
        jTextFieldNombreInicioSesion.setMaximumSize(new java.awt.Dimension(410, 30));
        jTextFieldNombreInicioSesion.setMinimumSize(new java.awt.Dimension(410, 30));
        jTextFieldNombreInicioSesion.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanel1.add(jTextFieldNombreInicioSesion);

        jPanelNombre.add(jPanel1);

        jPanelInicioSesion.add(jPanelNombre);

        jPanelContraseña.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña.setOpaque(false);
        jPanelContraseña.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelContraseñaInicioSesion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelContraseñaInicioSesion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelContraseñaInicioSesion.setText("Contraseña:");
        jLabelContraseñaInicioSesion.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelContraseñaInicioSesion.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelContraseñaInicioSesion.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanelContraseña.add(jLabelContraseñaInicioSesion);

        jPasswordFieldContraseñaInicioSesion.setBackground(new java.awt.Color(204, 204, 204));
        jPasswordFieldContraseñaInicioSesion.setForeground(new java.awt.Color(0, 0, 0));
        jPasswordFieldContraseñaInicioSesion.setText("passwd");
        jPasswordFieldContraseñaInicioSesion.setMaximumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldContraseñaInicioSesion.setMinimumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldContraseñaInicioSesion.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanelContraseña.add(jPasswordFieldContraseñaInicioSesion);

        jPanelInicioSesion.add(jPanelContraseña);

        jPanelBotones.setMaximumSize(new java.awt.Dimension(700, 350));
        jPanelBotones.setMinimumSize(new java.awt.Dimension(700, 350));
        jPanelBotones.setOpaque(false);
        jPanelBotones.setPreferredSize(new java.awt.Dimension(700, 350));

        jButtonAcceder.setBackground(new java.awt.Color(0, 204, 51));
        jButtonAcceder.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonAcceder.setForeground(new java.awt.Color(0, 0, 0));
        jButtonAcceder.setText("Acceder");
        jButtonAcceder.setBorder(null);
        jButtonAcceder.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonAcceder.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonAcceder.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonAcceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAccederActionPerformed(evt);
            }
        });
        jButtonAcceder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonAccederKeyPressed(evt);
            }
        });
        jPanelBotones.add(jButtonAcceder);

        jButtonCrearCuenta.setBackground(new java.awt.Color(0, 204, 51));
        jButtonCrearCuenta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonCrearCuenta.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCrearCuenta.setText("Crear Cuenta");
        jButtonCrearCuenta.setBorder(null);
        jButtonCrearCuenta.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonCrearCuenta.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonCrearCuenta.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearCuentaActionPerformed(evt);
            }
        });
        jPanelBotones.add(jButtonCrearCuenta);

        jPanelInicioSesion.add(jPanelBotones);

        jPanelLayout.add(jPanelInicioSesion, "cardInicioSesion");

        jPanelCrearCuenta.setBackground(new java.awt.Color(51, 51, 51));
        jPanelCrearCuenta.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelCrearCuenta.setMinimumSize(new java.awt.Dimension(700, 850));
        jPanelCrearCuenta.setPreferredSize(new java.awt.Dimension(700, 850));
        jPanelCrearCuenta.setLayout(new javax.swing.BoxLayout(jPanelCrearCuenta, javax.swing.BoxLayout.Y_AXIS));

        jPanelTitulo.setMaximumSize(new java.awt.Dimension(700, 120));
        jPanelTitulo.setMinimumSize(new java.awt.Dimension(700, 120));
        jPanelTitulo.setOpaque(false);
        jPanelTitulo.setPreferredSize(new java.awt.Dimension(700, 120));
        jPanelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        jLabelCrearCuenta.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelCrearCuenta.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCrearCuenta.setText("Crear cuenta");
        jPanelTitulo.add(jLabelCrearCuenta);

        jPanelCrearCuenta.add(jPanelTitulo);

        jPanelImagen1.setMaximumSize(new java.awt.Dimension(700, 200));
        jPanelImagen1.setMinimumSize(new java.awt.Dimension(700, 200));
        jPanelImagen1.setOpaque(false);
        jPanelImagen1.setPreferredSize(new java.awt.Dimension(700, 200));
        jPanelImagen1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jLabelImagenCrearCliente.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelImagenCrearCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabelImagenCrearCliente.setMaximumSize(new java.awt.Dimension(200, 200));
        jLabelImagenCrearCliente.setMinimumSize(new java.awt.Dimension(200, 200));
        jLabelImagenCrearCliente.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanelImagen1.add(jLabelImagenCrearCliente);

        jPanelCrearCuenta.add(jPanelImagen1);

        jPanelImagen2.setMaximumSize(new java.awt.Dimension(700, 70));
        jPanelImagen2.setMinimumSize(new java.awt.Dimension(700, 70));
        jPanelImagen2.setOpaque(false);
        jPanelImagen2.setPreferredSize(new java.awt.Dimension(700, 70));

        jButtonCrearAvatar.setBackground(new java.awt.Color(0, 204, 51));
        jButtonCrearAvatar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonCrearAvatar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCrearAvatar.setText("Avatar");
        jButtonCrearAvatar.setBorder(null);
        jButtonCrearAvatar.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonCrearAvatar.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonCrearAvatar.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonCrearAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearAvatarActionPerformed(evt);
            }
        });
        jPanelImagen2.add(jButtonCrearAvatar);

        jPanelCrearCuenta.add(jPanelImagen2);

        jPanelNombre1.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelNombre1.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelNombre1.setOpaque(false);
        jPanelNombre1.setPreferredSize(new java.awt.Dimension(700, 100));

        jPanel2.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanel2.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelCrearNombre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelCrearNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCrearNombre.setText("Nombre:");
        jLabelCrearNombre.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelCrearNombre.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelCrearNombre.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanel2.add(jLabelCrearNombre);

        jTextFieldCrearNombre.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldCrearNombre.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldCrearNombre.setText("kevin");
        jTextFieldCrearNombre.setMaximumSize(new java.awt.Dimension(410, 30));
        jTextFieldCrearNombre.setMinimumSize(new java.awt.Dimension(410, 30));
        jTextFieldCrearNombre.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanel2.add(jTextFieldCrearNombre);

        jPanelNombre1.add(jPanel2);

        jPanelCrearCuenta.add(jPanelNombre1);

        jPanelContraseña1.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña1.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña1.setOpaque(false);
        jPanelContraseña1.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelCrearContraseña.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelCrearContraseña.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCrearContraseña.setText("Contraseña:");
        jLabelCrearContraseña.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelCrearContraseña.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelCrearContraseña.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanelContraseña1.add(jLabelCrearContraseña);

        jPasswordFieldCrearContraseña.setBackground(new java.awt.Color(204, 204, 204));
        jPasswordFieldCrearContraseña.setForeground(new java.awt.Color(0, 0, 0));
        jPasswordFieldCrearContraseña.setText("passwd");
        jPasswordFieldCrearContraseña.setMaximumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldCrearContraseña.setMinimumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldCrearContraseña.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanelContraseña1.add(jPasswordFieldCrearContraseña);

        jPanelCrearCuenta.add(jPanelContraseña1);

        jPanelContraseña2.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña2.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña2.setOpaque(false);
        jPanelContraseña2.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelConfirmarContraseña.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelConfirmarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        jLabelConfirmarContraseña.setText("Confirmar contraseña:");
        jLabelConfirmarContraseña.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelConfirmarContraseña.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelConfirmarContraseña.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanelContraseña2.add(jLabelConfirmarContraseña);

        jPasswordFieldConfirmarContraseña.setBackground(new java.awt.Color(204, 204, 204));
        jPasswordFieldConfirmarContraseña.setForeground(new java.awt.Color(0, 0, 0));
        jPasswordFieldConfirmarContraseña.setText("passwd");
        jPasswordFieldConfirmarContraseña.setMaximumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldConfirmarContraseña.setMinimumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldConfirmarContraseña.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanelContraseña2.add(jPasswordFieldConfirmarContraseña);

        jPanelCrearCuenta.add(jPanelContraseña2);

        jPanelBotones1.setMaximumSize(new java.awt.Dimension(700, 350));
        jPanelBotones1.setMinimumSize(new java.awt.Dimension(700, 350));
        jPanelBotones1.setOpaque(false);
        jPanelBotones1.setPreferredSize(new java.awt.Dimension(700, 350));

        jButtonVolverCrearCuentaToInicioSesion.setBackground(new java.awt.Color(0, 204, 51));
        jButtonVolverCrearCuentaToInicioSesion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonVolverCrearCuentaToInicioSesion.setForeground(new java.awt.Color(0, 0, 0));
        jButtonVolverCrearCuentaToInicioSesion.setText("Volver");
        jButtonVolverCrearCuentaToInicioSesion.setBorder(null);
        jButtonVolverCrearCuentaToInicioSesion.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonVolverCrearCuentaToInicioSesion.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonVolverCrearCuentaToInicioSesion.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonVolverCrearCuentaToInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverCrearCuentaToInicioSesionActionPerformed(evt);
            }
        });
        jPanelBotones1.add(jButtonVolverCrearCuentaToInicioSesion);

        jButtonConfirmarCrearCuenta.setBackground(new java.awt.Color(0, 204, 51));
        jButtonConfirmarCrearCuenta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonConfirmarCrearCuenta.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConfirmarCrearCuenta.setText("Confirmar");
        jButtonConfirmarCrearCuenta.setBorder(null);
        jButtonConfirmarCrearCuenta.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonConfirmarCrearCuenta.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonConfirmarCrearCuenta.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonConfirmarCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarCrearCuentaActionPerformed(evt);
            }
        });
        jPanelBotones1.add(jButtonConfirmarCrearCuenta);

        jPanelCrearCuenta.add(jPanelBotones1);

        jPanelLayout.add(jPanelCrearCuenta, "cardCrearCuenta");

        jPanelInicio.setBackground(new java.awt.Color(51, 51, 51));
        jPanelInicio.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelInicio.setMinimumSize(new java.awt.Dimension(700, 850));
        jPanelInicio.setPreferredSize(new java.awt.Dimension(700, 850));
        jPanelInicio.setLayout(new javax.swing.BoxLayout(jPanelInicio, javax.swing.BoxLayout.Y_AXIS));

        jPanelMenuSuperiorChats.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuSuperiorChats.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelMenuSuperiorChats.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelMenuSuperiorChats.setPreferredSize(new java.awt.Dimension(700, 100));
        jPanelMenuSuperiorChats.setLayout(new javax.swing.BoxLayout(jPanelMenuSuperiorChats, javax.swing.BoxLayout.Y_AXIS));

        jPanelMenuSuperiorChats2.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuSuperiorChats2.setMaximumSize(new java.awt.Dimension(700, 50));
        jPanelMenuSuperiorChats2.setMinimumSize(new java.awt.Dimension(700, 50));
        jPanelMenuSuperiorChats2.setPreferredSize(new java.awt.Dimension(700, 50));
        jPanelMenuSuperiorChats2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));

        jTextFieldPuerto.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldPuerto.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldPuerto.setText("12345");
        jTextFieldPuerto.setMaximumSize(new java.awt.Dimension(205, 30));
        jTextFieldPuerto.setMinimumSize(new java.awt.Dimension(205, 30));
        jTextFieldPuerto.setPreferredSize(new java.awt.Dimension(205, 30));
        jPanelMenuSuperiorChats2.add(jTextFieldPuerto);

        jTextFieldServerIP.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldServerIP.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldServerIP.setText("192.168.1.129");
        jTextFieldServerIP.setMaximumSize(new java.awt.Dimension(205, 30));
        jTextFieldServerIP.setMinimumSize(new java.awt.Dimension(205, 30));
        jTextFieldServerIP.setPreferredSize(new java.awt.Dimension(205, 30));
        jPanelMenuSuperiorChats2.add(jTextFieldServerIP);

        jPanelMenuSuperiorChats.add(jPanelMenuSuperiorChats2);

        jPanelMenuSuperiorChats3.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuSuperiorChats3.setMaximumSize(new java.awt.Dimension(700, 50));
        jPanelMenuSuperiorChats3.setMinimumSize(new java.awt.Dimension(700, 50));
        jPanelMenuSuperiorChats3.setPreferredSize(new java.awt.Dimension(700, 50));
        jPanelMenuSuperiorChats3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

        jButtonConectarServidor.setBackground(new java.awt.Color(0, 204, 51));
        jButtonConectarServidor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonConectarServidor.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConectarServidor.setText("Conectar al Servidor");
        jButtonConectarServidor.setBorder(null);
        jButtonConectarServidor.setMaximumSize(new java.awt.Dimension(130, 30));
        jButtonConectarServidor.setMinimumSize(new java.awt.Dimension(130, 30));
        jButtonConectarServidor.setPreferredSize(new java.awt.Dimension(130, 30));
        jButtonConectarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConectarServidorActionPerformed(evt);
            }
        });
        jPanelMenuSuperiorChats3.add(jButtonConectarServidor);

        jButtonConectarCliente.setBackground(new java.awt.Color(0, 204, 51));
        jButtonConectarCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonConectarCliente.setForeground(new java.awt.Color(0, 0, 0));
        jButtonConectarCliente.setText("Conectar Cliente");
        jButtonConectarCliente.setBorder(null);
        jButtonConectarCliente.setMaximumSize(new java.awt.Dimension(130, 30));
        jButtonConectarCliente.setMinimumSize(new java.awt.Dimension(130, 30));
        jButtonConectarCliente.setPreferredSize(new java.awt.Dimension(130, 30));
        jButtonConectarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConectarClienteActionPerformed(evt);
            }
        });
        jPanelMenuSuperiorChats3.add(jButtonConectarCliente);

        jButtonEliminarServidor.setBackground(new java.awt.Color(0, 204, 51));
        jButtonEliminarServidor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonEliminarServidor.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEliminarServidor.setText("Eliminar servidor");
        jButtonEliminarServidor.setBorder(null);
        jButtonEliminarServidor.setMaximumSize(new java.awt.Dimension(130, 30));
        jButtonEliminarServidor.setMinimumSize(new java.awt.Dimension(130, 30));
        jButtonEliminarServidor.setPreferredSize(new java.awt.Dimension(130, 30));
        jButtonEliminarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarServidorActionPerformed(evt);
            }
        });
        jPanelMenuSuperiorChats3.add(jButtonEliminarServidor);

        jPanelMenuSuperiorChats.add(jPanelMenuSuperiorChats3);

        jPanelInicio.add(jPanelMenuSuperiorChats);

        jPanelScrollServidores.setMinimumSize(new java.awt.Dimension(600, 200));
        jPanelScrollServidores.setPreferredSize(new java.awt.Dimension(600, 200));

        jScrollPaneServidores.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPaneServidores.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneServidores.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneServidores.setMinimumSize(new java.awt.Dimension(600, 200));
        jScrollPaneServidores.setPreferredSize(new java.awt.Dimension(600, 200));

        jPanelServidores.setBackground(new java.awt.Color(204, 204, 204));
        jPanelServidores.setForeground(new java.awt.Color(0, 0, 0));
        jPanelServidores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelServidores.setMinimumSize(new java.awt.Dimension(600, 200));
        jPanelServidores.setPreferredSize(new java.awt.Dimension(600, 200));
        jPanelServidores.setLayout(new javax.swing.BoxLayout(jPanelServidores, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneServidores.setViewportView(jPanelServidores);

        javax.swing.GroupLayout jPanelScrollServidoresLayout = new javax.swing.GroupLayout(jPanelScrollServidores);
        jPanelScrollServidores.setLayout(jPanelScrollServidoresLayout);
        jPanelScrollServidoresLayout.setHorizontalGroup(
            jPanelScrollServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScrollServidoresLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPaneServidores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanelScrollServidoresLayout.setVerticalGroup(
            jPanelScrollServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScrollServidoresLayout.createSequentialGroup()
                .addComponent(jScrollPaneServidores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanelInicio.add(jPanelScrollServidores);

        jPanelMenuInferiorChats.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuInferiorChats.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelMenuInferiorChats.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelMenuInferiorChats.setPreferredSize(new java.awt.Dimension(700, 100));
        jPanelMenuInferiorChats.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jButtonCerrarSesion.setBackground(new java.awt.Color(0, 204, 51));
        jButtonCerrarSesion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonCerrarSesion.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCerrarSesion.setText("Cerrar sesión");
        jButtonCerrarSesion.setBorder(null);
        jButtonCerrarSesion.setPreferredSize(new java.awt.Dimension(100, 30));
        jButtonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarSesionActionPerformed(evt);
            }
        });
        jPanelMenuInferiorChats.add(jButtonCerrarSesion);

        jButtonVerPerfil.setBackground(new java.awt.Color(0, 204, 51));
        jButtonVerPerfil.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonVerPerfil.setForeground(new java.awt.Color(0, 0, 0));
        jButtonVerPerfil.setText("Ver perfil");
        jButtonVerPerfil.setBorder(null);
        jButtonVerPerfil.setPreferredSize(new java.awt.Dimension(100, 30));
        jButtonVerPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerPerfilActionPerformed(evt);
            }
        });
        jPanelMenuInferiorChats.add(jButtonVerPerfil);

        jPanelInicio.add(jPanelMenuInferiorChats);

        jPanelLayout.add(jPanelInicio, "cardInicio");

        jPanelChat.setBackground(new java.awt.Color(51, 51, 51));
        jPanelChat.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelChat.setMinimumSize(new java.awt.Dimension(700, 850));
        jPanelChat.setPreferredSize(new java.awt.Dimension(700, 850));
        jPanelChat.setLayout(new javax.swing.BoxLayout(jPanelChat, javax.swing.BoxLayout.Y_AXIS));

        jPanelMenuSuperior1.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuSuperior1.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelMenuSuperior1.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelMenuSuperior1.setPreferredSize(new java.awt.Dimension(700, 100));
        jPanelMenuSuperior1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        jLabelServidorPuerto.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelServidorPuerto.setForeground(new java.awt.Color(255, 255, 255));
        jLabelServidorPuerto.setText("Servidor: 192.168.1.129   -   Puerto: 12345");
        jPanelMenuSuperior1.add(jLabelServidorPuerto);

        jPanelChat.add(jPanelMenuSuperior1);

        jPanelScrollChat.setMinimumSize(new java.awt.Dimension(600, 200));

        jScrollPaneChat.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPaneChat.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPaneChat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneChat.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPaneChat.setMinimumSize(new java.awt.Dimension(600, 200));
        jScrollPaneChat.setPreferredSize(new java.awt.Dimension(600, 200));

        jPanelMensajes.setBackground(new java.awt.Color(204, 204, 204));
        jPanelMensajes.setForeground(new java.awt.Color(0, 0, 0));
        jPanelMensajes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelMensajes.setMinimumSize(new java.awt.Dimension(600, 200));
        jPanelMensajes.setPreferredSize(new java.awt.Dimension(600, 200));
        jPanelMensajes.setLayout(new javax.swing.BoxLayout(jPanelMensajes, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneChat.setViewportView(jPanelMensajes);

        javax.swing.GroupLayout jPanelScrollChatLayout = new javax.swing.GroupLayout(jPanelScrollChat);
        jPanelScrollChat.setLayout(jPanelScrollChatLayout);
        jPanelScrollChatLayout.setHorizontalGroup(
            jPanelScrollChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScrollChatLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(jScrollPaneChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanelScrollChatLayout.setVerticalGroup(
            jPanelScrollChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScrollChatLayout.createSequentialGroup()
                .addComponent(jScrollPaneChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanelChat.add(jPanelScrollChat);

        jPanelMenuInferior1.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuInferior1.setMaximumSize(new java.awt.Dimension(700, 70));
        jPanelMenuInferior1.setMinimumSize(new java.awt.Dimension(700, 70));
        jPanelMenuInferior1.setPreferredSize(new java.awt.Dimension(700, 70));
        jPanelMenuInferior1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jButtonEnviarArchivo.setBackground(new java.awt.Color(0, 204, 51));
        jButtonEnviarArchivo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonEnviarArchivo.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEnviarArchivo.setText("Enviar Archivo");
        jButtonEnviarArchivo.setBorder(null);
        jButtonEnviarArchivo.setPreferredSize(new java.awt.Dimension(100, 30));
        jButtonEnviarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarArchivoActionPerformed(evt);
            }
        });
        jPanelMenuInferior1.add(jButtonEnviarArchivo);

        jTextFieldMensaje.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldMensaje.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldMensaje.setMaximumSize(new java.awt.Dimension(2147483647, 22));
        jTextFieldMensaje.setPreferredSize(new java.awt.Dimension(396, 30));
        jPanelMenuInferior1.add(jTextFieldMensaje);

        jButtonEnviar.setBackground(new java.awt.Color(0, 204, 51));
        jButtonEnviar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonEnviar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEnviar.setText("Enviar");
        jButtonEnviar.setBorder(null);
        jButtonEnviar.setPreferredSize(new java.awt.Dimension(100, 30));
        jButtonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarActionPerformed(evt);
            }
        });
        jButtonEnviar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonEnviarKeyPressed(evt);
            }
        });
        jPanelMenuInferior1.add(jButtonEnviar);

        jPanelChat.add(jPanelMenuInferior1);

        jPanelMenuInferior2.setBackground(new java.awt.Color(51, 51, 51));
        jPanelMenuInferior2.setMaximumSize(new java.awt.Dimension(700, 70));
        jPanelMenuInferior2.setMinimumSize(new java.awt.Dimension(700, 70));
        jPanelMenuInferior2.setPreferredSize(new java.awt.Dimension(700, 70));
        jPanelMenuInferior2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        jButtonVolverChatToChats.setBackground(new java.awt.Color(0, 204, 51));
        jButtonVolverChatToChats.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonVolverChatToChats.setForeground(new java.awt.Color(0, 0, 0));
        jButtonVolverChatToChats.setText("Volver");
        jButtonVolverChatToChats.setBorder(null);
        jButtonVolverChatToChats.setPreferredSize(new java.awt.Dimension(100, 30));
        jButtonVolverChatToChats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverChatToChatsActionPerformed(evt);
            }
        });
        jPanelMenuInferior2.add(jButtonVolverChatToChats);

        jPanelChat.add(jPanelMenuInferior2);

        jPanelLayout.add(jPanelChat, "cardChat");

        jPanelPerfil.setBackground(new java.awt.Color(51, 51, 51));
        jPanelPerfil.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanelPerfil.setMinimumSize(new java.awt.Dimension(700, 850));
        jPanelPerfil.setPreferredSize(new java.awt.Dimension(700, 850));
        jPanelPerfil.setLayout(new javax.swing.BoxLayout(jPanelPerfil, javax.swing.BoxLayout.Y_AXIS));

        jPanelTitulo1.setMaximumSize(new java.awt.Dimension(700, 120));
        jPanelTitulo1.setMinimumSize(new java.awt.Dimension(700, 120));
        jPanelTitulo1.setOpaque(false);
        jPanelTitulo1.setPreferredSize(new java.awt.Dimension(700, 120));
        jPanelTitulo1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        jLabelPerfil.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelPerfil.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPerfil.setText("Perfil");
        jPanelTitulo1.add(jLabelPerfil);

        jPanelPerfil.add(jPanelTitulo1);

        jPanelImagen3.setMaximumSize(new java.awt.Dimension(700, 200));
        jPanelImagen3.setMinimumSize(new java.awt.Dimension(700, 200));
        jPanelImagen3.setOpaque(false);
        jPanelImagen3.setPreferredSize(new java.awt.Dimension(700, 200));
        jPanelImagen3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        jLabelImagenPerfilCliente.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabelImagenPerfilCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLabelImagenPerfilCliente.setMaximumSize(new java.awt.Dimension(200, 200));
        jLabelImagenPerfilCliente.setMinimumSize(new java.awt.Dimension(200, 200));
        jLabelImagenPerfilCliente.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanelImagen3.add(jLabelImagenPerfilCliente);

        jPanelPerfil.add(jPanelImagen3);

        jPanelImagen4.setMaximumSize(new java.awt.Dimension(700, 70));
        jPanelImagen4.setMinimumSize(new java.awt.Dimension(700, 70));
        jPanelImagen4.setOpaque(false);
        jPanelImagen4.setPreferredSize(new java.awt.Dimension(700, 70));

        jButtonCambiarAvatar.setBackground(new java.awt.Color(0, 204, 51));
        jButtonCambiarAvatar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonCambiarAvatar.setForeground(new java.awt.Color(0, 0, 0));
        jButtonCambiarAvatar.setText("Cambiar Avatar");
        jButtonCambiarAvatar.setBorder(null);
        jButtonCambiarAvatar.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonCambiarAvatar.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonCambiarAvatar.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonCambiarAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCambiarAvatarActionPerformed(evt);
            }
        });
        jPanelImagen4.add(jButtonCambiarAvatar);

        jPanelPerfil.add(jPanelImagen4);

        jPanelNombre2.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelNombre2.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelNombre2.setOpaque(false);
        jPanelNombre2.setPreferredSize(new java.awt.Dimension(700, 100));

        jPanel3.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanel3.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelNombrePerfil.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelNombrePerfil.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombrePerfil.setText("Nombre:");
        jLabelNombrePerfil.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelNombrePerfil.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelNombrePerfil.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanel3.add(jLabelNombrePerfil);

        jTextFieldNombrePerfil.setBackground(new java.awt.Color(204, 204, 204));
        jTextFieldNombrePerfil.setForeground(new java.awt.Color(0, 0, 0));
        jTextFieldNombrePerfil.setEnabled(false);
        jTextFieldNombrePerfil.setMaximumSize(new java.awt.Dimension(410, 30));
        jTextFieldNombrePerfil.setMinimumSize(new java.awt.Dimension(410, 30));
        jTextFieldNombrePerfil.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanel3.add(jTextFieldNombrePerfil);

        jPanelNombre2.add(jPanel3);

        jPanelPerfil.add(jPanelNombre2);

        jPanelContraseña3.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña3.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña3.setOpaque(false);
        jPanelContraseña3.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelContraseñaPerfil.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelContraseñaPerfil.setForeground(new java.awt.Color(255, 255, 255));
        jLabelContraseñaPerfil.setText("Contraseña:");
        jLabelContraseñaPerfil.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelContraseñaPerfil.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelContraseñaPerfil.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanelContraseña3.add(jLabelContraseñaPerfil);

        jPasswordFieldContraseñaPerfil.setBackground(new java.awt.Color(204, 204, 204));
        jPasswordFieldContraseñaPerfil.setForeground(new java.awt.Color(0, 0, 0));
        jPasswordFieldContraseñaPerfil.setEnabled(false);
        jPasswordFieldContraseñaPerfil.setMaximumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldContraseñaPerfil.setMinimumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldContraseñaPerfil.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanelContraseña3.add(jPasswordFieldContraseñaPerfil);

        jPanelPerfil.add(jPanelContraseña3);

        jPanelContraseña4.setMaximumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña4.setMinimumSize(new java.awt.Dimension(700, 100));
        jPanelContraseña4.setOpaque(false);
        jPanelContraseña4.setPreferredSize(new java.awt.Dimension(700, 100));

        jLabelConfirmarContraseñaPerfil.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabelConfirmarContraseñaPerfil.setForeground(new java.awt.Color(255, 255, 255));
        jLabelConfirmarContraseñaPerfil.setText("Confirmar contraseña:");
        jLabelConfirmarContraseñaPerfil.setMaximumSize(new java.awt.Dimension(410, 32));
        jLabelConfirmarContraseñaPerfil.setMinimumSize(new java.awt.Dimension(410, 32));
        jLabelConfirmarContraseñaPerfil.setPreferredSize(new java.awt.Dimension(410, 32));
        jPanelContraseña4.add(jLabelConfirmarContraseñaPerfil);

        jPasswordFieldConfirmarContraseñaPerfil.setBackground(new java.awt.Color(204, 204, 204));
        jPasswordFieldConfirmarContraseñaPerfil.setForeground(new java.awt.Color(0, 0, 0));
        jPasswordFieldConfirmarContraseñaPerfil.setEnabled(false);
        jPasswordFieldConfirmarContraseñaPerfil.setMaximumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldConfirmarContraseñaPerfil.setMinimumSize(new java.awt.Dimension(410, 30));
        jPasswordFieldConfirmarContraseñaPerfil.setPreferredSize(new java.awt.Dimension(410, 30));
        jPanelContraseña4.add(jPasswordFieldConfirmarContraseñaPerfil);

        jPanelPerfil.add(jPanelContraseña4);

        jPanelBotones2.setMaximumSize(new java.awt.Dimension(700, 350));
        jPanelBotones2.setMinimumSize(new java.awt.Dimension(700, 350));
        jPanelBotones2.setOpaque(false);
        jPanelBotones2.setPreferredSize(new java.awt.Dimension(700, 350));

        jButtonVolverPerfilToChats.setBackground(new java.awt.Color(0, 204, 51));
        jButtonVolverPerfilToChats.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonVolverPerfilToChats.setForeground(new java.awt.Color(0, 0, 0));
        jButtonVolverPerfilToChats.setText("Volver");
        jButtonVolverPerfilToChats.setBorder(null);
        jButtonVolverPerfilToChats.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonVolverPerfilToChats.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonVolverPerfilToChats.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonVolverPerfilToChats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverPerfilToChatsActionPerformed(evt);
            }
        });
        jPanelBotones2.add(jButtonVolverPerfilToChats);

        jButtonEditarPerfil.setBackground(new java.awt.Color(0, 204, 51));
        jButtonEditarPerfil.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButtonEditarPerfil.setForeground(new java.awt.Color(0, 0, 0));
        jButtonEditarPerfil.setText("Editar");
        jButtonEditarPerfil.setBorder(null);
        jButtonEditarPerfil.setMaximumSize(new java.awt.Dimension(200, 30));
        jButtonEditarPerfil.setMinimumSize(new java.awt.Dimension(200, 30));
        jButtonEditarPerfil.setPreferredSize(new java.awt.Dimension(200, 30));
        jButtonEditarPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarPerfilActionPerformed(evt);
            }
        });
        jPanelBotones2.add(jButtonEditarPerfil);

        jPanelPerfil.add(jPanelBotones2);

        jPanelLayout.add(jPanelPerfil, "cardPerfil");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanelLayout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonAccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAccederActionPerformed
        String usuarioIntroducido = this.getjTextFieldNombreInicioSesion().getText();

        if (usuarioIntroducido.isEmpty()) {
            MensajeDialog.showMessageDialog(this, "Por favor, complete todos los campos.", "Alerta");
            return;
        }

        if (!existUser(usuarioIntroducido)) {
            MensajeDialog.showMessageDialog(this, "Usuario no encontrado", "Alerta");
            return;
        }

        char[] contraseñaUsuario = Main.getCurrentUser().getPasswd();
        char[] contraseñaIntroducida = this.getjPasswordFieldContraseñaInicioSesion().getPassword();

        if (contraseñaIntroducida.length == 0) {
            MensajeDialog.showMessageDialog(this, "Por favor, complete todos los campos.", "Alerta");
        } else if (!Arrays.equals(contraseñaIntroducida, contraseñaUsuario)) {
            MensajeDialog.showMessageDialog(this, "Contraseña incorrecta.", "Alerta");
        } else {
            setProfile();
            this.cambiarLayout("cardInicio");
        }

        // Limpiar la contraseña ingresada por seguridad
        Arrays.fill(contraseñaIntroducida, '\0');
    }//GEN-LAST:event_jButtonAccederActionPerformed

    private void jButtonCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearCuentaActionPerformed
        ctrlr.escalarEstablecerImagenFromString(jLabelImagenCrearCliente, Client.DEFAULT_AVATAR_ID);
        cambiarLayout("cardCrearCuenta");
    }//GEN-LAST:event_jButtonCrearCuentaActionPerformed

    private void jButtonConfirmarCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarCrearCuentaActionPerformed
        // Validaciones de campos
        if (this.getjTextFieldCrearNombre().getText().trim().isEmpty()
                || this.getjPasswordFieldCrearContraseña().getPassword().length == 0
                || this.getjPasswordFieldConfirmarContraseña().getPassword().length == 0) {
            MensajeDialog.showMessageDialog(this, "Complete todos los campos y seleccione un avatar.", "Alerta");
            return;
        }

        // Validar si ya existe un cliente con el mismo nombre
        String nombre = this.getjTextFieldCrearNombre().getText().trim();

        if (existUser(nombre)) {
            MensajeDialog.showMessageDialog(this, "Ya existe un cliente con el mismo nombre.", "Alerta");
            return;
        }

        if (this.getjPasswordFieldCrearContraseña().getPassword().length < 6) {
            MensajeDialog.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", "Alerta");
            return;
        }

        if (!Arrays.equals(this.getjPasswordFieldCrearContraseña().getPassword(), this.getjPasswordFieldConfirmarContraseña().getPassword())) {
            MensajeDialog.showMessageDialog(this, "Las contraseñas no coinciden.", "Alerta");
            return;
        }

        if (Main.getAvatarPathSelected() == null || Main.getAvatarPathSelected().isBlank()) {
            Main.setAvatarPathSelected(Main.getUSER_ICON_URL());
            ctrlr.escalarEstablecerImagenFromString(this.getjLabelImagenCrearCliente(), Main.getUSER_ICON_URL());
        }

        char[] contraseña = this.getjPasswordFieldCrearContraseña().getPassword();

        System.out.println("avatarPathSelected: " + Main.getAvatarPathSelected());

        Client cliente = new Client(nombre, contraseña, Main.getAvatarPathSelected());

        Main.setCurrentUser(cliente);
        cliente.createClientFolder();
        cliente.saveClientData();

        System.out.println("\nCuenta creada exitosamente.\n");
        MensajeDialog.showMessageDialog(this, "¡Cuenta creada exitosamente!", "Información");

        this.cambiarLayout("cardInicioSesion");

        // Limpiar campos
        this.getjTextFieldCrearNombre().setText("");
        this.getjPasswordFieldCrearContraseña().setText("");
        this.getjPasswordFieldConfirmarContraseña().setText("");
        this.getjLabelImagenCrearCliente().setIcon(null);
    }//GEN-LAST:event_jButtonConfirmarCrearCuentaActionPerformed

    private void jButtonVolverCrearCuentaToInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverCrearCuentaToInicioSesionActionPerformed
        cambiarLayout("cardInicioSesion");
    }//GEN-LAST:event_jButtonVolverCrearCuentaToInicioSesionActionPerformed

    private void jButtonCrearAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearAvatarActionPerformed
        // Crear el FileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        // Filtro para solo permitir imágenes
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg, *.gif)", "png", "jpg", "jpeg", "gif");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtener el path seleccionado
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();

            Main.setAvatarPathSelected(imagePath);

            ctrlr.escalarEstablecerImagenFromString(this.getjLabelImagenCrearCliente(), Main.getAvatarPathSelected());
        }
    }//GEN-LAST:event_jButtonCrearAvatarActionPerformed

    private void jButtonConectarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConectarClienteActionPerformed
        getRootPane().setDefaultButton(jButtonEnviar);
        this.connectUserToServer();
        new Thread(() -> Main.getPc().showMessagesOnServer()).start();
    }//GEN-LAST:event_jButtonConectarClienteActionPerformed

    private void jButtonConectarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConectarServidorActionPerformed
        // Obtener IP y puerto desde los campos de texto

        Main.setServerIP(jTextFieldServerIP.getText());
        Main.setPort(Integer.parseInt(jTextFieldPuerto.getText()));

        Main.getServer().connect(Main.getServerIP(), Main.getPort());

        // Mostrar en el área de texto que el servidor ha sido iniciado
        MensajeDialog.showMessageDialog(this, "Servidor iniciado en " + Main.getServerIP() + ":" + Main.getPort(), "Información");

    }//GEN-LAST:event_jButtonConectarServidorActionPerformed

    private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarActionPerformed
        String message = getjTextFieldMensaje().getText(); // Obtener mensaje del campo de texto
        String messageToSend = Main.getCurrentUser().getUsername() + ": " + message;
        Main.getCurrentUser().sendMessage(messageToSend);
        jTextFieldMensaje.setText("");  // Limpiar el campo de texto
    }//GEN-LAST:event_jButtonEnviarActionPerformed

    private void jButtonEnviarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonEnviarKeyPressed
        Controller.funcionBoton(evt, jButtonEnviar);
    }//GEN-LAST:event_jButtonEnviarKeyPressed

    private void jButtonVolverChatToChatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverChatToChatsActionPerformed

        Main.getCurrentUser().close();
        cambiarLayout("cardInicio");
    }//GEN-LAST:event_jButtonVolverChatToChatsActionPerformed

    private void jButtonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarSesionActionPerformed

        // Vaciar el panel de chats antes de cambiar de pantalla
        jPanelServidores.removeAll();

        // Actualizar la interfaz gráfica
        jPanelServidores.revalidate();
        jPanelServidores.repaint();

        // Desconectar el servidor
        if (Main.getServer() != null) {
            Main.getServer().close();
        }

        if (Main.getCurrentUser() != null) {
            Main.getCurrentUser().close();
            Main.setCurrentUser(null);
        }

        getRootPane().setDefaultButton(jButtonAcceder);
        cambiarLayout("cardInicioSesion");
    }//GEN-LAST:event_jButtonCerrarSesionActionPerformed

    private void jButtonCambiarAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCambiarAvatarActionPerformed
        // Crear el FileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        // Filtro para solo permitir imágenes
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg, *.gif)", "png", "jpg", "jpeg", "gif");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtener el path seleccionado
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Guardar el path en una variable (puedes usar una variable de clase si es necesario)
            Main.getCurrentUser().setAvatar(imagePath);
            Main.getCurrentUser().createClientFolder();
            Main.getCurrentUser().saveClientData();

            ctrlr.escalarEstablecerImagenFromString(this.getjLabelImagenCrearCliente(), Main.getCurrentUser().getAvatar());
            ctrlr.escalarEstablecerImagenFromString(this.getjLabelImagenPerfilCliente(), Main.getCurrentUser().getAvatar());
        }
    }//GEN-LAST:event_jButtonCambiarAvatarActionPerformed

    private void jButtonVolverPerfilToChatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverPerfilToChatsActionPerformed
        // Restablecer el texto del botón a "Editar"
        this.getjButtonEditarPerfil().setText("Editar");

        // Guardar valores originales
        Client currentUser = Main.getCurrentUser();
        originalUsername = currentUser.getUsername();
        originalPassword = currentUser.getPasswdAsString();

        this.getjTextFieldNombrePerfil().setText(originalUsername);
        this.getjPasswordFieldContraseñaPerfil().setText(originalPassword);
        this.getjPasswordFieldConfirmarContraseñaPerfil().setText(originalPassword);

        // Deshabilitar los campos de edición
        this.getjTextFieldNombrePerfil().setEnabled(false);
        this.getjPasswordFieldContraseñaPerfil().setEnabled(false);
        this.getjPasswordFieldConfirmarContraseñaPerfil().setEnabled(false);

        // Cambiar el layout
        cambiarLayout("cardInicio");
    }//GEN-LAST:event_jButtonVolverPerfilToChatsActionPerformed

    private void jButtonEditarPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarPerfilActionPerformed

        if (this.getjButtonEditarPerfil().getText().equals("Editar")) {
            // Modo edición - Guardar valores originales y habilitar campos
            enterEditMode();

        } else {
            // Modo aceptar - Validar y guardar cambios
            saveProfileChanges();

        }

    }//GEN-LAST:event_jButtonEditarPerfilActionPerformed

    private void enterEditMode() {
        // Guardar valores originales
        Client currentUser = Main.getCurrentUser();
        originalUsername = currentUser.getUsername();
        originalPassword = currentUser.getPasswdAsString();

        // Habilitar edición
        this.getjButtonEditarPerfil().setText("Aceptar");
        this.getjTextFieldNombrePerfil().setEnabled(true);
        this.getjPasswordFieldContraseñaPerfil().setEnabled(true);
        this.getjPasswordFieldConfirmarContraseñaPerfil().setEnabled(true);

    }

    private void saveProfileChanges() {
        // Obtener valores del formulario
        String nuevoNombre = this.getjTextFieldNombrePerfil().getText().trim();
        char[] nuevaPasswd = this.getjPasswordFieldContraseñaPerfil().getPassword();
        char[] confirmPasswd = this.getjPasswordFieldConfirmarContraseñaPerfil().getPassword();

        // Validaciones básicas
        if (!validateProfileFields(nuevoNombre, nuevaPasswd, confirmPasswd)) {
            return;
        }

        try {
            // Verificar nombre único si cambió
            if (!nuevoNombre.equals(originalUsername) && existUser(nuevoNombre)) {
                MensajeDialog.showMessageDialog(this, "Nombre de usuario ya existe.", "Alerta");
                return;
            }

            // Actualizar datos del usuario
            updateUserProfile(nuevoNombre, nuevaPasswd);

            // Guardar cambios
            Main.getCurrentUser().saveClientData();
            MensajeDialog.showMessageDialog(this, "Perfil actualizado correctamente", "Éxito");

            // Restablecer UI
            exitEditMode();

        } catch (IOException e) {
            MensajeDialog.showMessageDialog(this, "Error al actualizar perfil: " + e.getMessage(), "Error");

        } finally {
            // Limpiar arrays de contraseña por seguridad
            Arrays.fill(nuevaPasswd, '\0');
            Arrays.fill(confirmPasswd, '\0');

        }

    }

    private boolean validateProfileFields(String nombre, char[] passwd, char[] confirmPasswd) {
        if (nombre.isEmpty()) {
            MensajeDialog.showMessageDialog(this, "El nombre de usuario no puede estar vacío.", "Alerta");
            return false;
        }

        if (passwd.length == 0 || confirmPasswd.length == 0) {
            MensajeDialog.showMessageDialog(this, "Complete ambos campos de contraseña.", "Alerta");
            return false;
        }

        if (!Arrays.equals(passwd, confirmPasswd)) {
            MensajeDialog.showMessageDialog(this, "Las contraseñas no coinciden.", "Alerta");
            return false;
        }

        if (passwd.length < 6) {
            MensajeDialog.showMessageDialog(this, "La contraseña debe tener al menos 6 caracteres.", "Alerta");
            return false;
        }

        return true;
    }

    private void updateUserProfile(String nuevoNombre, char[] nuevaPasswd) throws IOException {
        Client currentUser = Main.getCurrentUser();
        boolean nombreCambiado = !nuevoNombre.equals(originalUsername);

        // Manejar cambio de nombre (y carpeta)
        if (nombreCambiado) {
            handleUsernameChange(currentUser, nuevoNombre);
        }

        // Actualizar datos del usuario
        currentUser.setUsername(nuevoNombre);
        currentUser.setPasswd(nuevaPasswd);

        // Manejar avatar (si hay uno nuevo seleccionado o si cambió el nombre)
        if (Main.getAvatarPathSelected() != null && !Main.getAvatarPathSelected().equals(originalAvatar)) {
            handleAvatarChange(currentUser);
        } else if (nombreCambiado && currentUser.getAvatar() != null
                && !currentUser.getAvatar().equals(Main.getUSER_ICON_URL())) {
            // Si cambió el nombre pero no el avatar, y el avatar no es el por defecto,
            // asegurarnos de que la ruta del avatar sea correcta
            String avatarPath = currentUser.getAvatar();
            if (avatarPath.contains(originalUsername)) {
                String nuevoAvatarPath = avatarPath.replace(
                        File.separator + originalUsername + File.separator,
                        File.separator + nuevoNombre + File.separator
                );
                currentUser.setAvatar(nuevoAvatarPath);
            }
        }
    }

    private void handleUsernameChange(Client user, String nuevoNombre) throws IOException {
        // 1. Crear nueva estructura de carpetas
        Path nuevaCarpeta = Paths.get(Main.getCLIENTS_FOLDER_PATH(), nuevoNombre);
        Files.createDirectories(nuevaCarpeta);

        // 2. Copiar archivos de la carpeta antigua a la nueva
        Path carpetaAntigua = Paths.get(Main.getCLIENTS_FOLDER_PATH(), originalUsername);
        if (Files.exists(carpetaAntigua)) {
            Files.walk(carpetaAntigua)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            Path destino = nuevaCarpeta.resolve(path.getFileName());
                            Files.copy(path, destino, StandardCopyOption.REPLACE_EXISTING);

                            // 3. Si es el archivo de avatar, actualizar la referencia
                            if (path.getFileName().toString().equals("avatar.jpg")) {
                                user.setAvatar(destino.toString());
                            }
                        } catch (IOException e) {
                            System.err.println("Error copiando archivo: " + e.getMessage());
                        }
                    });
        }

        // 4. Actualizar ruta de la carpeta en el usuario
        user.setClientFolderPath(nuevoNombre);

        // 5. Manejar el avatar por defecto
        if (user.getAvatar() != null && user.getAvatar().equals(Main.getUSER_ICON_URL())) {
            // No necesita cambios, sigue siendo el avatar por defecto
        } else if (user.getAvatar() != null && user.getAvatar().contains(originalUsername)) {
            // Actualizar ruta del avatar si estaba en la carpeta antigua
            String nuevoAvatarPath = user.getAvatar().replace(
                    File.separator + originalUsername + File.separator,
                    File.separator + nuevoNombre + File.separator
            );
            user.setAvatar(nuevoAvatarPath);
        }

        // 6. Eliminar carpeta antigua si es diferente
        if (!originalUsername.equals(nuevoNombre)) {
            deleteDirectory(carpetaAntigua.toFile());
        }
    }

    private void handleAvatarChange(Client user) throws IOException {
        String nuevoAvatarPath = Main.getAvatarPathSelected();

        // Si es el avatar por defecto, solo guardar la referencia
        if (nuevoAvatarPath.equals(Main.getUSER_ICON_URL())) {
            user.setAvatar(nuevoAvatarPath);
        } else {
            // Copiar el archivo de avatar a la carpeta del usuario
            Path destinoAvatar = Paths.get(user.getClientFolderPath(), "avatar.jpg");
            Files.copy(Paths.get(nuevoAvatarPath), destinoAvatar, StandardCopyOption.REPLACE_EXISTING);
            user.setAvatar(destinoAvatar.toString());
        }
    }

    private void exitEditMode() {
        this.getjButtonEditarPerfil().setText("Editar");
        this.getjTextFieldNombrePerfil().setEnabled(false);
        this.getjPasswordFieldContraseñaPerfil().setEnabled(false);
        this.getjPasswordFieldConfirmarContraseñaPerfil().setEnabled(false);
    }

    private boolean deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directory.delete();
    }


    private void jButtonVerPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerPerfilActionPerformed
        cambiarLayout("cardPerfil");
    }//GEN-LAST:event_jButtonVerPerfilActionPerformed

    // (REVISAR)
    private void jButtonEnviarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarArchivoActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            Main.getCurrentUser().sendFile(file.getPath());  // Llamar al método de envío de archivo
        }

    }//GEN-LAST:event_jButtonEnviarArchivoActionPerformed

    private void jButtonEliminarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarServidorActionPerformed
        // Ejemplo de uso:
        pc.removeServerPanel(jTextFieldServerIP.getText() + ":" + jTextFieldPuerto.getText());
    }//GEN-LAST:event_jButtonEliminarServidorActionPerformed

    private void jButtonAccederKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonAccederKeyPressed
        Controller.funcionBoton(evt, jButtonAcceder);
    }//GEN-LAST:event_jButtonAccederKeyPressed

    @Override
    public AccessibleContext getAccessibleContext() {
        return super.getAccessibleContext();
    }

    public JButton getjButtonEnviar() {
        return jButtonEnviar;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getjPanelMensajes() {
        return jPanelMensajes;
    }

    public JLabel getjLabelImagenCrearCliente() {
        return jLabelImagenCrearCliente;
    }

    public JLabel getjLabelImagenPerfilCliente() {
        return jLabelImagenPerfilCliente;
    }

    public JLabel getjLabelServidorPuerto() {
        return jLabelServidorPuerto;
    }

    public JTextField getjTextFieldCrearNombre() {
        return jTextFieldCrearNombre;
    }

    public void setjTextFieldCrearNombre(JTextField jTextFieldCrearNombre) {
        this.jTextFieldCrearNombre = jTextFieldCrearNombre;
    }

    public JTextField getjTextFieldMensaje() {
        return jTextFieldMensaje;
    }

    public void setjTextFieldMensaje(JTextField jTextFieldMensaje) {
        this.jTextFieldMensaje = jTextFieldMensaje;
    }

    public JTextField getjTextFieldNombreInicioSesion() {
        return jTextFieldNombreInicioSesion;
    }

    public void setjTextFieldNombreInicioSesion(JTextField jTextFieldNombreInicioSesion) {
        this.jTextFieldNombreInicioSesion = jTextFieldNombreInicioSesion;
    }

    public JTextField getjTextFieldNombrePerfil() {
        return jTextFieldNombrePerfil;
    }

    public void setjTextFieldNombrePerfil(JTextField jTextFieldNombrePerfil) {
        this.jTextFieldNombrePerfil = jTextFieldNombrePerfil;
    }

    public JTextField getjTextFieldPuerto() {
        return jTextFieldPuerto;
    }

    public void setjTextFieldPuerto(JTextField jTextFieldPuerto) {
        this.jTextFieldPuerto = jTextFieldPuerto;
    }

    public JTextField getjTextFieldServerIP() {
        return jTextFieldServerIP;
    }

    public void setjTextFieldServerIP(JTextField jTextFieldServerIP) {
        this.jTextFieldServerIP = jTextFieldServerIP;
    }

    public JPasswordField getjPasswordFieldConfirmarContraseña() {
        return jPasswordFieldConfirmarContraseña;
    }

    public void setjPasswordFieldConfirmarContraseña(JPasswordField jPasswordFieldConfirmarContraseña) {
        this.jPasswordFieldConfirmarContraseña = jPasswordFieldConfirmarContraseña;
    }

    public JPasswordField getjPasswordFieldConfirmarContraseñaPerfil() {
        return jPasswordFieldConfirmarContraseñaPerfil;
    }

    public void setjPasswordFieldConfirmarContraseñaPerfil(JPasswordField jPasswordFieldConfirmarContraseñaPerfil) {
        this.jPasswordFieldConfirmarContraseñaPerfil = jPasswordFieldConfirmarContraseñaPerfil;
    }

    public JPasswordField getjPasswordFieldContraseñaInicioSesion() {
        return jPasswordFieldContraseñaInicioSesion;
    }

    public void setjPasswordFieldContraseñaInicioSesion(JPasswordField jPasswordFieldContraseñaInicioSesion) {
        this.jPasswordFieldContraseñaInicioSesion = jPasswordFieldContraseñaInicioSesion;
    }

    public JPasswordField getjPasswordFieldContraseñaPerfil() {
        return jPasswordFieldContraseñaPerfil;
    }

    public void setjPasswordFieldContraseñaPerfil(JPasswordField jPasswordFieldContraseñaPerfil) {
        this.jPasswordFieldContraseñaPerfil = jPasswordFieldContraseñaPerfil;
    }

    public JPasswordField getjPasswordFieldCrearContraseña() {
        return jPasswordFieldCrearContraseña;
    }

    public void setjPasswordFieldCrearContraseña(JPasswordField jPasswordFieldCrearContraseña) {
        this.jPasswordFieldCrearContraseña = jPasswordFieldCrearContraseña;
    }

    public JPanel getjPanelChat() {
        return jPanelChat;
    }

    public JPanel getjPanelChats() {
        return jPanelServidores;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public JButton getjButtonEditarPerfil() {
        return jButtonEditarPerfil;
    }

    public Controller getCtrlr() {
        return ctrlr;
    }

    public PanelsController getPc() {
        return pc;
    }

    public UserController getUc() {
        return uc;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAcceder;
    private javax.swing.JButton jButtonCambiarAvatar;
    private javax.swing.JButton jButtonCerrarSesion;
    private javax.swing.JButton jButtonConectarCliente;
    private javax.swing.JButton jButtonConectarServidor;
    private javax.swing.JButton jButtonConfirmarCrearCuenta;
    private javax.swing.JButton jButtonCrearAvatar;
    private javax.swing.JButton jButtonCrearCuenta;
    private javax.swing.JButton jButtonEditarPerfil;
    private javax.swing.JButton jButtonEliminarServidor;
    private javax.swing.JButton jButtonEnviar;
    private javax.swing.JButton jButtonEnviarArchivo;
    private javax.swing.JButton jButtonVerPerfil;
    private javax.swing.JButton jButtonVolverChatToChats;
    private javax.swing.JButton jButtonVolverCrearCuentaToInicioSesion;
    private javax.swing.JButton jButtonVolverPerfilToChats;
    private javax.swing.JLabel jLabelConfirmarContraseña;
    private javax.swing.JLabel jLabelConfirmarContraseñaPerfil;
    private javax.swing.JLabel jLabelContraseñaInicioSesion;
    private javax.swing.JLabel jLabelContraseñaPerfil;
    private javax.swing.JLabel jLabelCrearContraseña;
    private javax.swing.JLabel jLabelCrearCuenta;
    private javax.swing.JLabel jLabelCrearNombre;
    private javax.swing.JLabel jLabelIconoApp;
    private javax.swing.JLabel jLabelImagenCrearCliente;
    private javax.swing.JLabel jLabelImagenPerfilCliente;
    private javax.swing.JLabel jLabelNombreInicioSesion;
    private javax.swing.JLabel jLabelNombrePerfil;
    private javax.swing.JLabel jLabelPerfil;
    private javax.swing.JLabel jLabelServidorPuerto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBotones;
    private javax.swing.JPanel jPanelBotones1;
    private javax.swing.JPanel jPanelBotones2;
    private javax.swing.JPanel jPanelChat;
    private javax.swing.JPanel jPanelContraseña;
    private javax.swing.JPanel jPanelContraseña1;
    private javax.swing.JPanel jPanelContraseña2;
    private javax.swing.JPanel jPanelContraseña3;
    private javax.swing.JPanel jPanelContraseña4;
    private javax.swing.JPanel jPanelCrearCuenta;
    private javax.swing.JPanel jPanelImagen;
    private javax.swing.JPanel jPanelImagen1;
    private javax.swing.JPanel jPanelImagen2;
    private javax.swing.JPanel jPanelImagen3;
    private javax.swing.JPanel jPanelImagen4;
    private javax.swing.JPanel jPanelInicio;
    private javax.swing.JPanel jPanelInicioSesion;
    public javax.swing.JPanel jPanelLayout;
    private javax.swing.JPanel jPanelMensajes;
    private javax.swing.JPanel jPanelMenuInferior1;
    private javax.swing.JPanel jPanelMenuInferior2;
    private javax.swing.JPanel jPanelMenuInferiorChats;
    private javax.swing.JPanel jPanelMenuSuperior1;
    private javax.swing.JPanel jPanelMenuSuperiorChats;
    private javax.swing.JPanel jPanelMenuSuperiorChats2;
    private javax.swing.JPanel jPanelMenuSuperiorChats3;
    private javax.swing.JPanel jPanelNombre;
    private javax.swing.JPanel jPanelNombre1;
    private javax.swing.JPanel jPanelNombre2;
    private javax.swing.JPanel jPanelPerfil;
    private javax.swing.JPanel jPanelScrollChat;
    private javax.swing.JPanel jPanelScrollServidores;
    private javax.swing.JPanel jPanelServidores;
    private javax.swing.JPanel jPanelTitulo;
    private javax.swing.JPanel jPanelTitulo1;
    private javax.swing.JPasswordField jPasswordFieldConfirmarContraseña;
    private javax.swing.JPasswordField jPasswordFieldConfirmarContraseñaPerfil;
    private javax.swing.JPasswordField jPasswordFieldContraseñaInicioSesion;
    private javax.swing.JPasswordField jPasswordFieldContraseñaPerfil;
    private javax.swing.JPasswordField jPasswordFieldCrearContraseña;
    private javax.swing.JScrollPane jScrollPaneChat;
    private javax.swing.JScrollPane jScrollPaneServidores;
    private javax.swing.JTextField jTextFieldCrearNombre;
    private javax.swing.JTextField jTextFieldMensaje;
    private javax.swing.JTextField jTextFieldNombreInicioSesion;
    private javax.swing.JTextField jTextFieldNombrePerfil;
    private javax.swing.JTextField jTextFieldPuerto;
    private javax.swing.JTextField jTextFieldServerIP;
    // End of variables declaration//GEN-END:variables

}
