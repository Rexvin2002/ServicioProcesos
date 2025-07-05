# Create, Parse and Display

Este proyecto Java demuestra cómo crear, parsear y visualizar diferentes tipos de archivos (JSON, XML, Moodle XML y CSV) utilizando varias técnicas de procesamiento.

## Características principales

1. **Creación de archivos de ejemplo**:
   - Genera archivos en diferentes formatos con datos de ejemplo
   - Formatos soportados: JSON, XML, Moodle XML y CSV

2. **Procesamiento y análisis**:
   - Parseo de JSON usando la librería `org.json`
   - Parseo de XML usando tanto expresiones regulares como el parser DOM estándar
   - Parseo de archivos Moodle (basados en XML)
   - Parseo de CSV usando expresiones regulares

3. **Visualización**:
   - Muestra los datos parseados en interfaces gráficas (JFrame)
   - Cada tipo de archivo tiene su propia ventana de visualización

## Estructura del proyecto

El proyecto contiene dos clases principales:

1. **CreateFiles**:
   - Métodos para crear archivos de ejemplo en diferentes formatos
   - `createJSONFile()`: Crea un archivo JSON simple
   - `createXMLFile()`: Crea un archivo XML básico
   - `createMoodleFile()`: Crea un archivo de pregunta Moodle
   - `createCSVFile()`: Crea un archivo CSV con datos tabulares

2. **ParseAndDisplay**:
   - Métodos para parsear y mostrar cada tipo de archivo
   - `parseJSON()`: Parsea y muestra datos JSON
   - `parseXML()`: Parsea XML con regex y DOM
   - `parseMoodle()`: Parsea archivos Moodle XML
   - `parseCSV()`: Parsea archivos CSV con regex

## Dependencias

- `org.json` para el procesamiento de JSON
- Java XML API para el procesamiento de XML
- Swing para la interfaz gráfica

## Uso

1. Ejecute la clase `ParseAndDisplay` como aplicación Java
2. El programa:
   - Creará automáticamente los archivos de ejemplo
   - Parseará cada archivo
   - Mostrará ventanas con los datos procesados

## Notas

- Los archivos se crean en `src/main/java/com/mycompany/createparsedisplay/`
- El proyecto demuestra diferentes técnicas de procesamiento de archivos
- Incluye manejo básico de errores para cada tipo de operación