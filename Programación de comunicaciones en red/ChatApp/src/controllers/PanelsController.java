package controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import main.Main;
import models.Client;
import models.ServerMessage;
import org.json.JSONException;
import org.json.JSONObject;
import ui.ChatApp;
import ui.MensajeDialog;

/**
 *
 * @author kgv17
 */
public class PanelsController {

    private Controller ctrlr;
    private ChatApp APP;

    public PanelsController(ChatApp APP) {
        this.ctrlr = new Controller();
        this.APP = APP;
    }

    public JPanel createServerPanel(String text) {
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBackground(new Color(204, 204, 204));

        JLabel messageLabel = new JLabel(
                "<html><div style='width:400px; text-align:center;'>"
                + text.replace("\\n", "<br>")
                + "</div></html>"
        );
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setForeground(Color.white);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(new Color(51, 51, 51));
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        messageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messagePanel.setMaximumSize(new Dimension(600, 50));

        messageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] parts = text.split(":");
                if (parts.length == 2) {
                    try {
                        String selectedIP = parts[0].trim();
                        int selectedPort = Integer.parseInt(parts[1].trim());

                        Main.getServer().connect(selectedIP, selectedPort);

                        Main.getCurrentUser().setServerIP(selectedIP);
                        Main.getCurrentUser().setPort(selectedPort);
                        Main.getCurrentUser().connect();
                        new Thread(() -> showMessagesOnServer()).start();  // Escuchar mensajes en un hilo separado

                        String connectionMsg = Main.getCurrentUser().isConnected()
                                ? Main.getCurrentUser().getUsername() + ": Conectado a " + selectedIP + ":" + selectedPort
                                : Main.getCurrentUser().getUsername() + ": Error al conectar a " + selectedIP + ":" + selectedPort;

                        // Enviar mensaje al servidor
                        Main.getCurrentUser().sendMessage(connectionMsg);

                        APP.getjTextFieldMensaje().setText("");

                        APP.getjLabelServidorPuerto().setText("Servidor: " + selectedIP + " | Puerto: " + selectedPort);

                        MensajeDialog.showMessageDialog(APP, "Conexión establecida", "Información");

                        if (Main.getCurrentUser().isConnected()) {
                            APP.getRootPane().setDefaultButton(APP.getjButtonEnviar());
                            APP.cambiarLayout("cardChat");
                        }
                    } catch (NumberFormatException ex) {
                        updateChatPanel("Sistema: Puerto inválido. No es un número.");
                    }
                }
            }
        });

        messagePanel.add(messageLabel);
        return messagePanel;
    }

    public void updateServerPanels() {
        
        APP.getjPanelChats().removeAll();  // Limpiar el panel antes de añadir los servidores

        for (List<ServerMessage> mensajes : Main.getCurrentUser().getServerList().values()) {
            
            for (ServerMessage msg : mensajes) {
                APP.getjPanelChats().add(createServerPanel(msg.getMessage()));
            }
            
        }

        APP.getjPanelChats().revalidate();
        APP.getjPanelChats().repaint();
        
    }

    public void updateServersPanel(String message) {
        
        String[] parts = message.split(": ", 2);
        
        if (parts.length == 2) {
            
            String username = Main.getCurrentUser().getUsername();
            String text = parts[1];

            // Si no existe una lista para el cliente, crear una nueva
            Main.getCurrentUser().getServerList().putIfAbsent(username, new ArrayList<>());
            Main.getCurrentUser().getServerList().get(username).add(new ServerMessage(username, text));

            APP.saveClientsServers();

            JPanel messagePanel = createServerPanel(text);
            APP.getjPanelChats().add(messagePanel);
            APP.getjPanelChats().revalidate();
            APP.getjPanelChats().repaint();

        }
        
    }

    /**
     * Elimina un panel de servidor específico basado en el texto que contiene
     *
     * @param textToRemove El texto del panel que se desea eliminar
     */
    public void removeServerPanel(String textToRemove) {
        
        Component[] panels = APP.getjPanelChats().getComponents();

        for (Component panel : panels) {
            
            if (panel instanceof JPanel serverPanel) {
                
                // Buscar el JLabel dentro del panel
                for (Component comp : serverPanel.getComponents()) {
                    
                    if (comp instanceof JLabel label) {
                        
                        // Obtener el texto del HTML (eliminando las etiquetas)
                        String labelText = label.getText()
                                .replaceAll("<html>.*?>", "")
                                .replaceAll("</.*?>", "")
                                .replaceAll("\\\\n", "\n")
                                .trim();

                        if (labelText.equals(textToRemove.trim())) {
                            
                            APP.getjPanelChats().remove(panel);
                            // También eliminar de la lista de servidores
                            removeFromServerList(textToRemove);
                            APP.getjPanelChats().revalidate();
                            APP.getjPanelChats().repaint();
                            return;
                            
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
    }

    /**
     * Elimina un servidor de la lista de servidores del usuario actual
     *
     * @param serverText El texto del servidor a eliminar
     */
    private void removeFromServerList(String serverText) {
        
        String username = Main.getCurrentUser().getUsername();
        
        if (Main.getCurrentUser().getServerList().containsKey(username)) {
            
            List<ServerMessage> messages = Main.getCurrentUser().getServerList().get(username);
            // Buscar y eliminar el mensaje que coincida
            messages.removeIf(msg -> msg.getMessage().equals(serverText));

            // Si la lista queda vacía, eliminar la entrada del mapa
            if (messages.isEmpty()) {
                Main.getCurrentUser().getServerList().remove(username);
            }

            // Guardar los cambios
            APP.saveClientsServers();
            
        }
        
    }

    private JPanel createMessagePanel(String username, String text, ImageIcon avatarIcon) {
        JPanel messagePanel = new JPanel();
        messagePanel.setMaximumSize(new Dimension(600, 50));
        messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        messagePanel.setBackground(new Color(204, 204, 204));

        JLabel avatarLabel = new JLabel(avatarIcon);
        messagePanel.add(avatarLabel);

        JLabel usernameLabel = new JLabel(username + ": ");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        messagePanel.add(usernameLabel);

        JLabel messageLabel = new JLabel(
                "<html><div style='width:300px;'>" + text.replace("\n", "<br>") + "</div></html>"
        );
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        messagePanel.add(messageLabel);

        return messagePanel;
    }

    public void updateChatPanel(String message) {
        String[] parts = message.split(": ", 2);
        if (parts.length == 2) {
            String username = parts[0];
            String text = parts[1];

            // Usar SwingUtilities para asegurar que se ejecute en el hilo de UI
            SwingUtilities.invokeLater(() -> {
                try {
                    ImageIcon avatarIcon = loadAvatarForUser(username);

                    JPanel messagePanel = createMessagePanel(username, text, avatarIcon);
                    APP.getjPanelMensajes().add(messagePanel);

                    APP.getjPanelMensajes().revalidate();
                    APP.getjPanelMensajes().repaint();

                    // Auto-scroll al último mensaje
                    JScrollBar vertical = APP.getjScrollPaneChat().getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());
                } catch (Exception e) {
                    System.err.println("Error al mostrar mensaje: " + e.getMessage());
                    // Mostrar mensaje con avatar predeterminado si hay error
                    JPanel messagePanel = createMessagePanel(username, text, getDefaultAvatarIcon());
                    APP.getjPanelMensajes().add(messagePanel);
                    APP.getjPanelMensajes().revalidate();
                    APP.getjPanelMensajes().repaint();
                }
            });
        }
    }

    /**
     * Crea un panel para mostrar un archivo recibido
     *
     * @param username Nombre de usuario que envió el archivo
     * @param filename Nombre del archivo
     * @param avatarIcon Icono de avatar del usuario
     * @return Panel que representa el archivo en el chat
     */
    private JPanel createFilePanel(String username, String filename, ImageIcon avatarIcon) {
        JPanel filePanel = new JPanel();
        filePanel.setMaximumSize(new Dimension(600, 70));
        filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filePanel.setBackground(new Color(204, 204, 204));

        // Avatar del usuario
        JLabel avatarLabel = new JLabel(avatarIcon);
        filePanel.add(avatarLabel);

        // Nombre de usuario
        JLabel usernameLabel = new JLabel(username + ": ");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        filePanel.add(usernameLabel);

        // Panel para el archivo
        JPanel attachmentPanel = new JPanel();
        attachmentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        attachmentPanel.setBackground(new Color(230, 230, 250));
        attachmentPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 200), 1));

        // Icono de archivo
        ImageIcon fileIcon;
        try {
            Image iconImage = ImageIO.read(new File(Main.getFILE_ICON_PATH()));
            iconImage = iconImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            fileIcon = new ImageIcon(iconImage);
        } catch (IOException e) {
            // Si no se puede cargar el icono, usar un texto como alternativa
            fileIcon = new ImageIcon();
        }

        JLabel iconLabel = new JLabel(fileIcon);
        attachmentPanel.add(iconLabel);

        // Nombre del archivo
        JLabel filenameLabel = new JLabel(filename);
        filenameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        filenameLabel.setForeground(new Color(50, 50, 150));
        attachmentPanel.add(filenameLabel);

        // Botón para descargar
        JLabel downloadLabel = new JLabel("Descargar");
        downloadLabel.setFont(new Font("Arial", Font.BOLD, 11));
        downloadLabel.setForeground(new Color(0, 100, 0));
        downloadLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        downloadLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        attachmentPanel.add(downloadLabel);

        filePanel.add(attachmentPanel);

        return filePanel;
    }

    /**
     * Actualiza el panel de chat cuando se recibe un mensaje que contiene un
     * archivo
     *
     * @param jsonMessage Mensaje JSON que contiene la información del archivo
     */
    public void updateChatPanelWithFile(String jsonMessage) {
        try {
            JSONObject fileObj = new JSONObject(jsonMessage);

            if (fileObj.getString("type").equals("file")) {
                String sender = fileObj.getString("sender");
                String filename = fileObj.getString("filename");
                String fileData = fileObj.getString("data");

                // Guardar el archivo en la carpeta del cliente para descarga posterior
                String saveDir = Main.getCurrentUser().getClientFolderPath() + Controller.getSeparator() + "downloads";

                // Crear directorio si no existe
                File dir = new File(saveDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Guardar el archivo
                final String filePath = saveDir + Controller.getSeparator() + filename;
                final File outputFile = new File(filePath);

                // Decodificar y guardar en un hilo separado para no bloquear la UI
                new Thread(() -> {
                    try {
                        byte[] decodedBytes = Base64.getDecoder().decode(fileData);
                        Files.write(outputFile.toPath(), decodedBytes);
                        System.out.println("Archivo guardado: " + filePath);
                    } catch (IOException e) {
                        System.err.println("Error al guardar el archivo: " + e.getMessage());
                    }
                }).start();

                // Mostrar en la UI
                SwingUtilities.invokeLater(() -> {
                    try {
                        // Obtener avatar para el remitente
                        ImageIcon avatarIcon;

                        // Si el remitente es el usuario actual, usar su avatar
                        if (sender.equals(Main.getCurrentUser().getUsername())) {
                            Image avatarImage = ImageIO.read(new File(Main.getCurrentUser().getAvatar()));
                            avatarImage = avatarImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                            avatarIcon = new ImageIcon(avatarImage);
                        } else {
                            // Para otros usuarios, intentar buscar su avatar o usar uno predeterminado
                            String otherUserAvatar = getUserAvatar(sender);
                            if (otherUserAvatar != null && new File(otherUserAvatar).exists()) {
                                Image avatarImage = ImageIO.read(new File(otherUserAvatar));
                                avatarImage = avatarImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                                avatarIcon = new ImageIcon(avatarImage);
                            } else {
                                // Usar avatar predeterminado
                                try {
                                    Image defaultImage = ImageIO.read(new File(Main.getUSER_ICON_URL()));
                                    defaultImage = defaultImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                                    avatarIcon = new ImageIcon(defaultImage);
                                } catch (IOException ex) {
                                    // Si no se puede cargar el avatar predeterminado, crear uno vacío
                                    avatarIcon = new ImageIcon();
                                }
                            }
                        }

                        // Crear panel de archivo
                        JPanel filePanel = createFilePanel(sender, filename, avatarIcon);

                        // Agregar listener para descargar
                        JLabel downloadLabel = (JLabel) ((JPanel) filePanel.getComponent(2)).getComponent(2);
                        downloadLabel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                try {
                                    File file = new File(filePath);
                                    if (file.exists()) {
                                        Desktop.getDesktop().open(file);
                                    } else {
                                        MensajeDialog.showMessageDialog(APP,
                                                "El archivo aún se está descargando. Intente nuevamente en unos segundos.", "Información");
                                    }
                                } catch (IOException ex) {
                                    MensajeDialog.showMessageDialog(APP,
                                            "Error al abrir el archivo: " + ex.getMessage(), "Error");
                                }
                            }
                        });

                        // Añadir a la interfaz
                        APP.getjPanelMensajes().add(filePanel);
                        APP.getjPanelMensajes().revalidate();
                        APP.getjPanelMensajes().repaint();

                        // Scroll al último mensaje
                        /*
                        JScrollPane scrollPane = APP.getjScrollPaneChat();
                        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                        verticalBar.setValue(verticalBar.getMaximum());
                         */
                    } catch (IOException e) {
                        MensajeDialog.showMessageDialog(APP, "Error al cargar el avatar: " + e.getMessage(), "Error");
                    }
                });
            }
        } catch (JSONException e) {
            System.err.println("Error al procesar mensaje JSON: " + e.getMessage());
        }
    }

    public String getUserAvatar(String username) {
        // Si no se encuentra, intentar cargar desde archivo
        String userFile = Main.getCLIENTS_FOLDER_PATH() + Controller.getSeparator() + username + Controller.getSeparator() + "profile.json";
        File file = new File(userFile);

        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONObject userObj = new JSONObject(content);
                if (userObj.has("avatar")) {
                    return userObj.getString("avatar");
                }
            } catch (IOException | JSONException e) {
                System.err.println("Error al leer el avatar del usuario: " + e.getMessage());
            }
        }

        // Si todo falla, retornar null
        return null;
    }

    /**
     * Método para determinar si un mensaje es un archivo o texto regular
     *
     * @param message Mensaje a procesar
     */
    // Add this to PanelsController.processMessage()
    public void processMessage(String message) {
        if (message.trim().startsWith("{") && message.trim().endsWith("}")) {
            try {
                JSONObject jsonObj = new JSONObject(message);
                if (jsonObj.has("type") && jsonObj.getString("type").equals("file")) {

                    updateChatPanelWithFile(message);
                    System.out.println("\nArchivo enviado." + "\n");
                    return;
                }
            } catch (JSONException e) {
                System.err.println("\nJSON parsing error: " + e.getMessage() + "\n");
            }
        }

        updateChatPanel(message);
        System.out.println("\nMensaje de texto enviado." + "\n");
    }

    public void showMessagesOnServer() {
        try {
            String message;
            while ((message = Main.getCurrentUser().getIn().readLine()) != null) {  // Leer línea a línea
                System.out.println("Servidor: " + message);  // Mostrar mensajes del servidor

                // Procesar el mensaje (puede ser texto o archivo)
                processMessage(message);
            }
        } catch (IOException e) {
            System.out.println("Conexión cerrada: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado. Intente nuevamente más tarde: " + e.getMessage());
        } finally {
            Main.getCurrentUser().closeResources();  // Asegurar el cierre de la conexión
        }
    }

    private ImageIcon loadAvatarForUser(String username) {
        try {
            // Si es el usuario actual
            if (username.equals(Main.getCurrentUser().getUsername())) {
                return loadAvatarIcon(Main.getCurrentUser().getAvatar());
            }

            // Para otros usuarios
            String avatarPath = getUserAvatar(username);
            if (avatarPath != null && !avatarPath.isEmpty()) {
                return loadAvatarIcon(avatarPath);
            }

            // Avatar predeterminado si no se encuentra
            return getDefaultAvatarIcon();
        } catch (Exception e) {
            System.err.println("Error cargando avatar para " + username + ": " + e.getMessage());
            return getDefaultAvatarIcon();
        }
    }

    private ImageIcon loadAvatarIcon(String avatarPath) throws IOException {
        if (avatarPath == null || avatarPath.isEmpty() || avatarPath.equals(Client.DEFAULT_AVATAR_ID)) {
            return getDefaultAvatarIcon();
        }

        File avatarFile = new File(avatarPath);
        if (!avatarFile.exists()) {
            return getDefaultAvatarIcon();
        }

        Image avatarImage = ImageIO.read(avatarFile);
        if (avatarImage == null) {
            return getDefaultAvatarIcon();
        }

        avatarImage = avatarImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(avatarImage);
    }

    private ImageIcon getDefaultAvatarIcon() {
        try {
            // Cargar desde recursos si está en JAR
            InputStream is = getClass().getResourceAsStream("/images/default_avatar.jpg");
            if (is != null) {
                Image defaultImage = ImageIO.read(is);
                defaultImage = defaultImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(defaultImage);
            }

            // Intentar cargar desde ruta externa
            File defaultFile = new File(Main.getUSER_ICON_URL());
            if (defaultFile.exists()) {
                Image defaultImage = ImageIO.read(defaultFile);
                defaultImage = defaultImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(defaultImage);
            }
        } catch (IOException e) {
            System.err.println("Error cargando avatar predeterminado: " + e.getMessage());
        }

        // Crear icono vacío como último recurso
        return new ImageIcon();
    }
}
