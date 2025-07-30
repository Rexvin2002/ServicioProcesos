package models;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import controllers.Controller;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.Main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {

    public static final String DEFAULT_AVATAR_ID = "src/img/userIcon.png";
    private static final String CLIENT_JSON_NAME = "client_data.json";
    private static final String AVATAR_FILE_NAME = "avatar.jpg";

    private String serverIP;
    private int port;
    private Map<String, List<ServerMessage>> serverList = new HashMap<>();

    private Socket socket;
    private PrintWriter out;
    public BufferedReader in;

    private String clientFolderPath;
    private String username;
    private String avatar;
    private char[] passwd;

    public Client(String username, char[] passwd, String avatar) {
        this.username = username;
        this.avatar = avatar;
        this.passwd = passwd;
        this.clientFolderPath = Main.getCLIENTS_FOLDER_PATH() + File.separator + username;
    }

    /**
     * Conectar al servidor
     */
    public void connect() {
        try {
            socket = new Socket(serverIP, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send join message with username and avatar info
            JSONObject joinMessage = new JSONObject();
            joinMessage.put("type", "join");
            joinMessage.put("username", username);
            joinMessage.put("avatar", avatar);
            out.println(joinMessage.toString());

        } catch (IOException e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
            closeResources();
        } catch (JSONException e) {
            System.err.println("Error creating join message: " + e.getMessage());
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

    /**
     * Crear Carpeta Cliente
     */
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

    /**
     * Mensajes en Servidor
     *
     * @param messageToSend
     */
    public void sendMessage(String messageToSend) {

        if (Main.getCurrentUser() != null && messageToSend != null && !messageToSend.isEmpty()) {

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
                System.out.println("\nNo hay conexión con el servidor.");
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

    /**
     * Datos JSON
     */
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
                avatarBase64 = DEFAULT_AVATAR_ID;

            } else {
                // Es un archivo en el sistema de archivos
                File avatarFile = new File(avatar);
                System.out.println("\nSAVED CLIENT DATA\n"
                        + "Username: " + username
                        + "\nPassword: " + passwd
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
                    mensajeJson.put("username", srv.getUsername());
                    mensajeJson.put("servers", srv.getMessage());
                    serversArray.put(mensajeJson);
                }
                serversJSON.put(entry.getKey(), serversArray);
            }
            clientData.put("servers", serversJSON);

            // Guardar el JSON
            File jsonFile = new File(clientFolderPath + Controller.getSeparator() + CLIENT_JSON_NAME);
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

        File jsonFile = new File(clientFolderPath + Controller.getSeparator() + CLIENT_JSON_NAME);

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

                    if (DEFAULT_AVATAR_ID.equals(avatarData)) {
                        // Es la imagen predeterminada dentro del JAR
                        avatar = DEFAULT_AVATAR_ID;
                        System.out.println("Avatar predeterminado cargado.");
                    } else {
                        // Es un archivo en Base64
                        byte[] avatarBytes = Base64.getDecoder().decode(avatarData);
                        File tempAvatarFile = new File(clientFolderPath + Controller.getSeparator() + AVATAR_FILE_NAME);
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
                        String username = messageJson.getString("username");
                        String message = messageJson.getString("servers");

                        // Crear un nuevo objeto ServerMessage y añadirlo a la lista
                        messages.add(new ServerMessage(username, message));
                    }

                    // Añadir la lista de mensajes al mapa de servidores
                    serverList.put(serverName, messages);

                }

            } catch (IOException | JSONException e) {
                System.err.println("Error al cargar los datos del cliente desde JSON: " + e.getMessage());
            }

        } else {
            System.out.println("El archivo JSON no existe.");
        }

    }

    // Getters y Setters
    public Map<String, List<ServerMessage>> getServerList() {
        return serverList;
    }

    public void setServerList(Map<String, List<ServerMessage>> serverList) {
        this.serverList = serverList;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public char[] getPasswd() {
        return passwd;
    }

    public void setPasswd(char[] passwd) {
        this.passwd = passwd;
    }

    public String getPasswdAsString() {
        return new String(passwd);
    }

    public void setClientFolderPath(String username) {
        this.clientFolderPath = Main.getCLIENTS_FOLDER_PATH() + Controller.getSeparator() + username;
    }

    public String getClientFolderPath() {
        return clientFolderPath;
    }

    @Override
    public String toString() {
        return "Cliente{ username=" + username + ", clientFolderPath=" + clientFolderPath + ", avatar=" + avatar + ", serverIP=" + serverIP + ", port=" + port + '}';
    }
}
