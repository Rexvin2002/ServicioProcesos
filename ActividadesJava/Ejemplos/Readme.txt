# Ejemplos de Expresiones Regulares en Java

## Descripción
Este paquete contiene tres programas Java que demuestran diferentes técnicas de expresiones regulares:

1. Coincidencia básica insensible a mayúsculas/minúsculas
2. Coincidencia exacta con límites de palabra
3. Aserciones avanzadas (lookahead y lookbehind)

## Autor
Kevin Gómez Valderas (2ºDAM)

## Programas incluidos

### 1. Ejemplo01.java - Coincidencia insensible a mayúsculas
**Características:**
- Usa el flag `Pattern.CASE_INSENSITIVE`
- Busca "kevin" en cualquier combinación de mayúsculas/minúsculas
- Utiliza `Matcher.find()` para coincidencias parciales

**Casos de prueba:**
- "Práctica Kevin Gomez" → Coincide
- "Práctica kevin Gomez" → Coincide
- "Práctica KEVIN Gomez" → Coincide

### 2. Ejemplo02.java - Coincidencia exacta de palabra
**Características:**
- Usa límites de palabra (`\b`)
- Patrón: `".*\\bkevin\\b.*"` (debe contener "kevin" como palabra completa)
- Usa `Matcher.matches()` para validación de cadena completa

### 3. Ejemplo03.java - Aserciones avanzadas
**Demuestra:**
1. Lookahead positivo (`?=pattern`): números seguidos de "Euros"
2. Lookahead negativo (`?!pattern`): números NO seguidos de "Libras" 
3. Lookbehind positivo (`?<=pattern`): números precedidos por "unos _"
4. Lookbehind negativo (`?<!pattern`): números NO precedidos por "unos _"

**Incluye** el método auxiliar `matchersMine()` para salida consistente

## Conceptos clave demostrados
- Flags de compilación de patrones
- Diferencias entre `find()` y `matches()`
- Límites de palabra (`\b`)
- Aserciones lookahead/lookbehind
- Captura de grupos

## Ejemplos de salida (Ejemplo03)
```
LOOKAHEAD ----------------------
TEXTO 1: 
Coincidencia encontrada: 123

NEGATIVE LOOKAHEAD ----------------------
TEXTO 2: 
Coincidencia encontrada: 200
Coincidencia encontrada: 6

LOOKBEHIND ----------------------
TEXTO 1: 
Coincidencia encontrada: 123
```

## Instrucciones de uso
1. Compilar: `javac ejemplos/Ejemplo0X.java`
2. Ejecutar: `java ejemplos.Ejemplo0X`

## Requisitos
- Java 8+ (paquete java.util.regex)
- Sin dependencias externas

## Historial de versiones
1.0 - Versión inicial con ejemplos básicos y avanzados de regex