# Validador Básico de Expresiones Regulares

## Descripción
Programa Java sencillo que demuestra el uso básico de expresiones regulares. Valida si cadenas de entrada contienen exactamente 3 caracteres, donde cada carácter debe ser 'a', 'b' o 'c' (solo minúsculas).

## Autor
Kevin Gómez Valderas (2ºDAM)

## Patrón Regex
```java
"[abc]{3}"
```

### Explicación del Patrón:
- `[abc]`: Coincide con cualquier carácter que sea 'a', 'b' o 'c'
- `{3}`: Especifica exactamente 3 repeticiones del patrón anterior
- `.matches()`: Requiere que toda la cadena coincida con el patrón

## Entradas Válidas
- Cualquier combinación de 3 caracteres usando solo 'a', 'b' o 'c'
- Ejemplos: "abc", "bca", "aaa", "ccc"

## Entradas Inválidas
- Cadenas con más o menos de 3 caracteres
- Cadenas que contengan caracteres distintos a a/b/c
- Ejemplos: "abcd", "ab", "def", "ABC"

## Características del Programa
- Casos de prueba predefinidos que muestran patrones válidos e inválidos
- Salida clara en consola indicando los resultados de validación
- Demostración simple de las clases Pattern y Matcher

## Ejemplo de Salida
```
El texto 'abc' cumple con el patrón.
El texto 'bca' cumple con el patrón. 
El texto 'abcd' no cumple con el patrón.
El texto 'def' no cumple con el patrón.
```

## Instrucciones de Uso
1. Compilar: `javac regexvalidator/RegexValidator.java`
2. Ejecutar: `java regexvalidator.RegexValidator`

## Personalización
Para probar diferentes patrones:
1. Modificar la variable 'pattern' en main()
2. Actualizar el array 'texts' con nuevos casos de prueba
3. Recompilar y ejecutar

## Requisitos
- Java 8 o superior
- No se requieren librerías externas

## Valor Educativo
- Demuestra clases básicas de caracteres en regex
- Muestra coincidencia exacta de longitud con el cuantificador {n}
- Ilustra la coincidencia completa de cadenas con .matches()

## Historial de Versiones
1.0 - Versión inicial con validación básica de [abc]{3}