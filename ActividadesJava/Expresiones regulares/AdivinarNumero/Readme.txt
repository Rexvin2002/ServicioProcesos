# 🎮 **Juego de Adivinanza de Números**  

**¡Adivina los 4 números ocultos!**  
Este programa es un juego interactivo en Java que genera **4 números aleatorios (0-9)** y desafía al usuario a adivinarlos.  

---

## 🚀 **Características**  
✔ **Interfaz gráfica intuitiva** (Swing).  
✔ **Validación en tiempo real**: Solo permite números de un dígito.  
✔ **Retroalimentación visual**:  
   - `>` si el número ingresado es **mayor** al oculto.  
   - `<` si es **menor**.  
   - `=` si es **correcto**.  
✔ **Diseño responsive**: Se adapta al tamaño de la ventana.  
✔ **Efectos visuales**:  
   - Botón **OK** se vuelve verde al acertar todos los números.  
   - Campos de texto con estilo naranja.  

---

## 🛠️ **Cómo Funciona**  
1. **Generación de números aleatorios**:  
   - Se crean 4 números entre 0 y 9 al iniciar el juego.  
2. **Validación de entrada**:  
   - Usa un `DocumentFilter` para permitir **solo un dígito numérico**.  
3. **Comparación de números**:  
   - Al presionar **OK**, compara cada número ingresado con los números ocultos.  
4. **Muestra resultados**:  
   - Muestra `>`, `<` o `=` según corresponda.  
   - Si todos son correctos, el botón OK se pone verde.  

---

## ▶️ **Cómo Ejecutarlo**  
1. **Compilar**:  
   ```bash
   javac NumberGuessingGame.java
   ```
2. **Ejecutar**:  
   ```bash
   java NumberGuessingGame
   ```
3. **Jugar**:  
   - Ingresa números en los 4 campos.  
   - Presiona **OK** para verificar.  
   - ¡Adivina todos para ganar!  

---

## 📜 **Licencia**  
Código libre para uso educativo.  

---

**🎯 ¡Diviértete adivinando!**  
🔹 *Autor: Kevin*  
🔹 *Tecnologías: Java, Swing*