## 1. Clase Animal (Extiende Thread)

**Qué hace:**
Esta implementación crea animales como hilos que simulan el proceso de comer. Cada animal es un hilo independiente que:

- Indica cuándo empieza a comer mostrando el tiempo transcurrido
- Simula el proceso de comer con mensajes "come come..." cada 500ms
- Cuenta cuántas veces ha comido
- Muestra cuándo termina de comer y el tiempo total que tardó

**Características principales:**
- Usa herencia directa de la clase Thread
- Tiene un contador de comidas por animal
- Mide y muestra tiempos de ejecución
- Compara ejecución secuencial (llamando a run()) vs concurrente (con start())

## 2. Clase AnimalRunnable (Implementa Runnable)

**Qué hace:**
Versión mejorada del programa anterior que:

- Crea los mismos animales que simulan comer
- Muestra los mismos mensajes y tiempos
- Realiza la misma comparación entre ejecución secuencial y concurrente

**Diferencias clave:**
- Implementa la interfaz Runnable en lugar de extender Thread
- Requiere crear objetos Thread separados para ejecutarse
- El diseño es más limpio y flexible (permite mayor herencia)
- El funcionamiento observable es idéntico al primero

## 3. Clase AnimalRunnable2 (Implementación avanzada)

**Qué hace:**
Versión sofisticada que:

- Crea múltiples hilos de animales que comen
- Implementa un sistema de interrupción para detener los hilos
- Usa un ExecutorService para gestionar los hilos eficientemente
- Emplea CountDownLatch para sincronización
- Muestra los resultados en una interfaz gráfica con tabla
- Realiza análisis estadístico del rendimiento con diferentes cantidades de hilos

**Características avanzadas:**
- Interfaz gráfica con JFrame y JTable
- Pruebas de rendimiento con 2, 10, 100 y 1000 hilos
- Cálculo de errores y estadísticas de ejecución
- Uso de AtomicInteger para contadores thread-safe
- Implementación más robusta de manejo de interrupciones
- Muestra comparativas de comidas esperadas vs reales