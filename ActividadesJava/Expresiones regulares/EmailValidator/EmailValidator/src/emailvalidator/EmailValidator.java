package emailvalidator;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
public class EmailValidator {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String regex = "([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+" // Coincide con la parte local y el dominio del correo
                + "\\.[a-zA-Z]{2,})" // Asegura que el correo termine con un punto seguido de al menos dos letras
                + "(\\s*,\\s*" // Permite espacios en blanco alrededor de la coma
                + "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+" // Otra dirección de correo
                + "\\.[a-zA-Z]{2,})*" // Asegura que la nueva dirección de correo también termine correctamente
                + "(\\s*-\\s*" // Permite un guión rodeado de espacios en blanco
                + "([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+" // Una dirección de correo después del guión
                + "\\.[a-zA-Z]{2,})" // Asegura que esta dirección termine correctamente
                + "(\\s*,\\s*" // Permite espacios en blanco alrededor de la coma
                + "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+" // Otra dirección de correo
                + "\\.[a-zA-Z]{2,})" // Asegura que la nueva dirección de correo también termine correctamente
                + "*)*"; // Permite cero o más repeticiones de las direcciones de correo y grupos separados

        String[] inputs = {
            "email1@example.com - email2@example.com",
            "email1@example.com, email2@example.com - email3@example.com, email4@example.com",
            "email1@example.com email2@example.com",
            "email1@example.com",
            "email1@example.com, email2@example.com, email3@example.com - email4@example.com - email4@example.com"

        };

        for (String input : inputs) {
            System.out.println(input.matches(regex) ? "Entrada " + input + " --------- VALIDA." : "Entrada " + input + " --------- NO VALIDA.");
        }

    }

}
