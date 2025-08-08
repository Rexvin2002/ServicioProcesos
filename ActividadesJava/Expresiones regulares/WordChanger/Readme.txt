# Herramientas de Reemplazo de Palabras

## Descripción
Este paquete contiene dos programas Java que demuestran diferentes técnicas de manipulación de texto usando expresiones regulares:

1. **WordChanger.java**: Intercambia palabras relacionadas con gatos y perros en texto en español
2. **WordChanger2.java**: Reemplazo selectivo de palabras con soporte UTF-8

## Autor
Kevin Gómez Valderas (2ºDAM)

---

## Programa 1: WordChanger.java
### Funcionalidad
- Reemplaza todas las ocurrencias de palabras felinas por caninas y viceversa
- Maneja formas singulares/plurales y términos de especie (felinos/caninos)
- Conserva la estructura y formato original del texto

### Características clave
- Usa límites de palabra (`\b`) para coincidencias exactas
- Implementa switch-case para reemplazos específicos
- Mantiene la gramática correcta en español
- Procesa textos largos de múltiples párrafos

### Reglas de reemplazo:
```
gato → perro
gatos → perros 
felinos → perros
perro → gato
perros → gatos
caninos → gatos
```

### Detalles técnicos
- Usa las clases Pattern y Matcher
- Utiliza StringBuffer para manipulación eficiente
- Conserva puntuación y formato original

---

## Programa 2: WordChanger2.java
### Funcionalidad
- Reemplaza específicamente la 2ª y 4ª ocurrencia de "madera" por "metal"
- Maneja texto codificado en UTF-8
- Demuestra reemplazo posicional con contador

### Características clave
- Soporte completo para caracteres UTF-8
- Lógica de reemplazo basada en posición
- Usa text blocks para cadenas multilínea
- Maneja caracteres especiales del español (ñ, acentos)

### Detalles técnicos
- Configuración explícita de UTF-8
- Seguimiento de posición con contador
- StringBuffer para construcción de texto
- Text blocks para mejor legibilidad

---

## Características comunes
- Demuestran manipulación avanzada de texto con regex
- Muestran diferentes enfoques de reemplazo
- Manejan especificidades del español
- Usan java.util.regex

---

## Instrucciones de uso
### Para WordChanger:
```bash
javac wordchanger/WordChanger.java
java wordchanger.WordChanger
```

### Para WordChanger2:
```bash 
javac wordchanger/WordChanger2.java
java wordchanger.WordChanger2
```

---

## Personalización
- Modificar el texto de entrada en el código
- Cambiar reglas de reemplazo editando los patrones
- Ajustar la lógica posicional en WordChanger2

## Requisitos
- Java 11+ (para text blocks en WordChanger2)
- Sin dependencias externas

## Historial de versiones
1.0 - Versión inicial con ambas herramientas
1.1 - Añadido soporte UTF-8 y reemplazo posicional