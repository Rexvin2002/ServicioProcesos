package programamultiplataforma;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FileCounter {

    private static final Scanner SCANNER = Controller.getSCANNER();
    private static final String SEPARATOR = Controller.getSEPARATOR();

    /*
     * -----------------------------------------------------------------------
     * CONSTRUCTOR
     * -----------------------------------------------------------------------
     */
    public FileCounter() {
    }

    /*
     * -----------------------------------------------------------------------
     * MÉTODOS
     * -----------------------------------------------------------------------
     */
    private static FileCounts countFilesExternal(String path, boolean recursive) throws Exception {

        String countCommand;
        String sizeCommand;
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("windows")) {

            // Comando para contar archivos
            countCommand = recursive
                    ? "dir /s /b /a-d \"" + path + "\" | find /c /v \"\""
                    : "dir /b /a-d \"" + path + "\" | find /c /v \"\"";

            // Comando para obtener tamaño total
            sizeCommand = recursive
                    ? "for /r \"" + path + "\" %i in (*) do @echo %~zi"
                    : "for %i in (\"" + path + "\\*\") do @echo %~zi";

        } else {

            // Para sistemas Unix/Linux
            countCommand = recursive
                    ? "find \"" + path + "\" -type f | wc -l"
                    : "ls -1 \"" + path + "\" | wc -l";

            sizeCommand = recursive
                    ? "find \"" + path + "\" -type f -exec ls -l {} \\; | awk '{sum += $5} END {print sum}'"
                    : "ls -l \"" + path + "\" | awk '{sum += $5} END {print sum}'";

        }

        // Obtener conteo de archivos
        Process countProcess = Runtime.getRuntime().exec(new String[]{"cmd", "/c", countCommand});
        BufferedReader countReader = new BufferedReader(new InputStreamReader(countProcess.getInputStream()));
        int fileCount = Integer.parseInt(countReader.readLine().trim());

        // Obtener tamaño total
        Process sizeProcess = Runtime.getRuntime().exec(new String[]{"cmd", "/c", sizeCommand});
        BufferedReader sizeReader = new BufferedReader(new InputStreamReader(sizeProcess.getInputStream()));
        long totalSize = 0;
        String line;

        while ((line = sizeReader.readLine()) != null) {

            try {

                totalSize += Long.parseLong(line.trim());

            } catch (NumberFormatException e) {
                System.err.println("\nError: " + e.getMessage());
                System.out.println("\n" + SEPARATOR);
            }

        }

        return new FileCounts(fileCount, totalSize);

    }

    private static FileCounts countFilesJava(File directory, boolean recursive) {

        if (!directory.exists() || !directory.isDirectory()) {
            return new FileCounts(0, 0);
        }

        AtomicInteger count = new AtomicInteger(0);
        AtomicLong size = new AtomicLong(0);
        File[] files = directory.listFiles();

        if (files != null) {

            for (File file : files) {

                if (file.isFile()) {

                    count.incrementAndGet();
                    size.addAndGet(file.length());

                } else if (recursive && file.isDirectory()) {

                    FileCounts subDirCounts = countFilesJava(file, true);
                    count.addAndGet(subDirCounts.fileCount);
                    size.addAndGet(subDirCounts.totalSize);

                }

            }

        }

        return new FileCounts(count.get(), size.get());

    }

    private static String formatSize(long bytes) {

        if (bytes < 1024) {
            return bytes + " B";
        }

        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";

        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);

    }

    private static class FileCounts {

        final int fileCount;
        final long totalSize;

        FileCounts(int fileCount, long totalSize) {

            this.fileCount = fileCount;
            this.totalSize = totalSize;

        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof FileCounts)) {
                return false;
            }

            FileCounts other = (FileCounts) obj;
            return this.fileCount == other.fileCount && this.totalSize == other.totalSize;

        }

        @Override
        public int hashCode() {

            int hash = 5;
            hash = 71 * hash + this.fileCount;
            hash = 71 * hash + (int) (this.totalSize ^ (this.totalSize >>> 32));
            return hash;

        }

    }

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        try {

            Controller.configurarUTF8Encoding();

            // Solicitar ruta del directorio con valor por defecto
            System.out.println("\nIntroduce la ruta del directorio:");
            String pathInput = SCANNER.nextLine().trim();
            String path = pathInput.isEmpty() ? ProgramaMultiplataforma.getCARPETAEJEMPLO() : pathInput;

            // Solicitar modo recursivo con valor por defecto (true)
            System.out.println("¿Quieres contar los archivos de forma recursiva?: ");
            String recursiveInput = SCANNER.nextLine().trim();
            boolean recursive = recursiveInput.isEmpty() ? true : Boolean.parseBoolean(recursiveInput);

            // Mostrar configuración que se usará
            System.out.println("Configuración seleccionada:");
            System.out.println("Directorio: " + path);
            System.out.println("Recursivo: " + recursive);

            // Conteo usando programa externo
            FileCounts externalCounts = countFilesExternal(path, recursive);
            System.out.println("\nResultados del programa externo:");
            System.out.println("Número de archivos: " + externalCounts.fileCount);
            System.out.println("Tamaño total: " + formatSize(externalCounts.totalSize) + " (" + externalCounts.totalSize + " bytes)");

            // Conteo usando Java
            FileCounts javaCounts = countFilesJava(new File(path), recursive);
            System.out.println("\nResultados de Java:");
            System.out.println("Número de archivos: " + javaCounts.fileCount);
            System.out.println("Tamaño total: " + formatSize(javaCounts.totalSize) + " (" + javaCounts.totalSize + " bytes)");

            // Comparación
            if (externalCounts.equals(javaCounts)) {

                System.out.println("\n¡Los conteos coinciden!");

            } else {

                System.out.println("\n¡Advertencia! Los conteos no coinciden.");

                if (externalCounts.fileCount != javaCounts.fileCount) {

                    System.out.println("Diferencia en número de archivos: "
                            + Math.abs(externalCounts.fileCount - javaCounts.fileCount));

                }

                if (externalCounts.totalSize != javaCounts.totalSize) {

                    System.out.println("Diferencia en tamaño: "
                            + formatSize(Math.abs(externalCounts.totalSize - javaCounts.totalSize)));

                }

            }

            System.out.println("\n" + SEPARATOR);

        } catch (Exception e) {

            System.err.println("\nError: " + e.getMessage());
            System.out.println("\n" + SEPARATOR);

        }

    }

}
