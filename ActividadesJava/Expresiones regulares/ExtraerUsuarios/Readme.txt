# Passwd User Extractor

Este programa Java está diseñado para analizar el archivo `/etc/passwd` de sistemas Unix/Linux y extraer información específica sobre los usuarios del sistema utilizando expresiones regulares.

## Funcionalidad

El programa realiza las siguientes acciones:

1. **Lee el archivo `/etc/passwd`**: Accede al archivo que contiene información sobre las cuentas de usuario del sistema.
2. **Aplica un filtro con expresión regular**: Busca líneas que cumplan con un patrón específico que identifica usuarios válidos.
3. **Muestra los usuarios encontrados**: Imprime en consola las líneas que coinciden con el patrón buscado.

## Patrón de Expresión Regular

La expresión regular utilizada (`^[^:]+:[^:]*:([1-9][0-9]{2,}|1000):[1-9][0-9]*:([^:]*)?:/home/[^:]+:/[^:]+$`) busca:

- Usuarios con UID (User ID) mayor o igual a 1000 (usuarios normales, no del sistema)
- Que tengan un directorio home bajo `/home/`
- Con un shell asignado

## Estructura del Código

- **Clase principal**: `PasswdUserExtractor`
- **Método main**: Punto de entrada del programa
- **Flujo de ejecución**:
  1. Compila la expresión regular
  2. Lee el archivo `/etc/passwd` línea por línea
  3. Aplica el patrón a cada línea
  4. Imprime las líneas que coinciden

## Requisitos

- Sistema operativo Unix/Linux (para tener el archivo `/etc/passwd`)
- Java 8 o superior

## Manejo de Errores

El programa incluye manejo básico de errores para:
- Problemas de lectura del archivo
- Problemas de acceso al archivo

## Uso

1. Compilar el programa: `javac PasswdUserExtractor.java`
2. Ejecutar: `java PasswdUserExtractor`

## Notas

- Este programa solo funciona en sistemas Unix/Linux que tengan el archivo `/etc/passwd`
- Requiere permisos de lectura para acceder al archivo
- El patrón puede ajustarse para filtrar diferentes tipos de usuarios según sea necesario