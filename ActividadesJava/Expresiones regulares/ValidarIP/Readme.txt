# Validador de Direcciones IP en Java  

## 📌 Descripción  
Este proyecto contiene una clase Java que permite **validar direcciones IPv4** mediante expresiones regulares. El programa verifica si una cadena de texto cumple con el formato estándar de una dirección IP (ej. `192.168.0.1`).  

---

## 🛠️ Funcionalidades  
✔ **Validación de IPv4**:  
   - Acepta direcciones IP en el rango `0.0.0.0` a `255.255.255.255`.  
   - Usa una **expresión regular** para asegurar el formato correcto.  

✔ **Método reutilizable**:  
   - La función `esDireccionIPValida(String ip)` puede integrarse en otros proyectos.  

✔ **Manejo básico de salida**:  
   - Muestra en consola si la IP es válida o no.  

---

## 📋 Uso  
1. **Compilar el programa**:  
   ```bash
   javac ValidateIP.java
   ```
2. **Ejecutar**:  
   ```bash
   java ValidateIP
   ```
   Por defecto, valida la IP `192.168.0.1`, pero puedes modificarla en el código.  

---

## 🔍 Explicación de la Expresión Regular  
La validación se basa en el siguiente patrón:  
```java
^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9])\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9])$
```
- **`25[0-5]`**: Acepta números del 250 al 255.  
- **`2[0-4][0-9]`**: Acepta del 200 al 249.  
- **`[0-1]?[0-9]?[0-9]`**: Acepta del 0 al 199 (con opcionales para 1 o 2 dígitos).  
- **`\.`**: Separa los octetos con puntos.  

---

## 📝 Ejemplo de Salida  
```plaintext
192.168.0.1 es una dirección IP válida.
```
```plaintext
256.0.0.1 no es una dirección IP válida.
```

---

## 🚀 Posibles Mejoras  
- Añadir soporte para **IPv6**.  
- Permitir validar múltiples IPs desde la línea de comandos.  
- Implementar pruebas unitarias con JUnit.  

---

## 📜 Licencia  
Código libre de uso. ¡Siéntete libre de modificarlo! 🚀  

--- 

🔹 **Autor**: `Kevin`  

--- 