# Colección de Programas de Manejo de Arreglos

## Descripción
Este paquete contiene diversos programas Java para trabajar con arreglos, incluyendo operaciones básicas y avanzadas como búsqueda, comparación, modificación y generación de arreglos.

## Autor
Kevin Gómez Valderas (2ºDAM)

## Programas incluidos

### 1. ArrayNumberSearch
**Función**: Busca un número específico en un arreglo predefinido usando búsqueda binaria.
- Características:
  - Interfaz gráfica con JOptionPane
  - Validación de entrada del usuario
  - Uso de `Arrays.binarySearch()`

### 2. CompareArrays
**Función**: Compara dos arreglos de números decimales ingresados por el usuario.
- Características:
  - Detecta la primera diferencia entre arreglos
  - Usa `Arrays.mismatch()`
  - Manejo de entrada de 5 valores por arreglo

### 3. DeleteElementFromArray
**Función**: Elimina un elemento de un arreglo por su índice.
- Características:
  - Arreglo predefinido de 10 elementos
  - Crea nuevo arreglo sin el elemento eliminado
  - Validación de índices

### 4. DeleteElementFromSortedArray
**Función**: Elimina elementos por valor en un arreglo ordenado.
- Características:
  - Interfaz personalizada con nombre de usuario
  - Eliminación múltiple en bucle
  - Mantiene el arreglo ordenado

### 5. DeleteValueFromArray
**Función**: Elimina todas las ocurrencias de un valor en un arreglo.
- Características:
  - Elimina múltiples instancias del mismo valor
  - Bucle continuo para múltiples eliminaciones
  - Manejo de valores repetidos

### 6. InsertAfterThree
**Función**: Inserta un elemento después del número 3 en un arreglo.
- Características:
  - Uso de `Arrays.copyOf()`
  - Desplazamiento de elementos
  - Búsqueda con `Arrays.binarySearch()`

### 7. InsertElementInArray
**Función**: Inserta un elemento en posición específica.
- Características:
  - Demostración de inserción en arreglos
  - Desplazamiento manual de elementos
  - Uso de `Arrays.copyOf()`

### 8. NumberGame
**Función**: Juego para ingresar números únicos entre 1 y 100.
- Características:
  - Seguimiento de números ingresados
  - Validación de entradas
  - Límite de 100 números únicos

### 9. ParImparSeparador
**Función**: Separa números pares e impares ingresados por el usuario.
- Características:
  - Entrada dinámica con ArrayList
  - Ordenamiento de resultados
  - Salida gráfica y por consola

### 10. RandomTable
**Función**: Genera y suma un arreglo de números aleatorios.
- Características:
  - Uso de `Arrays.setAll()`
  - Generación de valores aleatorios
  - Cálculo de suma con streams

## Características comunes
- Uso de `java.util.Arrays` para operaciones con arreglos
- Interfaz gráfica con `JOptionPane` (en la mayoría de programas)
- Validación de entrada del usuario
- Manejo de excepciones
- Salida formateada

## Requisitos
- Java 8+
- Swing para programas con interfaz gráfica

## Uso
Cada programa puede compilarse y ejecutarse individualmente:
```bash
javac arrays/NombreDelPrograma.java
java arrays.NombreDelPrograma
```

## Notas
- Algunos programas usan características de Java 9+ (como `Arrays.mismatch()`)
- Los programas con interfaz gráfica requieren entorno con soporte para Swing
- La mayoría incluyen manejo básico de errores y validación de entrada