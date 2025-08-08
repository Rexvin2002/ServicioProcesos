package dnivalidator;

public class DNIValidator {

    public static void main(String[] args) {
        String regex = "\\b[1-9][0-9]{7}[-_][A-Z]\\b(\\s*,\\s*[1-9][0-9]{7}[-_][A-Z])*";

        String[] inputs = {"48134118-V", 
            "12345678-A", 
            "48134118-V , 48134118-V", 
            "12345678Z,87654321-B", 
            "12345678-A,87654321B", 
            "12345678A,87654321B",
            "12345678A87654321B",
            "12345678-A87654321B",
            "12345678A87654321-B",
            "12345678-A87654321-B",
            "12345678-A,87654321-B, 87654321-C",
            "12345678-A, 87654321-B, 87654321-C",
            "12345678-A,87654321-B, 87654321-C",
            "12345678A,87654321-B, 87654321-C",
            "12345678-A,87654321B, 87654321-C",
            "12345678-A,87654321-B, 87654321C",
            "12345678A,87654321B, 87654321-C",
            "12345678-A,87654321B, 87654321-C"
        };

        for (String input : inputs) {
            if (input.matches(regex)) {
                if (areDNIsValid(input)) {
                    System.out.println("Entrada " + input + " --------- VALIDA.");
                } else {
                    System.out.println("Entrada " + input + " --------- NO VALIDA (letra incorrecta).");
                }
            } else {
                System.out.println("Entrada " + input + " --------- NO VALIDA.");
            }
        }
    }

    // Verifica si la letra del DNI es la correcta
    private static boolean areDNIsValid(String input) {
        String[] dnis = input.split("\\s*,\\s*");
        for (String dni : dnis) {
            String[] parts = dni.split("[-_]");
            if (parts.length != 2) {
                return false;
            }
            String numberPart = parts[0];
            char letterPart = parts[1].charAt(0);

            if (!isDniLetterCorrect(numberPart, letterPart)) {
                return false;
            }
        }
        return true;
    }

    // Comprueba si la letra es la correspondiente para el número del DNI
    private static boolean isDniLetterCorrect(String numberPart, char letterPart) {
        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        int dniNumber = Integer.parseInt(numberPart);
        char correctLetter = letters.charAt(dniNumber % 23);
        return correctLetter == letterPart;
    }
}
