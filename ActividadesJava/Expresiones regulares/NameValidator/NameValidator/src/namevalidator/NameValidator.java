package namevalidator;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
public class NameValidator {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String regex = "[A-ZÁÉÍÓÚÑa-záéíóúñ0-9]{1,}([ -][A-ZÁÉÍÓÚÑa-záéíóúñ0-9]{1,})?\\s*,\\s*" // apellido1
                + "[A-ZÁÉÍÓÚÑa-záéíóúñ]{1,}" // nombre
                + "(\\s*-\\s*" // guion
                + "([A-ZÁÉÍÓÚÑa-záéíóúñ0-9]{1,}([ -][A-ZÁÉÍÓÚÑa-záéíóúñ0-9]{1,})?\\s*,\\s*" // apellido1N
                + "[A-ZÁÉÍÓÚÑa-záéíóúñ]{1,}" // NombreN
                + ")?\\s*)*" // opcional: nombres adicionales
                + "(?:(\\d)?[A-Z])?$"; // Opcional: dígito en penúltima posición y letra mayúscula al final

        String[] inputs = {
            "Gómez Pérez, Juan",
            "Pérez, Ana - Pérez, Ana - Pérez, Ana - ApellidoN ApellidoN, NombreN",
            "Pérez, Ana - Pérez, Ana - apellido1 apellido2, nombreN",
            "Gómez, Juan",
            "López García, Juan, Pérez, María",
            "Gómez Pérez, Juan,  López García, María",
            "gómez Pérez, Juan",
            "Gómez Pérez Juan",
            "Gómez Pérez,  Juan,  López García,  María",
            "López, Juan, Pérez, María",
            "Gómez Pérez, Juan, López García, ",
            "Gómez, Juan, Pérez García, María"
        };

        for (String input : inputs) {
            System.out.println(input.matches(regex) ? "Entrada '" + input + "' --------- VALIDA." : "Entrada '" + input + "' --------- NO VALIDA.");
        }

    }

}
