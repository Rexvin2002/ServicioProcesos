package wordchanger;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordChanger {

    /*
     * -----------------------------------------------------------------------
     * MAIN
     * -----------------------------------------------------------------------
     */
    public static void main(String[] args) {

        String text = "En un mundo dominado por los gatos el perro es el mejor aliado ya que los gatos son felinos mientras que los perros caninos. "
                + "Los gatos y los perros son dos de las mascotas más populares del mundo. Ambos tienen sus ventajas y desventajas, y la "
                + "elección entre ellos depende de las preferencias y el estilo de vida de cada persona. Algunos aspectos a considerar son: "
                + "- **Personalidad**: Los gatos suelen ser más independientes y tranquilos que los perros, que son más sociables y activos. "
                + "Los gatos pueden pasar más tiempo solos y se adaptan mejor a los espacios pequeños, mientras que los perros necesitan "
                + "más atención y ejercicio, y se llevan mejor con otros animales y personas. Los gatos también son más curiosos y "
                + "juguetones, mientras que los perros son más leales y obedientes. "
                + "- **Cuidados**: Los gatos y los perros requieren diferentes tipos de cuidados. Los gatos se limpian solos y solo necesitan "
                + "un arenero, un cepillado ocasional y una visita al veterinario cada año. Los perros, en cambio, necesitan baños regulares, "
                + "cortes de pelo, vacunas, desparasitación y revisiones frecuentes. Además, los perros tienen que salir a pasear varias veces "
                + "al día, lo que implica un mayor compromiso y responsabilidad por parte de sus dueños. "
                + "- **Costes**: Tener una mascota implica un gasto económico que hay que tener en cuenta. Los gatos y los perros tienen "
                + "costes similares en cuanto a alimentación, accesorios y juguetes, pero los perros suelen ser más caros en cuanto a "
                + "servicios veterinarios, higiene y adiestramiento. Según un estudio, el coste medio de tener un gato durante su vida es de "
                + "unos 15.000 euros, mientras que el de tener un perro es de unos 25.000 euros. "
                + "En conclusión, los gatos y los perros son animales muy diferentes que ofrecen distintas experiencias a sus dueños. No hay "
                + "una respuesta única a la pregunta de cuál es mejor, sino que depende de las características y necesidades de cada persona y de cada animal. "
                + "Lo importante es elegir con responsabilidad y ofrecerles el mejor cuidado y cariño posible.";

        // Patrón de búsqueda y reemplazo
        String palabras = "\\bgato\\b|\\bgatos\\b|\\bfelinos\\b|\\bperro\\b|\\bperros\\b|\\bcaninos\\b";
        String[] replacements = {"perro", "perros", "gato", "gatos"};

        // Crear un patrón y un matcher
        Pattern patron = Pattern.compile(palabras);
        Matcher emparejador = patron.matcher(text);

        // StringBuffer para almacenar el resultado modificado
        StringBuffer result = new StringBuffer();

        // Reemplazo de palabras usando Matcher
        while (emparejador.find()) {

            // Obtener el texto coincidente
            String matchedWord = emparejador.group();

            // Determinar el índice de la palabra coincidente y su reemplazo
            int index = -1;
            switch (matchedWord) {
                case "gato" ->
                    index = 0; // Reemplazar con "perro"
                case "gatos" ->
                    index = 1; // Reemplazar con "perros"
                case "felinos" ->
                    index = 1; // Reemplazar con "perros"
                case "perro" ->
                    index = 2; // Reemplazar con "gato"
                case "perros" ->
                    index = 3; // Reemplazar con "gatos"
                case "caninos" ->
                    index = 3; // Reemplazar con "gatos"
            }

            // Reemplazar la palabra en el StringBuffer
            if (index != -1) {
                emparejador.appendReplacement(result, replacements[index]);
            }

        }

        emparejador.appendTail(result); // Agregar el resto del texto

        // Imprimir el texto modificado
        System.out.println(result.toString());

    }

}
