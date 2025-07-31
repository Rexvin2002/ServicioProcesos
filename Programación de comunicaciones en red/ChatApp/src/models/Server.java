package models;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private String serverIP;
    private int port;

    private ServerSocket serverSocket;
    private final List<PrintWriter> clientWriters = new ArrayList<>();
    private final List<ClientHandler> clients = new ArrayList<>();

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public Server(String serverIP, int port) {

        this.serverIP = serverIP;
        this.port = port;

    }

    /*
     * -----------------------------------------------------------------------
     * CLIENTHANDLER
     * -----------------------------------------------------------------------
     */
    private class ClientHandler implements Runnable {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {

            this.clientSocket = socket;

            try {

                this.out = new PrintWriter(clientSocket.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                synchronized (clientWriters) {
                    clientWriters.add(out);  // Agregar escritor del cliente a la lista
                }

            } catch (IOException e) {
                System.err.println("Error al agregar escritor del cliente a la lista: " + e.getMessage());

            }

        }

        @Override
        public void run() {

            String message;
            try {

                while ((message = in.readLine()) != null) {

                    if (message.startsWith("FILE:")) {

                        // Si el mensaje indica que se va a recibir un archivo
                        receiveFile(clientSocket);

                    } else {
                        sendToAllClients(message);  // Enviar el mensaje a todos los clientes
                    }

                }

            } catch (IOException e) {
                System.err.println("Error al mostrar el mensaje del cliente o al enviar el mensaje a todos los clientes: " + e.getMessage());

            } finally {

                try {

                    clientSocket.close();  // Cerrar la conexión cuando el cliente se desconecta

                    synchronized (clientWriters) {
                        clientWriters.remove(out);  // Eliminar el escritor del cliente
                    }

                } catch (IOException e) {
                    System.err.println("Error al cerrar la conexión o al eliminar el escritor del cliente: " + e.getMessage());
                }

            }

        }

        private void receiveFile(Socket clientSocket) {

            try (DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream())) {

                // Recibir el nombre del archivo
                String fileName = dataInputStream.readUTF();

                // Recibir el tamaño del archivo
                long fileSize = dataInputStream.readLong();

                // Guardar el archivo en una carpeta temporal
                File receivedFile = new File("received_files/" + fileName);
                receivedFile.getParentFile().mkdirs();  // Crear la carpeta si no existe

                try (FileOutputStream fileOutputStream = new FileOutputStream(receivedFile)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalBytesRead = 0;

                    // Leer el archivo en bloques de bytes
                    while (totalBytesRead < fileSize
                            && (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize - totalBytesRead))) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                    }

                    System.out.println("Archivo recibido: " + fileName);

                    // Enviar el archivo a todos los clientes
                    sendFileToAllClients(receivedFile);

                } catch (IOException e) {
                    System.err.println("Error al guardar el archivo: " + e.getMessage());
                }

            } catch (IOException e) {
                System.err.println("Error al recibir el archivo: " + e.getMessage());
            }

        }

        public Socket getClientSocket() {
            return clientSocket;
        }

    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        // Crear una instancia del servidor
        Server server = new Server("localhost", 1234);

        // Iniciar el servidor en un hilo separado para no bloquear el main
        new Thread(() -> {
            System.out.println("Iniciando servidor en localhost:12345...");
            server.start();
        }).start();

        // Esperar un poco para que el servidor se inicie
        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());

        }

        // Verificar si el servidor está funcionando
        if (server.serverSocket != null && !server.serverSocket.isClosed()) {

            System.out.println("Servidor funcionando correctamente");

        } else {
            System.out.println("El servidor no se pudo iniciar (posiblemente el puerto está en uso)");
        }

        // Mantener el programa en ejecución por un tiempo
        try {

            Thread.sleep(5000);

        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());

        }

        // Cerrar el servidor
        System.out.println("Cerrando servidor...");
        server.close();
        System.out.println("Servidor cerrado");

    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    public void start() {

        try {

            // Intentar crear el socket del servidor
            try {

                serverSocket = new ServerSocket(port);

            } catch (IOException e) {

                // Si el socket ya está en uso (BindException), no hacer nada
                if (e instanceof java.net.BindException) {

                    // El puerto está en uso, no mostrar nada ni hacer nada
                    return;

                } else {

                    System.err.println("Error al crear el socket del servidor: " + e.getMessage());
                    return;

                }

            }

            // Aceptar conexiones de clientes en un bucle
            while (true) {

                try {

                    if (serverSocket.isClosed()) {
                        break; // Salir del bucle si el socket está cerrado
                    }

                    // Aceptar la conexión de un cliente
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);

                    // Agregar el cliente a la lista
                    synchronized (clients) {  // Asegurar el acceso concurrente
                        clients.add(clientHandler);
                    }

                    // Manejar el cliente en un hilo separado
                    new Thread(clientHandler).start();

                } catch (IOException e) {

                    // Solo mostrar el error si el socket sigue abierto
                    if (!serverSocket.isClosed()) {
                        System.err.println("Error al aceptar la conexión de un cliente: " + e.getMessage());
                    }

                    break; // Salir del bucle si hay un error

                }

            }

        } catch (Exception e) {
            // Error inesperado general
            System.err.println("Error inesperado: " + e.getMessage());
        }

    }

    public void connect(String selectedIP, int selectedPort) {

        // Crear una nueva instancia del servidor
        this.serverIP = selectedIP;
        this.port = selectedPort;

        // Ejecutar el servidor en un hilo separado para que no bloquee la interfaz
        new Thread(() -> {
            start();  // Iniciar el servidor en segundo plano
        }).start();

    }

    public void close() {

        try {

            if (serverSocket != null) {
                serverSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Error al encontrar el socket: " + e.getMessage());
        }

    }

    private void sendToAllClients(String message) {

        synchronized (clientWriters) {

            for (PrintWriter writer : clientWriters) {
                writer.println(message);  // Enviar mensaje a cada cliente
            }

        }

    }

    private void sendFileToAllClients(File file) {

        synchronized (clientWriters) {

            // Leer el archivo una sola vez
            byte[] fileData;

            try (FileInputStream fileInputStream = new FileInputStream(file)) {

                fileData = fileInputStream.readAllBytes(); // Leer todo el archivo en memoria

            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
                return;
            }

            // Enviar el archivo a cada cliente
            for (PrintWriter writer : clientWriters) {

                try {

                    // Obtener el socket del cliente desde el ClientHandler
                    Socket clientSocket = null;
                    synchronized (clients) {

                        for (ClientHandler client : clients) {

                            if (client.out == writer) {
                                clientSocket = client.getClientSocket();
                                break;
                            }

                        }

                    }

                    if (clientSocket == null) {

                        System.err.println("No se pudo encontrar el socket del cliente.");
                        continue;

                    }

                    // Enviar el archivo
                    try (DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream())) {

                        // Enviar el nombre del archivo
                        dataOutputStream.writeUTF(file.getName());

                        // Enviar el tamaño del archivo
                        dataOutputStream.writeLong(fileData.length);

                        // Enviar los datos del archivo
                        dataOutputStream.write(fileData);

                        System.out.println("Archivo enviado a un cliente: " + file.getName());

                    } catch (IOException e) {
                        System.err.println("Error al enviar el archivo a un cliente: " + e.getMessage());
                    }

                } catch (Exception e) {
                    System.err.println("Error inesperado al enviar el archivo: " + e.getMessage());
                }

            }

        }

    }

    /*
     * -----------------------------------------------------------------------
     * GETTERS Y SETTERS
     * -----------------------------------------------------------------------
     */
    public List<PrintWriter> getClientWriters() {
        return clientWriters;
    }

    public List<ClientHandler> getClients() {
        return clients;
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

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /*
     * -----------------------------------------------------------------------
     * TOSTRING
     * -----------------------------------------------------------------------
     */
    @Override
    public String toString() {
        return "Server{" + "serverIP=" + serverIP + ", port=" + port + ", serverSocket=" + serverSocket + ", clientWriters=" + clientWriters + ", clients=" + clients + '}';
    }

    /*
     * -----------------------------------------------------------------------
     * HASHCODE
     * -----------------------------------------------------------------------
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.serverIP);
        hash = 47 * hash + this.port;
        hash = 47 * hash + Objects.hashCode(this.serverSocket);
        hash = 47 * hash + Objects.hashCode(this.clientWriters);
        hash = 47 * hash + Objects.hashCode(this.clients);
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
        final Server other = (Server) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.serverIP, other.serverIP)) {
            return false;
        }
        if (!Objects.equals(this.serverSocket, other.serverSocket)) {
            return false;
        }
        if (!Objects.equals(this.clientWriters, other.clientWriters)) {
            return false;
        }
        return Objects.equals(this.clients, other.clients);
    }

}
