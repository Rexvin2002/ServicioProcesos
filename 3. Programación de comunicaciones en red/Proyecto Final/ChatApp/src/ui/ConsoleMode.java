package ui;

import controllers.Controller;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Scanner;
import main.Main;
import models.Client;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kgv17
 */
public class ConsoleMode {

    /**
     * Ejecutar en consola
     */
    public static void runCLI() {

        Scanner sca = new Scanner(System.in);
        boolean welcomeShown = false;  // Controla si se mostró el mensaje de bienvenida

        // Configurar encoding UTF-8
        configurarUTF8Encoding();

        // Mostrar menú inicial
        System.out.println("""
                       
               --- BIENVENIDO A CHATAPP ---

               Seleccione una opci\u00f3n:
               - 1. Iniciar sesi\u00f3n.
               - 2. Crear cuenta.
               - 3. Salir.""");

        // Bucle principal de la aplicación
        while (true) {

            if (Main.getCurrentUser() == null) {
                initialOptions(sca, welcomeShown);

            } else {
                welcomeShown = mainOptions(sca, welcomeShown);

            }

        }

    }

    /**
     * Initial Options
     */
    private static void initialOptions(Scanner sca, boolean welcomeShown) {

        welcomeShown = false;  // Resetear variable

        System.out.print("\nOpción: ");
        String opcion = sca.nextLine();

        switch (opcion) {
            case "1" ->
                login(sca);
            case "2" ->
                accountCreation(sca);
            case "3" -> {
                System.out.println("\nSaliendo...\n");
                System.exit(0);
            }
            default ->
                System.err.println("\nSeleccione una opción válida.\n");

        }

    }

    private static void login(Scanner sca) {

        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.println("\nAdvertencia: La consola no admite entrada oculta. La contraseña será visible.");

        while (true) {

            // Solicitar y validar nombre de usuario
            String usuario;
            while (true) {

                System.out.print("\nIntroduzca nombre de usuario ('0' para cancelar): ");
                usuario = sca.nextLine().trim();

                if (usuario.equals("0")) {
                    System.out.println("\nOperación cancelada.");
                    return;
                }

                if (usuario.isEmpty()) {
                    System.err.println("\nError: El nombre no puede estar vacío.\n");
                    continue;
                }

                if (!existUser(usuario)) {
                    System.err.println("\nError: Usuario no encontrado.\n");
                    continue;
                }

                break; // Usuario válido

            }

            // Solicitar y validar contraseña
            while (true) {

                System.out.print("Introduzca contraseña ('0' para cancelar): ");
                String contraseña = sca.nextLine();

                if (contraseña.equals("0")) {
                    System.out.println("\nOperación cancelada.");
                    return;
                }

                if (contraseña.isEmpty()) {
                    System.err.println("\nError: La contraseña no puede estar vacía.");
                    continue;
                }

                // Verificar contraseña sin establecer el usuario actual todavía
                if (!verifyPassword(usuario, contraseña)) {
                    System.err.println("\nError: Contraseña incorrecta.");
                    continue;
                }

                break; // Contraseña válida
            }

            // Configurar perfil y finalizar
            Main.getCurrentUser().loadClientData();
            System.out.println("\n¡Inicio de sesión exitoso!");
            break;

        }

    }

    private static void accountCreation(Scanner sca) {

        System.out.println("\n--- CREACIÓN DE CUENTA ---");
        System.out.println("Advertencia: La consola no admite entrada oculta. La contraseña será visible.\n");

        while (true) {
            // Solicitar y validar nombre de usuario
            String nombre;
            while (true) {

                System.out.print("Introduzca nombre de usuario ('0' para cancelar): ");
                nombre = sca.nextLine().trim();

                if (nombre.equals("0")) {
                    System.out.println("\nOperación cancelada.");
                    return;
                }

                if (nombre.isEmpty()) {
                    System.out.println("\nError: El nombre no puede estar vacío.\n");
                    continue;
                }

                if (existUser(nombre)) {
                    System.out.println("\nError: Ya existe un usuario con ese nombre.\n");
                    continue;
                }

                break; // Nombre válido

            }

            // Solicitar y validar contraseña
            String contraseña;
            while (true) {
                System.out.print("Introduzca contraseña (mínimo 6 caracteres) ('0' para cancelar): ");
                contraseña = sca.nextLine();

                if (contraseña.equals("0")) {
                    System.out.println("\nOperación cancelada.");
                    return;
                }

                if (contraseña.length() < 6) {
                    System.out.println("\nError: La contraseña debe tener al menos 6 caracteres.\n");
                    continue;
                }

                System.out.print("Confirme la contraseña ('0' para cancelar): ");
                String confirmacion = sca.nextLine();

                if (confirmacion.equals("0")) {
                    System.out.println("\nOperación cancelada.");
                    return;
                }

                if (!contraseña.equals(confirmacion)) {
                    System.out.println("\nError: Las contraseñas no coinciden.\n");
                    continue;
                }

                break; // Contraseña válida
            }

            // Solicitar URL del avatar
            System.out.print("Introduzca la URL del avatar (opcional, presione Enter para omitir, '0' para cancelar): ");
            String avatarURL = sca.nextLine().trim();

            if (avatarURL.equals("0")) {
                System.out.println("\nOperación cancelada.");
                return;
            }

            if (avatarURL.isEmpty()) {
                avatarURL = Main.getUSER_ICON_URL();
                System.out.println("Usando avatar por defecto: " + avatarURL);
            }

            // Crear el usuario (convertimos la contraseña a char[] para el constructor)
            Client cliente = new Client(nombre, contraseña.toCharArray(), avatarURL);

            // Guardar y configurar el usuario
            Main.setCurrentUser(cliente);
            cliente.createClientFolder();
            cliente.saveClientData();

            // Limpiar datos sensibles (aunque ya no están en uso)
            contraseña = ""; // No hay forma perfecta de limpiar Strings en Java

            System.out.println("\n¡Cuenta creada exitosamente!");
            break;

        }

    }

    /**
     * Main Options
     */
    private static boolean mainOptions(Scanner sca, boolean welcomeShown) {

        // Mostrar mensaje de bienvenida solo la primera vez
        if (!welcomeShown) {
            System.out.println("\n--- BIENVENIDO, " + Main.getCurrentUser().getUsername() + "---");
            welcomeShown = true;
        }

        // Mostrar menú para usuarios autenticados
        System.out.println("""
                           
                       Seleccione una opci\u00f3n:
                       - 1. Conectar servidor.
                       - 2. Conectar cliente.
                       - 3. Servidores recientes.
                       - 4. Ver perfil.
                       - 5. Editar perfil.
                       - 6. Cerrar sesi\u00f3n.
                       - 7. Salir.""");

        System.out.print("\nOpción: ");
        String opcion = sca.nextLine();

        switch (opcion) {
            case "1" ->
                connectToServer(sca);
            case "2" ->
                connectUserToServer(sca);
            case "3" ->
                showPreviousServers(sca);
            case "4" ->
                viewProfile(sca);
            case "5" ->
                editProfile(sca);
            case "6" ->
                logout(sca);
            case "7" -> {
                System.out.println("\nSaliendo...\n");
                System.exit(0);
            }
            default ->
                System.err.println("\nSeleccione una opción válida.");
        }

        return welcomeShown;

    }

    private static void connectToServer(Scanner sca) {

        // Verificar si hay un usuario conectado
        if (Main.getCurrentUser() == null) {
            login(sca);
            if (Main.getCurrentUser() == null) {
                System.out.println("\nDebe iniciar sesión primero.");
                return;
            }
        }

        // Mostrar opción de ver servidores anteriores
        System.out.println("\n¿Desea ver servidores a los que se ha conectado antes? (s/n)");
        String verServidores = sca.nextLine().trim();

        if (verServidores.equalsIgnoreCase("s")) {

            if (!showPreviousServers(sca)) {
                System.out.println("\nNo hay servidores guardados o no se seleccionó ninguno.");

            } else {
                return; // Si se seleccionó un servidor, salir

            }

        }

        while (true) {

            String serverIP;
            String portInput;

            try {
                System.out.print("\nIntroduzca la IP del servidor (o 'salir' para cancelar): ");
                serverIP = sca.nextLine().trim();

                if ("salir".equalsIgnoreCase(serverIP)) {
                    return;
                }

                System.out.print("Introduzca el puerto del servidor: ");
                portInput = sca.nextLine().trim();
                int port = Integer.parseInt(portInput);

                // Validaciones básicas
                if (port <= 0 || port > 65535) {
                    System.err.println("\nPuerto inválido. Debe estar entre 1 y 65535.");
                    continue;
                }

                Main.setServerIP(serverIP);
                Main.setPort(port);

                try {
                    Main.getServer().connect(Main.getServerIP(), Main.getPort());
                    System.out.println("\n✅ Servidor iniciado en " + serverIP + ":" + port);

                    // Guardar el servidor en el historial
                    saveServerToHistory(serverIP, port);
                    return;

                } catch (Exception e) {
                    System.err.println("\nError al conectar: " + e.getMessage() + "\n");
                }

                System.out.println("\nNo se pudo conectar al servidor. ¿Desea intentar con otra IP/puerto? (s/n)");
                String respuesta = sca.nextLine();
                if (!respuesta.equalsIgnoreCase("s")) {
                    return;
                }

            } catch (NumberFormatException e) {
                System.out.println("\nError: El puerto debe ser un número válido.\n");

            } catch (Exception e) {
                System.err.println("\nError inesperado: " + e.getMessage());
                System.err.println("¿Desea intentarlo de nuevo? (s/n)");
                String respuesta = sca.nextLine();
                if (!respuesta.equalsIgnoreCase("s")) {
                    return;
                }

            }

        }

    }

    private static boolean showPreviousServers(Scanner sca) {

        try {

            // Leer el archivo JSON del usuario
            File jsonFile = new File(Main.getCurrentUser().getClientFolderPath()
                    + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());
            if (!jsonFile.exists()) {
                return false;
            }

            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            // Obtener la lista de servidores
            JSONObject servers = clientData.optJSONObject("servers");
            if (servers == null || servers.length() == 0) {
                System.out.println("\nNo hay servidores guardados.");
                return false;
            }

            // Mostrar lista de servidores
            System.out.println("\n--- SERVIDORES CONECTADOS RECIENTEMENTE ---");
            int index = 1;
            String[] serverKeys = servers.keySet().toArray(new String[0]);

            for (String key : serverKeys) {

                JSONArray serverArray = servers.getJSONArray(key);

                for (int i = 0; i < serverArray.length(); i++) {

                    JSONObject server = serverArray.getJSONObject(i);
                    String serverAddress = server.getString("servers");
                    System.out.println(index + ". " + serverAddress);
                    index++;

                }

            }

            // Permitir al usuario seleccionar un servidor
            System.out.print("\nSeleccione un servidor (0 para cancelar): ");
            String selection = sca.nextLine().trim();

            try {

                int selectedIndex = Integer.parseInt(selection);
                if (selectedIndex == 0) {
                    return false;
                }

                if (selectedIndex > 0 && selectedIndex < index) {
                    // Reconstruir la lista para encontrar el servidor seleccionado
                    index = 1;

                    for (String key : serverKeys) {

                        JSONArray serverArray = servers.getJSONArray(key);

                        for (int i = 0; i < serverArray.length(); i++) {

                            if (index == selectedIndex) {
                                JSONObject server = serverArray.getJSONObject(i);
                                String serverAddress = server.getString("servers");

                                // Extraer IP y puerto
                                String[] parts = serverAddress.split(":");

                                if (parts.length == 2) {
                                    Main.setServerIP(parts[0]);
                                    Main.setPort(Integer.parseInt(parts[1]));

                                    // Intentar conectar
                                    Main.getServer().connect(Main.getServerIP(), Main.getPort());
                                    System.out.println("\n✅ Conectado al servidor " + serverAddress);
                                    return true;

                                }

                            }
                            index++;

                        }

                    }

                }

            } catch (NumberFormatException e) {
                System.out.println("\nEntrada inválida.");
                return false;

            } catch (JSONException e) {
                System.err.println("\nError al conectar: " + e.getMessage());
                return false;
            }

        } catch (IOException | JSONException e) {
            System.err.println("\nError al leer los servidores guardados: " + e.getMessage());
        }

        return false;

    }

    private static void saveServerToHistory(String serverIP, int port) {

        try {
            // Leer el archivo JSON actual
            File jsonFile = new File(Main.getCurrentUser().getClientFolderPath()
                    + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());
            JSONObject clientData;

            if (jsonFile.exists()) {
                String content = new String(Files.readAllBytes(jsonFile.toPath()));
                clientData = new JSONObject(content);

            } else {
                clientData = new JSONObject();
            }

            // Obtener o crear la sección de servidores
            JSONObject servers;
            if (clientData.has("servers")) {
                servers = clientData.getJSONObject("servers");

            } else {
                servers = new JSONObject();
                clientData.put("servers", servers);

            }

            // Crear entrada para este servidor
            String serverKey = "server_" + System.currentTimeMillis();
            JSONArray serverArray = new JSONArray();
            JSONObject serverEntry = new JSONObject();
            serverEntry.put("servers", serverIP + ":" + port);
            serverEntry.put("last_connection", System.currentTimeMillis());
            serverArray.put(serverEntry);

            servers.put(serverKey, serverArray);

            // Guardar los cambios
            Files.write(jsonFile.toPath(), clientData.toString().getBytes());

        } catch (IOException | JSONException e) {
            System.err.println("\nError al guardar el servidor en el historial: " + e.getMessage());
        }

    }

    public static void connectUserToServer(Scanner sca) {

        // Verificar si el usuario ya está conectado
        if (Main.getCurrentUser().isConnected()) {
            System.out.println("\nAdvertencia: Ya estás conectado al servidor.");
            return;
        }

        // Primero intentar conectar con la configuración existente (si existe)
        if (Main.getServerIP() != null && Main.getPort() > 0) {

            try {

                Main.getCurrentUser().setServerIP(Main.getServerIP());
                Main.getCurrentUser().setPort(Main.getPort());
                Main.getCurrentUser().connect();

                if (Main.getCurrentUser().isConnected()) {
                    System.out.println("\n✅ Usuario conectado al servidor " + Main.getServerIP() + ":" + Main.getPort());
                    String connectionMsg = Main.getCurrentUser().isConnected()
                            ? Main.getCurrentUser().getUsername() + ": Conectado a " + Main.getServerIP() + ":" + Main.getPort()
                            : Main.getCurrentUser().getUsername() + ": Error al conectar a " + Main.getServerIP() + ":" + Main.getPort();

                    // Enviar mensaje al servidor
                    Main.getCurrentUser().sendMessage(connectionMsg);
                    showConnectionMenu(sca);
                    return;

                }

            } catch (Exception e) {
                System.err.println("\nLa conexión automática falló: " + e.getMessage());
            }

        }

        // Si no hay configuración o falló la conexión automática, pedir datos manualmente
        while (!Main.getCurrentUser().isConnected()) {

            connectToServer(sca);
            Main.getCurrentUser().setServerIP(Main.getServerIP());
            Main.getCurrentUser().setPort(Main.getPort());
            Main.getCurrentUser().connect();

            // Si connectToServer() devuelve, verificar si la conexión fue exitosa
            if (Main.getCurrentUser().isConnected()) {

                System.out.println("\n✅ Usuario conectado al servidor " + Main.getServerIP() + ":" + Main.getPort());
                String connectionMsg = Main.getCurrentUser().isConnected()
                        ? Main.getCurrentUser().getUsername() + ": Conectado a " + Main.getServerIP() + ":" + Main.getPort()
                        : Main.getCurrentUser().getUsername() + ": Error al conectar a " + Main.getServerIP() + ":" + Main.getPort();

                // Enviar mensaje al servidor
                Main.getCurrentUser().sendMessage(connectionMsg);
                showConnectionMenu(sca);

            } else {
                System.out.println("\n¿Desea intentar conectarse de nuevo? (s/n)");
                String respuesta = sca.nextLine();

                if (!respuesta.equalsIgnoreCase("s")) {
                    return;
                }

            }

        }

    }

    private static void viewProfile(Scanner sca) {

        // Aquí puedes agregar la lógica para ver el perfil
        System.out.println("\n--- PERFIL ---\n");

        System.out.println("Avatar: " + Main.getCurrentUser().getAvatar());
        System.out.println("Usuario: " + Main.getCurrentUser().getUsername());
        System.out.println("Contraseña: " + Main.getCurrentUser().getPasswdAsString());

    }

    public static void editProfile(Scanner sca) {

        // Solicitar nuevo nombre de usuario (con bucle para validar que no exista)
        String newUsername;

        do {
            System.out.println("\nIntroduzca el nuevo nombre de usuario:");
            newUsername = sca.nextLine().trim();

            if (existUser(newUsername)) {
                System.out.println("\nAlerta: Ya existe un cliente con el mismo nombre. Por favor, elija otro.");
            }

            if (newUsername.isEmpty()) {
                System.out.println("\nAlerta: El nombre de usuario no puede estar vacío.");
            }

        } while (newUsername.isEmpty() || existUser(newUsername));

        // Solicitar nueva contraseña
        char[] newPasswd;
        char[] confirmPasswd;

        do {

            System.out.println("Introduzca la nueva contraseña:");
            newPasswd = sca.nextLine().toCharArray();

            System.out.println("Confirme la nueva contraseña:");
            confirmPasswd = sca.nextLine().toCharArray();

            // Validaciones de campos
            if (newPasswd.length == 0 || confirmPasswd.length == 0) {
                System.out.println("\nAlerta: Complete todos los campos.");
                continue;
            }

            if (!Arrays.equals(newPasswd, confirmPasswd)) {
                System.out.println("\nAlerta: Las contraseñas no coinciden.");
                continue;
            }

            if (newPasswd.length < 6) {
                System.out.println("\nAlerta: La contraseña debe tener al menos 6 caracteres.");
                continue;
            }

            break; // Salir del bucle si todo está correcto

        } while (true);

        // Asignar los valores al cliente logeado
        Main.getCurrentUser().setUsername(newUsername);
        Main.getCurrentUser().setPasswd(newPasswd);

        System.out.println("\nNombre de usuario actualizado: " + Main.getCurrentUser().getUsername());
        Main.getCurrentUser().setClientFolderPath(newUsername);
        System.out.println("Ruta de la carpeta del cliente actualizada: " + Main.getCurrentUser().getClientFolderPath());
        Main.getCurrentUser().createClientFolder();

        // Actualizar la ruta del avatar si es necesario
        try {

            String avatarPath = Main.getCurrentUser().getAvatar();

            if (avatarPath != null && !avatarPath.isBlank()) {
                Path source = Paths.get(avatarPath);
                Path oldFolder = source.getParent(); // Carpeta actual donde está el avatar
                Path newFolder = Paths.get(Main.getCurrentUser().getClientFolderPath()); // Nuevo nombre de carpeta

                if (Files.exists(oldFolder) && Files.isDirectory(oldFolder)) {
                    // Renombrar la carpeta
                    Files.move(oldFolder, newFolder, StandardCopyOption.REPLACE_EXISTING);

                    // Actualizar la ruta del avatar con la nueva ubicación
                    Path newAvatarPath = newFolder.resolve(source.getFileName());
                    Main.getCurrentUser().setAvatar(newAvatarPath.toString());

                    System.out.println("Carpeta y cliente renombrados.");

                } else {
                    System.out.println("La carpeta del avatar no existe.");

                }

            }

        } catch (IOException e) {
            System.out.println("\nError: No se pudo renombrar la carpeta del avatar.");
        }

        // Guardar los datos actualizados del cliente
        Main.getCurrentUser().saveClientData();
        System.out.println("\nPerfil editado correctamente.");

    }

    private static void logout(Scanner sca) {

        // Desconectar el servidor
        if (Main.getServer() != null) {
            Main.getServer().close();
        }

        if (Main.getCurrentUser() != null) {
            Main.getCurrentUser().close();
            Main.setCurrentUser(null);
        }

        // Aquí puedes agregar la lógica para cerrar la sesión
        System.out.println("Sesión cerrada.");

    }

    /**
     * Connection Options
     */
    private static void showConnectionMenu(Scanner sca) {

        if (Main.getCurrentUser() == null) {

            System.out.println("\n🔒 Sesión no iniciada. Redirigiendo al login...");
            login(sca);

            if (Main.getCurrentUser() == null) {
                return;

            }

        }

        while (true) {

            System.out.println("\nSeleccione una opción:"
                    + "\n- 1. Enviar mensaje."
                    + "\n- 2. Enviar archivo."
                    + "\n- 3. Volver al menú principal."
                    + "\n- 4. Salir.");

            System.out.print("\nOpción: ");

            String opcion = sca.nextLine();

            switch (opcion) {
                case "1" ->
                    sendMessage(sca);
                case "2" ->
                    sendFile(sca);
                case "3" -> {
                    return;
                } // Regresar al menú principal
                case "4" -> {
                    System.out.println("👋 Saliendo...");
                    System.exit(0);
                }
                default ->
                    System.err.println("\n ⚠️ Opción no válida.");
            }

        }

    }

    private static void sendMessage(Scanner sca) {

        if (Main.getCurrentUser() == null) {

            System.out.println("\n❌ Error: Debes iniciar sesión primero.");
            login(sca); // Redirigir al login

            if (Main.getCurrentUser() == null) {
                return; // Si sigue sin usuario, salir
            }

        }

        System.out.print("\nIntroduzca el mensaje:");
        String message = sca.nextLine();

        String messageToSend = Main.getCurrentUser().getUsername() + ": " + message;
        Main.getCurrentUser().sendMessage(messageToSend);

        System.out.println("\n📩 Mensaje enviado: " + message);

    }

    private static void sendFile(Scanner sca) {

        // Verificar si el usuario ha iniciado sesión
        if (Main.getCurrentUser() == null) {

            System.out.println("\n❌ Error: Debes iniciar sesión primero.");
            login(sca);

            if (Main.getCurrentUser() == null) {
                return;  // Salir si aún no ha iniciado sesión
            }

        }

        System.out.print("\nIntroduzca la ruta del archivo: ");
        String filePath = sca.nextLine().trim();

        // Verificar si el archivo existe
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.err.println("\n❌ Error: El archivo no existe o la ruta es incorrecta.");
            return;
        }

        // Intentar enviar el archivo
        boolean success = Main.getCurrentUser().sendFile(filePath);

        if (success) {
            System.out.println("\n📁 Archivo enviado correctamente: " + filePath);

        } else {
            System.err.println("\n❌ Error al enviar el archivo: " + filePath);
        }

    }

    /**
     * Other Methods
     */
    private static void configurarUTF8Encoding() {

        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setProperty("file.encoding", "UTF-8");

        } catch (UnsupportedEncodingException e) {
            System.err.println("Error: " + e);

        }

    }

    private static boolean verifyPassword(String username, String password) {

        File jsonFile = new File(Main.getCLIENTS_FOLDER_PATH() + Controller.getSeparator() + username + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());

        try {

            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            if (clientData.getString("password").equals(password)) {
                // Solo ahora establecemos el usuario actual
                Main.setCurrentUser(new Client(username,
                        password.toCharArray(),
                        clientData.optString("avatar", Main.getUSER_ICON_URL())));
                return true;
            }

        } catch (IOException | JSONException e) {
            System.err.println("\nError al verificar contraseña: " + e.getMessage() + "\n");
        }

        return false;

    }

    private static boolean existUser(String user) {

        File jsonFile = new File(Main.getCLIENTS_FOLDER_PATH() + Controller.getSeparator() + user + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());

        if (!jsonFile.exists()) {
            return false;
        }

        try {

            String content = new String(Files.readAllBytes(jsonFile.toPath()));
            JSONObject clientData = new JSONObject(content);

            if (clientData.has("username") && clientData.getString("username").equalsIgnoreCase(user)) {
                // Solo verificar la existencia, no establecer el usuario actual aquí
                return true;
            }

        } catch (IOException | JSONException e) {
            System.err.println("\nError al acceder a los datos del usuario: " + e.getMessage() + "\n");
        }

        return false;

    }

    public static boolean existServer(String serverAddress) {

        // Obtener los datos del archivo JSON
        File jsonFile = new File(Main.getCurrentUser().getClientFolderPath()
                + Controller.getSeparator() + Main.getCLIENT_JSON_NAME());
        if (!jsonFile.exists()) {
            System.out.println("\nError: El archivo de datos del cliente no existe.\n");
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

                for (int i = 0; i < serverArray.length(); i++) {

                    JSONObject server = serverArray.getJSONObject(i);
                    String serverAddressInJson = server.getString("servers");

                    if (serverAddressInJson.equals(serverAddress)) {
                        return true;
                    }

                }

            }

        } catch (IOException | JSONException e) {
            System.out.println("\nError al comprobar los servidores: " + e.getMessage() + "\n");
        }

        return false;

    }

    /**
     * Main
     * @param args
     */
    public static void main(String args[]) {
        ConsoleMode.runCLI(); // Ejecutar en modo CLI
    }

}
