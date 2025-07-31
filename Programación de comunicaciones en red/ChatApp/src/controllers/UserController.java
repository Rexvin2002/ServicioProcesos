package controllers;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
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
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Main;
import models.Client;
import models.ServerMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ui.ChatApp;
import ui.MensajeDialog;

public class UserController {

    private Controller CTRLR;
    private ChatApp APP;
    private PanelsController PC;

    private static final String USER_ICON_URL = "src\\img\\userIcon.png";
    private static final String CLIENTS_FOLDER_PATH = "src\\clients";
    private static final String CLIENT_JSON_NAME = "client_data.json";
    private static final String AVATAR_FILE_NAME = "avatar.jpg";

    private static String avatarPathSelected;
    private static Client currentUser;

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public UserController(ChatApp APP) {

        this.CTRLR = new Controller();
        this.APP = APP;
        this.PC = APP.getPc();

    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public void createAvatar() {

        // Crear el FileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        // Filtro para solo permitir imágenes
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg, *.gif)", "png", "jpg", "jpeg", "gif");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(APP);

        if (seleccion == JFileChooser.APPROVE_OPTION) {

            // Obtener el path seleccionado
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();

            avatarPathSelected = imagePath;

            CTRLR.escalarEstablecerImagenFromString(APP.getjLabelImagenCrearCliente(), avatarPathSelected);

        }

    }

    public void createUser() {

        // Validaciones de campos
        if (APP.getjTextFieldCrearNombre().getText().trim().isEmpty()
                || APP.getjPasswordFieldCrearContraseña().getPassword().length == 0
                || APP.getjPasswordFieldConfirmarContraseña().getPassword().length == 0) {
            MensajeDialog.showMessageDialog(APP, "Complete todos los campos y seleccione un avatar.", "Alerta");
            return;
        }

        // Validar si ya existe un cliente con el mismo nombre
        String nombre = APP.getjTextFieldCrearNombre().getText().trim();

        if (existUser(nombre)) {
            MensajeDialog.showMessageDialog(APP, "Ya existe un cliente con el mismo nombre.", "Alerta");
            return;
        }

        if (APP.getjPasswordFieldCrearContraseña().getPassword().length < 6) {
            MensajeDialog.showMessageDialog(APP, "La contraseña debe tener al menos 6 caracteres.", "Alerta");
            return;
        }

        if (!Arrays.equals(APP.getjPasswordFieldCrearContraseña().getPassword(), APP.getjPasswordFieldConfirmarContraseña().getPassword())) {
            MensajeDialog.showMessageDialog(APP, "Las contraseñas no coinciden.", "Alerta");
            return;
        }

        if (avatarPathSelected == null || avatarPathSelected.isBlank()) {
            avatarPathSelected = USER_ICON_URL;
            CTRLR.escalarEstablecerImagenFromString(APP.getjLabelImagenCrearCliente(), USER_ICON_URL);
        }

        char[] contraseña = APP.getjPasswordFieldCrearContraseña().getPassword();

        System.out.println("avatarPathSelected: " + avatarPathSelected);

        Client cliente = new Client(nombre, contraseña, avatarPathSelected);

        currentUser = cliente;
        cliente.createClientFolder();
        cliente.saveClientData();

        System.out.println("\nCuenta creada exitosamente.\n");
        MensajeDialog.showMessageDialog(APP, "¡Cuenta creada exitosamente!", "Información");

        APP.cambiarLayout("cardInicioSesion");

        // Limpiar campos
        APP.getjTextFieldCrearNombre().setText("");
        APP.getjPasswordFieldCrearContraseña().setText("");
        APP.getjPasswordFieldConfirmarContraseña().setText("");
        APP.getjLabelImagenCrearCliente().setIcon(null);

    }

    public void accesUser() {

        String usuarioIntroducido = APP.getjTextFieldNombreInicioSesion().getText();

        if (usuarioIntroducido.isEmpty()) {
            MensajeDialog.showMessageDialog(APP, "Por favor, complete todos los campos.", "Alerta");
            return;
        }

        if (!existUser(usuarioIntroducido)) {
            MensajeDialog.showMessageDialog(APP, "Usuario no encontrado", "Alerta");
            return;
        }

        char[] contraseñaUsuario = currentUser.getPasswd();
        char[] contraseñaIntroducida = APP.getjPasswordFieldContraseñaInicioSesion().getPassword();

        if (contraseñaIntroducida.length == 0) {

            MensajeDialog.showMessageDialog(APP, "Por favor, complete todos los campos.", "Alerta");

        } else if (!Arrays.equals(contraseñaIntroducida, contraseñaUsuario)) {

            MensajeDialog.showMessageDialog(APP, "Contraseña incorrecta.", "Alerta");

        } else {

            setProfile();
            APP.cambiarLayout("cardInicio");

        }

        // Limpiar la contraseña ingresada por seguridad
        Arrays.fill(contraseñaIntroducida, '\0');
    }

    private void setProfile() {

        currentUser.loadClientData();
        // Actualizar la interfaz de usuario
        PC.updateServerPanels();
        CTRLR.escalarEstablecerImagenFromString(APP.getjLabelImagenPerfilCliente(), currentUser.getAvatar());
        APP.getjTextFieldNombrePerfil().setText(currentUser.getUsername());
        APP.getjPasswordFieldContraseñaPerfil().setText(currentUser.getPasswdAsString());
        APP.getjPasswordFieldConfirmarContraseñaPerfil().setText(currentUser.getPasswdAsString());

    }

    public boolean existUser(String user) {

        File jsonFile = new File(CLIENTS_FOLDER_PATH + "/" + user + "/" + CLIENT_JSON_NAME);

        if (!jsonFile.exists()) {
            return false;
        }

        try {

            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            if (clientData.has("username") && clientData.getString("username").equalsIgnoreCase(user)) {

                currentUser = new Client(clientData.getString("username"),
                        clientData.getString("password").toCharArray(),
                        avatarPathSelected);
                return true;

            }

        } catch (IOException | JSONException e) {
            MensajeDialog.showMessageDialog(APP, "Error al acceder a los datos del usuario: " + e.getMessage(), "Error");
        }

        return false;

    }

    public void changeAvatar() {

        // Crear el FileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        // Filtro para solo permitir imágenes
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (*.png, *.jpg, *.jpeg, *.gif)", "png", "jpg", "jpeg", "gif");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(APP);

        if (seleccion == JFileChooser.APPROVE_OPTION) {

            // Obtener el path seleccionado
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Guardar el path en una variable (puedes usar una variable de clase si es necesario)
            currentUser.setAvatar(imagePath);
            currentUser.createClientFolder();
            currentUser.saveClientData();

            CTRLR.escalarEstablecerImagenFromString(APP.getjLabelImagenCrearCliente(), currentUser.getAvatar());
            CTRLR.escalarEstablecerImagenFromString(APP.getjLabelImagenPerfilCliente(), currentUser.getAvatar());

        }

    }

    public void editProfile() {

        if (APP.getjButtonEditarPerfil().getText().equals("Editar")) {

            APP.getjButtonEditarPerfil().setText("Aceptar");
            APP.getjTextFieldNombrePerfil().setEnabled(true);
            APP.getjPasswordFieldContraseñaPerfil().setEnabled(true);
            APP.getjPasswordFieldConfirmarContraseñaPerfil().setEnabled(true);

        } else {

            APP.getjButtonEditarPerfil().setText("Editar");
            APP.getjTextFieldNombrePerfil().setEnabled(false);
            APP.getjPasswordFieldContraseñaPerfil().setEnabled(false);
            APP.getjPasswordFieldConfirmarContraseñaPerfil().setEnabled(false);

            // Validaciones de campos
            if (APP.getjTextFieldNombrePerfil().getText().trim().isEmpty()
                    || APP.getjPasswordFieldContraseñaPerfil().getPassword().length == 0
                    || APP.getjPasswordFieldConfirmarContraseñaPerfil().getPassword().length == 0
                    || APP.getjLabelImagenPerfilCliente().getIcon() == null
                    || currentUser.getAvatar() == null || currentUser.getAvatar().isBlank()) {
                MensajeDialog.showMessageDialog(APP, "Complete todos los campos y seleccione un avatar.", "Alerta");
                return;
            }

            if (!Arrays.equals(APP.getjPasswordFieldContraseñaPerfil().getPassword(), APP.getjPasswordFieldConfirmarContraseñaPerfil().getPassword())) {
                MensajeDialog.showMessageDialog(APP, "Las contraseñas no coinciden.", "Alerta");
                return;
            }

            if (APP.getjPasswordFieldContraseñaPerfil().getPassword().length < 6) {
                MensajeDialog.showMessageDialog(APP, "La contraseña debe tener al menos 6 caracteres.", "Alerta");
                return;
            }

            try {

                String nombre = APP.getjTextFieldNombrePerfil().getText().trim();
                char[] passwd = APP.getjPasswordFieldContraseñaPerfil().getPassword();

                // Verificar si el nombre de usuario ya está en uso
                if (existUser(nombre)) {
                    MensajeDialog.showMessageDialog(APP, "Ya existe un cliente con el mismo nombre.", "Alerta");
                    return;
                }

                // Asignar los valores al cliente logeado
                currentUser.setUsername(nombre);
                currentUser.setPasswd(passwd);

                System.out.println("username: " + currentUser.getUsername());
                currentUser.setClientFolderPath(nombre);
                System.out.println("ClientFolderPath: " + currentUser.getClientFolderPath());
                currentUser.createClientFolder();

                try {

                    String avatarPath = currentUser.getAvatar();
                    Path source = Paths.get(avatarPath);
                    Path oldFolder = source.getParent(); // Carpeta actual donde está el avatar
                    Path newFolder = Paths.get(currentUser.getClientFolderPath()); // Nuevo nombre de carpeta

                    if (Files.exists(oldFolder) && Files.isDirectory(oldFolder)) {

                        // Renombrar la carpeta
                        Files.move(oldFolder, newFolder, StandardCopyOption.REPLACE_EXISTING);

                        // Actualizar la ruta del avatar con la nueva ubicación
                        Path newAvatarPath = newFolder.resolve(source.getFileName());
                        currentUser.setAvatar(newAvatarPath.toString());

                        System.out.println("Carpeta y cliente renombrado");

                    } else {
                        System.err.println("La carpeta del avatar no existe.");
                    }

                } catch (IOException e) {
                    MensajeDialog.showMessageDialog(APP, "Error al renombrar la carpeta del avatar.", "Error");
                }

                System.out.println("Avatar: " + currentUser.getAvatar());
                currentUser.saveClientData();

                // Mensaje de éxito o acción adicional después de editar el perfil
                MensajeDialog.showMessageDialog(APP, "Perfil editado correctamente.", "Éxito");

            } catch (NumberFormatException e) {
                MensajeDialog.showMessageDialog(APP, "El número de teléfono debe ser un valor numérico.", "Error");
            }

        }

    }

    public void connectUserToServer() {

        if (currentUser.isConnected()) {

            MensajeDialog.showMessageDialog(APP, "Ya estás conectado al servidor.", "Advertencia");

            APP.getjLabelServidorPuerto().setText("Servidor: " + Main.getServerIP() + " | Puerto: " + Main.getPort());

            APP.cambiarLayout("cardChat");

            return;  // Salir del método si ya está conectado

        }

        Main.setServerIP(APP.getjTextFieldServerIP().getText());  // Obtener IP desde el campo de texto
        String puertoText = APP.getjTextFieldPuerto().getText(); // Obtener puerto como String

        if (Main.getServerIP().isEmpty() || puertoText.isEmpty()) {
            MensajeDialog.showMessageDialog(APP, "Por favor, ingresa una IP y puerto válidos.", "Advertencia");
            return;
        }

        try {
            Main.setPort(Integer.parseInt(puertoText));  // Convertir el puerto a entero
        } catch (NumberFormatException e) {
            MensajeDialog.showMessageDialog(APP, "Puerto inválido. Debe ser un número.", "Error");
            return;
        }

        currentUser.setPort(Main.getPort());
        currentUser.setServerIP(Main.getServerIP());

        try {

            currentUser.connect();  // Conectar al servidor

            if (currentUser.isConnected()) {

                MensajeDialog.showMessageDialog(APP, "Conectado al servidor en " + Main.getServerIP() + ":" + Main.getPort(), "Información");
                APP.getjLabelServidorPuerto().setText(currentUser.getUsername() + ": " + Main.getServerIP() + " | Puerto: " + Main.getPort());

                APP.cambiarLayout("cardChat");

                // Agregar mensaje de conexión al chat
                String message = Main.getServerIP() + ":" + Main.getPort();
                String currentUserName = currentUser.getUsername();

                // Evitar mostrar el mensaje duplicado si el servidor lo reenvía
                if (!existServer(message)) {
                    PC.updateServersPanel(currentUserName + ": " + message);
                }

                currentUser.sendMessage(currentUser.getUsername() + ": Conectado a " + currentUser.getServerIP() + ":" + currentUser.getPort());

            }

        } catch (Exception e) {
            // Mostrar error detallado
            MensajeDialog.showMessageDialog(APP, "Error al conectar: " + e.getMessage() + "\n" + "Por favor, verifica la IP y el puerto.", "Error");
        }

    }

    public boolean existServer(String serverAddress) {

        // Obtener los datos del archivo JSON
        File jsonFile = new File(currentUser.getClientFolderPath() + "/" + CLIENT_JSON_NAME);

        if (!jsonFile.exists()) {
            MensajeDialog.showMessageDialog(APP, "El archivo de datos del cliente no existe.", "Error");
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
            MensajeDialog.showMessageDialog(APP, "Error al comprobar los servidores: " + e.getMessage(), "Error");
        }

        return false; // No se encontró el servidor

    }

    public void saveClientsServers() {

        try {

            // Cargar los datos existentes del archivo JSON
            File jsonFile = new File(currentUser.getClientFolderPath() + "/" + CLIENT_JSON_NAME);
            if (!jsonFile.exists()) {
                MensajeDialog.showMessageDialog(APP, "El archivo de datos del cliente no existe.", "Error");
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
            for (Map.Entry<String, List<ServerMessage>> entry : currentUser.getServerList().entrySet()) {

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
            MensajeDialog.showMessageDialog(APP, "Error al guardar los datos de los servidores: " + e.getMessage(), "Error");

        } catch (JSONException e) {
            System.err.println("Error inesperado: " + e.getMessage());
            MensajeDialog.showMessageDialog(APP, "Error inesperado al guardar los datos de los servidores.", "Error");

        }

    }

    public String getUserAvatar(String username) {
        // Si no se encuentra, intentar cargar desde archivo
        String userFile = CLIENTS_FOLDER_PATH + "/" + username + "/" + "profile.json";
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

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public static String getUSER_ICON_URL() {
        return USER_ICON_URL;
    }

    public static String getCLIENTS_FOLDER_PATH() {
        return CLIENTS_FOLDER_PATH;
    }

    public static String getCLIENT_JSON_NAME() {
        return CLIENT_JSON_NAME;
    }

    public static String getAVATAR_FILE_NAME() {
        return AVATAR_FILE_NAME;
    }

    public Controller getCTRLR() {
        return CTRLR;
    }

    public void setCTRLR(Controller CTRLR) {
        this.CTRLR = CTRLR;
    }

    public ChatApp getAPP() {
        return APP;
    }

    public void setAPP(ChatApp APP) {
        this.APP = APP;
    }

    public PanelsController getPC() {
        return PC;
    }

    public void setPC(PanelsController PC) {
        this.PC = PC;
    }

    public static String getAvatarPathSelected() {
        return avatarPathSelected;
    }

    public static void setAvatarPathSelected(String avatarPathSelected) {
        UserController.avatarPathSelected = avatarPathSelected;
    }

    public static Client getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Client currentUser) {
        UserController.currentUser = currentUser;
    }

    /*
     * -----------------------------------------------------------------------
     * TOSTRING
     * -----------------------------------------------------------------------
     */
    @Override
    public String toString() {
        return "UserController{" + "CTRLR=" + CTRLR + ", APP=" + APP + ", PC=" + PC + '}';
    }

    /*
     * -----------------------------------------------------------------------
     * HASHCODE
     * -----------------------------------------------------------------------
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.CTRLR);
        hash = 19 * hash + Objects.hashCode(this.APP);
        hash = 19 * hash + Objects.hashCode(this.PC);
        return hash;
    }

    /*
     * -----------------------------------------------------------------------
     * EQUALS
     * -----------------------------------------------------------------------
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserController other = (UserController) obj;
        if (!Objects.equals(this.CTRLR, other.CTRLR)) {
            return false;
        }
        if (!Objects.equals(this.APP, other.APP)) {
            return false;
        }
        return Objects.equals(this.PC, other.PC);
    }

}
