
package Unidad01.ExpresionesRegulares;

/**
 *
 * @author kgv17
 */
public class PasswdUserExtractor {
    public static void main(String[] args) {
        String regex = "^" 
                + "([a-zA-Z][a-zA-Z0-9_.-]{1,31}):" // Nombre de usuario: 2 a 32 caracteres
                + "([^!\"#%&':{}]*):" // Contraseña: puede incluir cualquier carácter excepto los prohibidos
                + "([0-9]{1,4}):" // UID: número de 1 a 4 dígitos
                + "([0-9]{1,4}):" // GID: número de 1 a 4 dígitos
                + "([^:]*):" // Descripción: cualquier carácter (excepto ":")
                + "([^:]*):" // Directorio Home: cualquier carácter (excepto ":")
                + "([^:]+)$"; // Shell: al menos un carácter (sin ":")

        // Conjunto de entradas para probar
        String[] inputs = {
            "usuario:x:1000:200:/description:/home/user:/bin/bash", // Válido
            "us:x:1000:200:/description:", // Válido (usuario corto)
            "usuario:password:1000:200:/description:/home/user:/bin/bash", // Válido
            "us:pwd:9999:9999:/desc:/home/user:/bin/bash", // Válido (números al máximo)
            "u:pwd:9999:9999:/desc:/home/user:/bin/bash", // Válido (usuario mínimo)
            "usuario:!invalid:1000:200:/description:/home/user:/bin/bash", // Inválido (contraseña inválida)
            "usuario:x:10000:200:/description:/home/user:/bin/bash", // Inválido (UID demasiado largo)
            "usuario:x:1000:200:/desc:/home/user:/bin:/bash:/extra", // Inválido (demasiados campos)
            "usuario:x:1000:200:/desc:/home/user:/bin:>bash", // Inválido (carácter prohibido en shell)
            "usuario_:x:1000:200:/description:/home/user:/bin/bash", // Inválido (subrayado al inicio)
            "45usuario:x:1000:200:/description:/home/user:/bin/bash", // Inválido (comienza con número)
            "usuario:x:-1:200:/description:/home/user:/bin/bash" // Inválido (UID negativo)
        };

        // Probar cada entrada
        for (String input : inputs) {
            if (input.matches(regex)) {
                System.out.println("Entrada válida: " + input);
            } else {
                System.out.println("Entrada inválida: " + input);
            }
        }
    }
}

