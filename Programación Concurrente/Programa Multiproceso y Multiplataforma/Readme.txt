NO FINALIZADO

Programa Multiplataforma - Herramientas de Gestión de Archivos
=============================================================

Autor: Kevin Gómez Valderas
Curso: 2º DAM (Desarrollo de Aplicaciones Multiplataforma)

Descripción:
------------
Este programa ofrece un conjunto de herramientas para la gestión de archivos y directorios, 
funcionando en múltiples plataformas (Windows, Linux, macOS). Proporciona una interfaz 
de consola y gráfica para realizar diversas operaciones con archivos.

Funcionalidades:
----------------
1. Contador de archivos - Cuenta archivos y calcula tamaño total (con comparativa entre método Java y sistema)
2. Renombrar Ficheros - Renombra archivos usando expresiones regulares
3. PowerRename - Interfaz gráfica para renombrar archivos
4. Concatenador - Combina contenido de múltiples archivos y detecta errores
5. Buscador de directorios vacíos - Identifica carpetas sin contenido
6. Buscador de archivos por extensión - Encuentra archivos por su extensión

Requisitos:
-----------
- Java 8 o superior
- Permisos de lectura/escritura en los directorios a procesar

Instrucciones de Uso:
---------------------
1. Ejecutar el programa desde la clase principal: ProgramaMultiplataforma
2. Seleccionar una opción del menú principal
3. Seguir las instrucciones específicas para cada herramienta
4. Los resultados se mostrarán en consola y/o se guardarán en archivos según la operación

Estructura del Proyecto:
------------------------
- src/
  - programamultiplataforma/ - Paquete principal con las clases del programa
    - Controller.java - Clase utilitaria con métodos comunes
    - FileCounter.java - Herramienta de conteo de archivos
    - FileRenamer.java - Herramienta de renombrado
    - PowerRename/ - Interfaz gráfica para renombrado
    - FileConcatenator.java - Concatenador de archivos
    - DirectoryCleaner.java - Buscador de directorios vacíos
    - FindFileByExtension.java - Buscador por extensión
  - CarpetaEjemplo/ - Carpeta con archivos de ejemplo para pruebas

Notas:
------
- Para las opciones que requieren rutas, se proporciona una ruta por defecto (CarpetaEjemplo)
- Las expresiones regulares siguen el estándar Java
- El programa maneja codificación UTF-8 para compatibilidad con caracteres especiales

Licencia:
---------
Este software se proporciona "tal cual", sin garantías de ningún tipo. 
Puede ser usado, modificado y distribuido libremente.