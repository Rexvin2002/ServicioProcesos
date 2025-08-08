# Validador de Nombres Españoles

## Descripción
Programa Java avanzado para validar formatos de nombres españoles según convenciones comunes, incluyendo:
- Apellidos compuestos
- Múltiples entradas de nombres separados por guiones
- Caracteres especiales (áéíóú, ñ/Ñ)
- Sufijos alfanuméricos opcionales (para documentos oficiales)

## Autor
Kevin Gómez Valderas (2ºDAM)

## Reglas de Validación

### Formato Básico:
1. Apellido(s) primero, seguido de coma y nombre
2. El apellido puede ser simple o compuesto (con espacio o guión)
3. Múltiples entradas de nombres separados por guiones con espacios opcionales

### Caracteres Soportados:
- Letras (A-Z, a-z)
- Caracteres españoles (ÁÉÍÓÚÑáéíóúñ)
- Números (0-9) en ciertas posiciones
- Espacios y guiones como separadores

### Casos Especiales:
- Sufijo alfanumérico opcional al final (ej. "1A")
- Espacios flexibles alrededor de separadores
- Múltiples entradas de nombres en una sola cadena

## Estructura del Patrón Regex
El patrón complejo maneja:
1. Primer apellido (simple o compuesto)
2. Separador de coma con espacios opcionales
3. Primer nombre
4. Nombres adicionales opcionales (separados por guiones)
5. Sufijo alfanumérico opcional

## Casos de Prueba Incluidos
El programa prueba varios patrones válidos e inválidos:
- Nombres simples válidos (Gómez Pérez, Juan)
- Múltiples entradas separadas por guiones
- Apellidos compuestos
- Casos límite (nombres faltantes, formatos incorrectos)
- Variaciones de espaciado

## Ejemplos de Salida
```
Entrada 'Gómez Pérez, Juan' --------- VALIDA.
Entrada 'Pérez, Ana - Pérez, Ana - Pérez, Ana' --------- VALIDA. 
Entrada 'Gómez Pérez Juan' --------- NO VALIDA.
Entrada 'gómez Pérez, Juan' --------- NO VALIDA. (inicial minúscula)
Entrada 'López García, Juan, Pérez, María' --------- VALIDA.
```

## Instrucciones de Uso
1. Compilar: `javac namevalidator/NameValidator.java`
2. Ejecutar: `java namevalidator.NameValidator`

Para añadir más casos de prueba:
- Modificar el array 'inputs' en el método main()

## Requisitos
- Java 8 o superior
- No se requieren librerías externas

## Limitaciones
- No verifica nombres contra registros oficiales
- Puede no cubrir todas las convenciones regionales
- La validación de sufijos es básica

## Historial de Versiones
1.0 - Versión inicial con validación completa de nombres españoles
1.1 - Añadido soporte para sufijos alfanuméricos