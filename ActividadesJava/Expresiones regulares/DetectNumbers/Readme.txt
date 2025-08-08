DetectNumbers

Descripción:
------------
Este programa Java detecta y extrae todos los números enteros presentes en una cadena de texto predefinida, mostrándolos como una lista.

Autor:
-------
Kevin Gómez Valderas (2ºDAM)

Funcionalidad:
--------------
- Busca patrones numéricos en un texto usando expresiones regulares
- Almacena los números encontrados en una lista
- Muestra los resultados por consola

Ejemplo de salida:
------------------
Para el texto: 
"En el centro tenemos 10 ordenadores portátiles, también tenemos 5 ordenadores fijos, tenemos 4 móviles pero sólo tenemos 3 cardboards."

Salida esperada:
Números encontrados: [10, 5, 4, 3]

Tecnologías utilizadas:
----------------------
- Expresiones regulares (Regex)
- ArrayList para almacenamiento dinámico
- Java Util package

Requisitos:
-----------
- Java JDK instalado
- No se necesitan librerías externas

Personalización:
---------------
Para analizar otro texto:
1. Modificar la variable 'frase' en el código fuente
2. Recompilar el programa

Características técnicas:
------------------------
- Patrón de búsqueda: "\\d+" (uno o más dígitos consecutivos)
- Conversión automática de String a Integer
- Manejo seguro de coincidencias con Matcher

Versión:
--------
1.0 (programa básico de detección de números)