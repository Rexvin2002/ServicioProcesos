AbrirNotepad

Descripción:
------------
Este programa Java abre el archivo "notepad.txt" en el Bloc de Notas (Notepad) de Windows.
Verifica si el archivo existe antes de intentar abrirlo y muestra mensajes de estado.

Autor:
-------
Kevin Gómez Valderas (2ºDAM)

Uso:
-----
1. Compilar el programa:
   javac AbrirNotepad.java

2. Ejecutar el programa:
   java AbrirNotepad

Características:
----------------
- Verifica la existencia del archivo antes de abrirlo
- Muestra mensajes de error descriptivos si el archivo no existe
- Abre el archivo en Notepad de Windows
- Espera a que el usuario cierre Notepad y muestra el código de salida

Estructura esperada de archivos:
-------------------------------
src/
└── notepad/
    ├── AbrirNotepad.java
    └── notepad.txt (archivo a abrir)

Requisitos:
-----------
- Sistema operativo Windows (usa notepad.exe)
- Java JDK instalado
- Archivo notepad.txt en la ruta especificada (o modificar el código)

Mensajes posibles:
-----------------
- "Error: El archivo '...' no existe."
- "Notepad se cerró correctamente."
- "Notepad se cerró con código de error: ..."
- "Error al ejecutar Notepad: ..."
- "El proceso fue interrumpido: ..."

Personalización:
---------------
Para cambiar el archivo a abrir, modificar las variables:
- nombreArchivo
- rutaArchivo

Versión:
--------
1.0 (programa básico para abrir archivos en Notepad)