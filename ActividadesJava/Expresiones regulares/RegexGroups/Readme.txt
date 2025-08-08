# Colección de Ejemplos de Expresiones Regulares (Regex)

## Descripción:
------------
Esta colección de programas Java demuestra diversas técnicas de expresiones regulares (regex) incluyendo:
- Coincidencia con referencias inversas (backreferences)
- Formateo de números telefónicos
- Extracción de contenido entre comillas simples
- División de texto alrededor de números

## Autor:
-------
Kevin Gómez Valderas (2ºDAM)

## Programas incluidos:
-----------------

1. **BackReference.java**
   - Detecta y analiza palabras consecutivas repetidas en texto
   - Características:
     * Usa delimitadores de palabra (\b)
     * Captura grupos y posiciones
     * Muestra coincidencia completa vs grupo capturado
   - Salida de ejemplo:
     === PALABRAS REPETIDAS ===
     Coincidencia encontrada: 'está está'
     Grupo 0 (match completo): 'está está'
     Grupo 1 (palabra repetida): 'está'
     Posiciones: [12-20]

2. **PhoneNumberFormatter.java**
   - Formatea números telefónicos de 10 dígitos al patrón (XXX) XXX-XXXX
   - Características:
     * Grupos de captura nombrados (?<nombre>patrón)
     * Reemplazo con referencias a grupos (${nombre})
     * Procesamiento por lotes de múltiples números
   - Salida de ejemplo:
     Texto: 1234567890 and 9876543210
     Texto Formateado: (123) 456-7890 and (987) 654-3210

3. **SingleQuoteExtractor.java**
   - Extrae contenido entre comillas simples de cadenas
   - Características:
     * Coincidencia no codiciosa (.+?)
     * Extracción de grupos
     * Maneja múltiples coincidencias
   - Salida de ejemplo para "['Cadena1', 'Cadena2', 'Cadena3']":
     0 ==> 'Cadena1'
     1 ==> Cadena1
     0 ==> 'Cadena2'
     1 ==> Cadena2
     0 ==> 'Cadena3'
     1 ==> Cadena3

4. **TextNumberSplitter.java**
   - Divide texto alrededor de números incrustados
   - Características:
     * Manejo de salida UTF-8
     * Múltiples grupos de captura
     * Demuestra coincidencia codiciosa vs no codiciosa
   - Salida de ejemplo para "Esto es un texto de 123 letras":
     Grupos de Números: 3
     Grupo Completo: Esto es un texto de 123 letras
     1º (.*?) Esto es un texto de 
     2º (\d+) 123
     3º (.*)  letras

## Características comunes:
---------------
- Todos los programas usan el paquete java.util.regex
- Demuestran las clases Matcher y Pattern
- Incluyen información detallada de posición de coincidencias
- Muestran aplicaciones prácticas de regex

## Requisitos:
------------
- Java JDK 8+
- Terminal compatible con UTF-8 (para TextNumberSplitter)

## Uso:
------
1. Compilar cualquier programa: `javac <NombrePrograma>.java`
2. Ejecutar: `java <NombrePrograma>`

## Versión:
--------
1.0 - Colección de demostración de expresiones regulares