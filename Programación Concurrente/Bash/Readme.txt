# Ejecutor de Scripts Bash en Windows

## Descripción
Programa Java que permite ejecutar scripts Bash en sistemas Windows, ideal para entornos de desarrollo donde se necesita compatibilidad multiplataforma.

## Autor
Kevin Gómez Valderas (2ºDAM)

## Características principales
- Ejecuta scripts Bash en Windows mediante Git Bash
- Verifica la existencia del script antes de ejecutarlo
- Captura y muestra la salida del script en tiempo real
- Proporciona información detallada del estado de ejecución

## Requisitos
- Windows con Git Bash instalado (ruta predeterminada: `C:\Program Files\Git\bin\bash.exe`)
- Script Bash existente en la ruta especificada

## Configuración
1. Modificar la variable `scriptPath` para apuntar a tu script Bash
2. Ajustar la ruta a `bash.exe` si Git está instalado en otra ubicación

## Ejemplo de uso

String scriptPath = "src/scripts/mi_script.bash";
ProcessBuilder pb = new ProcessBuilder(
        "C:\\Program Files\\Git\\bin\\bash.exe",
        "-c",
        "bash " + scriptPath
);


## Manejo de errores
- Verifica si el script existe antes de ejecutarlo
- Captura y muestra errores de E/S y de interrupción
- Proporciona el código de salida del script

## Salida esperada

Salida del script:
[Salida del script Bash línea por línea...]

Script finalizado con código: 0


## Personalización
- Cambiar la ruta de Git Bash si es necesario
- Modificar el manejo de la salida según necesidades
- Añadir parámetros adicionales al script

## Notas importantes
- Requiere Git instalado en Windows
- Los scripts deben tener permisos de ejecución
- Ideal para integración con herramientas de desarrollo

## Mejoras futuras
- Soporte para otras shells de Unix en Windows (WSL)
- Ejecución remota de scripts
- Mejor manejo de argumentos del script