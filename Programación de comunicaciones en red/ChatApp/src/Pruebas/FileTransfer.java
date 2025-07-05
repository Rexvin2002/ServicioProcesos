package Pruebas;

/**
 *
 * @author kgv17
 */
import java.io.*;
import java.net.*;

public class FileTransfer {

    private static final int PORT = 5000;
    private static final String FILE_PATH = "C:\\Users\\kgv17\\OneDrive\\Escritorio\\archivo.txt";

    public static void main(String[] args) {
        // Iniciar el servidor en un hilo separado
        Thread serverThread = new Thread(() -> startServer());
        serverThread.start();

        // Pequeña pausa para asegurar que el servidor esté listo antes de que el cliente se conecte
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("Error in sleep: " + e.getMessage());
        }

        // Iniciar el cliente en otro hilo
        Thread clientThread = new Thread(() -> startClient(FILE_PATH));
        clientThread.start();
    }

    // Método para iniciar el servidor
    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT + "...");

            while (true) {
                try (Socket socket = serverSocket.accept(); 
                        DataInputStream dis = new DataInputStream(socket.getInputStream()); 
                        FileOutputStream fos = new FileOutputStream(dis.readUTF())) {

                    System.out.println("Receiving file...");

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = dis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }

                    System.out.println("File received successfully!");
                } catch (IOException e) {
                    System.err.println("Error receiving file: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    // Método para iniciar el cliente
    public static void startClient(String filePath) {
        String serverAddress = "127.0.0.1"; // Dirección del servidor

        try (Socket socket = new Socket(serverAddress, PORT); FileInputStream fis = new FileInputStream(filePath); DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

            // Enviar el nombre del archivo al servidor
            dos.writeUTF(new File(filePath).getName());

            System.out.println("Sending file...");

            // Enviar el contenido del archivo
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully!");
        } catch (IOException e) {
            System.err.println("Error sending file: " + e.getMessage());
        }
    }
}
