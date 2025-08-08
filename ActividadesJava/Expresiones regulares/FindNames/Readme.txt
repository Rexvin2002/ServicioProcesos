# Buscador de Nombres Propios

## Descripción
Programa Java que identifica nombres propios en cadenas de texto usando expresiones regulares. Detecta palabras que:
- Comienzan con letra mayúscula
- Continúan con letras minúsculas (incluyendo vocales acentuadas en español)
- Respetan los límites de palabra

## Autor
Kevin Gómez Valderas (2ºDAM)

## Características principales
- Detecta nombres propios en cadenas separadas por comas
- Usa límites de palabra (`\b`) para coincidencias exactas
- Soporta caracteres acentuados del español (áéíóú)
- Muestra cada nombre encontrado que cumple el patrón

## Patrón de Expresión Regular
```java
"\\b[A-Z][a-záéíóú]*\\b"
```

### Explicación:
- `\\b` - Límite de palabra
- `[A-Z]` - Letra mayúscula inicial
- `[a-záéíóú]*` - Cero o más letras minúsculas (incluye acentos)
- `\\b` - Límite de palabra

## Ejemplo de uso
**Entrada:**
```java
"Manuel, Samuel, Martín"
```

**Salida:**
```
El texto Manuel cumple con el patrón.
El texto Samuel cumple con el patrón.
El texto Martín cumple con el patrón.
```

## Instrucciones de uso
1. Compilar: `javac findnames/FindNames.java`
2. Ejecutar: `java findnames.FindNames`

## Personalización
Para analizar diferente texto:
1. Modificar la variable `nombres` en el método principal
2. Recompilar y ejecutar

## Caracteres soportados
- Letras mayúsculas: A-Z
- Letras minúsculas: a-z incluyendo áéíóú
- Los límites de palabra garantizan coincidencias exactas

## Limitaciones
- Solo detecta nombres de una sola palabra
- No maneja nombres con apóstrofes o guiones
- No verifica contra un diccionario de nombres reales
- No distingue entre nombres propios y otras palabras que cumplan el patrón

## Requisitos
- Java 8 o superior
- No se requieren librerías externas

## Historial de versiones
1.0 - Versión inicial con detección básica de nombres