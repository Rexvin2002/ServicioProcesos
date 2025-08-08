# Validador de DNI

## Descripción:
Programa Java que valida números de DNI (Documento Nacional de Identidad) españoles, verificando tanto el formato como la letra según la normativa oficial. Soporta tanto DNIs individuales como listas separadas por comas.

## Autor:
-------
Kevin Gómez Valderas (2ºDAM)

## Características principales:
- Valida el formato del DNI mediante expresiones regulares
- Verifica que la letra corresponda al número según el algoritmo oficial
- Admite múltiples DNIs separados por comas
- Acepta tanto guiones (-) como guiones bajos (_) como separadores
- Proporciona retroalimentación detallada sobre la validación

## Reglas de validación:
-----------------
1. Formato: 8 dígitos + separador (- o _) + 1 letra (A-Z)
2. La letra debe coincidir con el cálculo oficial del DNI español
3. Para múltiples DNIs:
   - Deben estar separados por comas
   - Se permiten espacios alrededor de las comas
   - Cada DNI debe ser válido individualmente

## Patrón Regex:
--------------
"\\b[1-9][0-9]{7}[-_][A-Z]\\b(\\s*,\\s*[1-9][0-9]{7}[-_][A-Z])*"


## Cálculo de la letra:
-------------------
Utiliza la tabla oficial de letras del DNI: "TRWAGMYFPDXBNJZSQVHLCKE"
La letra se obtiene con: (número de DNI % 23) como índice en la cadena

## Cómo usar:
------
El programa incluye casos de prueba que demuestran patrones válidos e inválidos.
Para añadir tus propios casos de prueba, modifica el array 'inputs' en el método main.

## Ejemplo de salida:
--------------
Entrada 48134118-V --------- VALIDA.
Entrada 12345678-A --------- VALIDA.
Entrada 48134118-V , 48134118-V --------- VALIDA.
Entrada 12345678Z,87654321-B --------- NO VALIDA.
Entrada 12345678-A,87654321B --------- NO VALIDA.
...

## Tipos de error:
------------
1. "NO VALIDA" - El formato no coincide con el patrón regex
2. "NO VALIDA (letra incorrecta)" - El formato es correcto pero la letra no coincide

## Requisitos:
-------------
- Java JDK 8 o superior
- No se requieren dependencias externas

## Notas importantes:
------
- El primer dígito no puede ser 0 (valida [1-9] para el primer dígito)
- La letra debe estar en mayúscula
- Maneja múltiples DNIs con espacios flexibles alrededor de las comas

## Versión:
--------
1.0 - Validador básico de DNI con soporte para múltiples DNIs