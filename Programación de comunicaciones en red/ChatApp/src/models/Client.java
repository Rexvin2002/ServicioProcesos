package models;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import controllers.Controller;
import controllers.UserController;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import main.Main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {

    private final UserController UC;

    private String serverIP;
    private int port;
    private Map<String, List<ServerMessage>> serverList = new HashMap<>();

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String username;
    private char[] passwd;
    private String avatar;
    private String clientFolderPath;


    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public Client(String username, char[] passwd, String avatar) {

        this.UC = Main.getUc();

        this.username = username;
        this.passwd = passwd;
        this.avatar = avatar;
        this.clientFolderPath = UserController.getCLIENTS_FOLDER_PATH() + "\\" + username;

    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        Controller.configurarUTF8Encoding();

        // Crear servidor de prueba
        String serverIP = "localhost";
        int port = 12345;

        Server server = new Server(serverIP, port);

        // Iniciar servidor en un hilo separado
        new Thread(() -> {
            server.start();
        }).start();

        // Mensaje para indicar que el servidor está listo
        System.out.println("Servidor iniciado en " + serverIP + ":" + port);
        System.out.println("Esperando conexiones de clientes...");

        // Crear un cliente de prueba
        String username = "usuario_prueba";
        char[] password = "passwd".toCharArray();
        String avatar = "src\\img\\userIcon.png";

        Client client = new Client(username, password, avatar);

        // Configurar IP y puerto (usar localhost para pruebas)
        client.serverIP = "localhost";
        client.port = 12345;

        // Crear carpeta del cliente
        System.out.println("⌛ Intentando crear carpeta del cliente...");
        client.createClientFolder();
        File clientFolder = new File(client.getClientFolderPath());

        if (clientFolder.exists()) {
            System.out.println("✅ Carpeta del cliente creada exitosamente");

        } else {
            System.out.println("❌ Error al crear la carpeta del cliente");
        }

        // Conectar al servidor
        System.out.println("⌛ Intentando conectar al servidor...");
        client.connect();

        if (client.isConnected()) {
            System.out.println("✅ Conexión al servidor establecida exitosamente");

        } else {
            System.out.println("❌ Error al conectar con el servidor");
        }

        // Guardar datos del cliente
        System.out.println("⌛ Guardando datos del cliente...");
        client.saveClientData();
        File clientDataFile = new File(client.getClientFolderPath() + "\\" + UserController.getCLIENT_JSON_NAME());

        if (clientDataFile.exists()) {
            System.out.println("✅ Datos del cliente guardados exitosamente");

        } else {
            System.out.println("❌ Error al guardar los datos del cliente");
        }

        // Simular envío de mensaje
        System.out.println("⌛ Enviando mensaje de prueba...");
        client.sendMessage("Hola, este es un mensaje de prueba!");
        System.out.println("✅ Mensaje enviado (sin confirmación de recepción)");

        // Simular envío de archivo
        String filePath = "src\\lib\\archivo.txt"; // Cambia esto
        System.out.println("⌛ Enviando archivo de prueba...");
        boolean fileSent = client.sendFile(filePath);

        if (fileSent) {
            System.out.println("✅ Archivo enviado exitosamente al servidor");

        } else {
            System.out.println("❌ Error al enviar el archivo");
        }

        // Cerrar conexión
        System.out.println("⌛ Cerrando conexión...");
        client.close();

        if (!client.isConnected()) {
            System.out.println("✅ Conexión cerrada exitosamente");

        } else {
            System.out.println("❌ Error al cerrar la conexión");
        }

        System.out.println("✔✔ Todas las operaciones completadas ✔✔");

    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public void connect() {

        try {

            socket = new Socket(serverIP, port);  // Conectar al servidor
            out = new PrintWriter(socket.getOutputStream(), true);  // Salida para enviar mensajes
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Entrada para leer mensajes
            out.println("\n" + username + " se ha unido al chat");  // Enviar nombre de usuario y avatar

        } catch (IOException e) {
            System.err.println("\nError al conectar al servidor: " + e.getMessage());
            closeResources();  // Cerrar recursos si la conexión falla
        }

    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public void close() {

        try {

            if (socket != null) {

                socket.close();

            }

        } catch (IOException e) {
            System.err.println("Error al encontrar socket: " + e.getMessage());
        }

    }

    public void createClientFolder() {

        File clientFolder = new File(clientFolderPath);

        // Verificar si la carpeta ya existe o crearla si no existe
        if (!clientFolder.exists()) {

            boolean created = clientFolder.mkdirs();

            if (created) {
                System.out.println("\nCarpeta del cliente creada: " + clientFolderPath);

            } else {
                System.err.println("No se pudo crear la carpeta del cliente: " + clientFolderPath);
            }

        } else {
            System.out.println("La carpeta del cliente ya existe: " + clientFolderPath);
        }

    }

    public void saveClientData() {

        if (avatar == null || avatar.isEmpty()) {

            System.out.println("El cliente no tiene un avatar definido.");
            return;

        }

        try {

            JSONObject clientData = new JSONObject();
            // Datos básicos del cliente
            clientData.put("username", username);
            clientData.put("password", new String(passwd));

            // Verificar y cargar el avatar
            String avatarBase64;
            if (avatar.startsWith("jar:")) {

                // Es la imagen predeterminada dentro del JAR
                avatarBase64 = UserController.getUSER_ICON_URL();

            } else {

                // Es un archivo en el sistema de archivos
                File avatarFile = new File(avatar);
                System.out.println("""
                                   
                                   SAVED CLIENT DATA
                                   Username: """ + username
                        + "\nPassword: " + Arrays.toString(passwd)
                        + "\nRuta del avatar: " + avatar
                        + "\nArchivo Avatar: " + avatarFile);

                if (!avatarFile.exists() || !avatarFile.isFile()) {

                    System.err.println("\nEl archivo de avatar no existe o no es un archivo válido.");
                    return;

                }

                // Convertir el archivo a Base64
                byte[] avatarBytes = Files.readAllBytes(avatarFile.toPath());
                avatarBase64 = Base64.getEncoder().encodeToString(avatarBytes);

            }

            clientData.put("avatar", avatarBase64);
            System.out.println("Avatar codificado en Base64: " + avatarBase64);

            // Convertir mensajes a formato JSON
            JSONObject serversJSON = new JSONObject();
            for (Map.Entry<String, List<ServerMessage>> entry : serverList.entrySet()) {

                JSONArray serversArray = new JSONArray();

                for (ServerMessage srv : entry.getValue()) {

                    JSONObject mensajeJson = new JSONObject();
                    mensajeJson.put("username", srv.getUSERNAME());
                    mensajeJson.put("servers", srv.getMESSAGE());
                    serversArray.put(mensajeJson);

                }

                serversJSON.put(entry.getKey(), serversArray);

            }

            clientData.put("servers", serversJSON);

            // Guardar el JSON
            File jsonFile = new File(clientFolderPath + "/" + UserController.getCLIENT_JSON_NAME());
            System.out.println("JSON Creado");

            try (FileWriter writer = new FileWriter(jsonFile)) {

                writer.write(clientData.toString(4));
                System.out.println("\nDatos del cliente guardados exitosamente en JSON\n");

            } catch (JSONException e) {
                System.err.println("Error inesperado al guardar los datos del cliente: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error al guardar los datos del cliente: " + e.getMessage());
        }

    }

    public void loadClientData() {

        File jsonFile = new File(clientFolderPath + "/" + UserController.getCLIENT_JSON_NAME());

        if (jsonFile.exists()) {

            try (InputStream inputStream = Files.newInputStream(jsonFile.toPath())) {

                // Leer el archivo JSON
                String content = new String(inputStream.readAllBytes());
                JSONObject clientData = new JSONObject(content);

                // Cargar los datos básicos del cliente
                username = clientData.getString("username");
                passwd = clientData.getString("password").toCharArray();  // Convertir a char[] si es necesario

                // Cargar el avatar
                if (clientData.has("avatar")) {
                    String avatarData = clientData.getString("avatar");

                    if (UserController.getUSER_ICON_URL().equals(avatarData)) {

                        // Es la imagen predeterminada dentro del JAR
                        avatar = UserController.getUSER_ICON_URL();
                        System.out.println("Avatar predeterminado cargado.");

                    } else {

                        // Es un archivo en Base64
                        byte[] avatarBytes = Base64.getDecoder().decode(avatarData);
                        File tempAvatarFile = new File(clientFolderPath + "/" + UserController.getAVATAR_FILE_NAME());
                        Files.write(tempAvatarFile.toPath(), avatarBytes);
                        avatar = tempAvatarFile.getAbsolutePath();  // Actualizar la variable avatar

                    }

                }

                // Cargar los servidores
                JSONObject serversJSON = clientData.getJSONObject("servers");
                serverList.clear();  // Limpiar la lista de servidores antes de cargarla

                for (String serverName : serversJSON.keySet()) {

                    JSONArray serverArray = serversJSON.getJSONArray(serverName);
                    List<ServerMessage> messages = new ArrayList<>();

                    for (int i = 0; i < serverArray.length(); i++) {

                        JSONObject messageJson = serverArray.getJSONObject(i);
                        String username2 = messageJson.getString("username");
                        String message = messageJson.getString("servers");

                        // Crear un nuevo objeto ServerMessage y añadirlo a la lista
                        messages.add(new ServerMessage(username2, message));

                    }

                    // Añadir la lista de mensajes al mapa de servidores
                    serverList.put(serverName, messages);

                }

            } catch (IOException | JSONException e) {
                System.err.println("Error al cargar los datos del cliente desde JSON: " + e.getMessage());
            }

        } else {
            System.err.println("El archivo JSON no existe.");
        }

    }

    public void sendMessage(String messageToSend) {

        if (UserController.getCurrentUser() != null && messageToSend != null && !messageToSend.isEmpty()) {

            if (out != null) {
                out.println(messageToSend);  // Enviar mensaje al servidor
            }

        }

    }

    public boolean sendFile(String filePath) {

        try {

            File file = new File(filePath);

            if (!file.exists() || !file.isFile()) {

                System.err.println("El archivo no existe o no es válido.");
                return false;

            }

            // Leer el archivo como bytes
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            // Convertir a Base64 para enviar como texto
            String encodedFile = Base64.getEncoder().encodeToString(fileBytes);

            // Crear objeto JSON con la información del archivo
            JSONObject fileMessage = new JSONObject();
            fileMessage.put("type", "file");
            fileMessage.put("filename", file.getName());
            fileMessage.put("sender", username);
            fileMessage.put("data", encodedFile);
            fileMessage.put("timestamp", System.currentTimeMillis());

            // Enviar el mensaje JSON al servidor
            if (isConnected() && out != null) {

                out.println(fileMessage.toString());
                return true;

            } else {

                System.err.println("\nNo hay conexión con el servidor.");
                return false;

            }

        } catch (IOException e) {
            System.err.println("\nError al leer el archivo: " + e.getMessage());
            return false;

        } catch (JSONException e) {
            System.err.println("\nError al crear el mensaje JSON: " + e.getMessage());
            return false;

        } catch (Exception e) {
            System.err.println("\nError inesperado al enviar el archivo: " + e.getMessage());
            return false;

        }

    }

    public void closeResources() {

        try {

            if (in != null) {
                in.close();
            }

            if (out != null) {
                out.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            System.err.println("Error al cerrar los recursos: " + e.getMessage());

        }

    }

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public UserController getUC() {
        return UC;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<String, List<ServerMessage>> getServerList() {
        return serverList;
    }

    public void setServerList(Map<String, List<ServerMessage>> serverList) {
        this.serverList = serverList;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPasswd() {
        return passwd;
    }

    public String getPasswdAsString() {
        return new String(passwd);
    }

    public void setPasswd(char[] passwd) {
        this.passwd = passwd;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getClientFolderPath() {
        return clientFolderPath;
    }

    public void setClientFolderPath(String username) {
        this.clientFolderPath = UserController.getCLIENTS_FOLDER_PATH() + "/" + username;
    }

    /*
     * -----------------------------------------------------------------------
     * TOSTRING
     * -----------------------------------------------------------------------
     */
    @Override
    public String toString() {
        return "Cliente{ username=" + username + ", clientFolderPath=" + clientFolderPath + ", avatar=" + avatar + ", serverIP=" + serverIP + ", port=" + port + '}';
    }

    /*
     * -----------------------------------------------------------------------
     * HASHCODE
     * -----------------------------------------------------------------------
     */
    @Override
    public int hashCode() {
        int hash = 3;
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
        final Client other = (Client) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.serverIP, other.serverIP)) {
            return false;
        }
        if (!Objects.equals(this.clientFolderPath, other.clientFolderPath)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.avatar, other.avatar)) {
            return false;
        }
        if (!Objects.equals(this.serverList, other.serverList)) {
            return false;
        }
        if (!Objects.equals(this.socket, other.socket)) {
            return false;
        }
        if (!Objects.equals(this.out, other.out)) {
            return false;
        }
        if (!Objects.equals(this.in, other.in)) {
            return false;
        }
        return Arrays.equals(this.passwd, other.passwd);
    }

}
