# ChatApp - Sistema de Chat Cliente-Servidor

## Descripción del Proyecto

ChatApp es una aplicación de mensajería cliente-servidor desarrollada en Java que permite a los usuarios conectarse a servidores de chat, enviar mensajes y compartir archivos. La aplicación ofrece tanto una interfaz gráfica (GUI) como un modo consola (CLI), adaptándose a diferentes entornos de ejecución.

## Características Principales

- **Autenticación de usuarios**: Registro e inicio de sesión con avatar personalizado
- **Gestión de conexiones**: Conexión a múltiples servidores con historial
- **Comunicación en tiempo real**: Envío de mensajes de texto entre clientes
- **Transferencia de archivos**: Compartir archivos a través de la red
- **Modos de ejecución**: Interfaz gráfica (Swing) y línea de comandos
- **Persistencia de datos**: Almacenamiento local de perfiles y servidores conectados

## Estructura del Proyecto

El proyecto sigue una arquitectura MVC (Modelo-Vista-Controlador) organizada en los siguientes paquetes:

```
src/
├── controllers/      # Lógica de control
│   ├── Controller.java
│   ├── PanelsController.java
│   └── UserController.java
├── main/             # Punto de entrada
│   └── Main.java
├── models/           # Modelos de datos
│   ├── Client.java
│   ├── Server.java
│   └── ServerMessage.java
└── ui/               # Interfaces de usuario
    ├── ChatApp.java
    ├── ConsoleMode.java
    └── MensajeDialog.java
```

## Requisitos del Sistema

- Java JDK 17 o superior
- Maven (para gestión de dependencias)
- Conexión de red para funcionalidad cliente-servidor

## Instalación y Ejecución

### Desde IDE (Eclipse/IntelliJ/NetBeans)
1. Importar el proyecto como proyecto Maven existente
2. Ejecutar la clase `main.Main`

### Desde línea de comandos
```bash
mvn clean package
java -jar target/chatapp.jar
```

### Modos de Ejecución
- **Interfaz gráfica**: Se ejecuta automáticamente en entornos con soporte gráfico
- **Consola**: Se activa automáticamente en entornos sin GUI o con el parámetro `--headless`

## Uso Básico

1. **Crear una cuenta**:
   - Proporcionar nombre de usuario, contraseña y avatar (opcional)
   
2. **Iniciar sesión**:
   - Ingresar credenciales registradas

3. **Conectar a un servidor**:
   - Especificar dirección IP y puerto
   - Opción de guardar servidores favoritos

4. **Enviar mensajes/archivos**:
   - Escribir en el campo de texto o seleccionar archivo para enviar

## Documentación Técnica

### Modelos Principales

#### `Client`
- Gestiona la conexión del cliente al servidor
- Maneja el perfil del usuario y la persistencia de datos
- Implementa envío/recepción de mensajes y archivos

#### `Server`
- Escucha conexiones entrantes de clientes
- Distribuye mensajes a todos los clientes conectados
- Gestiona transferencia de archivos

### Controladores

#### `UserController`
- Autenticación y gestión de usuarios
- Operaciones CRUD sobre perfiles
- Validación de datos de entrada

#### `PanelsController`
- Actualización dinámica de la interfaz
- Gestión de paneles de mensajes y servidores
- Visualización de avatares y archivos

### Protocolo de Comunicación

- Los mensajes de texto se envían como strings simples
- Los archivos se codifican en Base64 y se envían como JSON:
```json
{
  "type": "file",
  "filename": "document.pdf",
  "sender": "usuario1",
  "data": "[base64 encoded data]",
  "timestamp": 1234567890
}
```

## Mejoras Futuras

1. **Seguridad**:
   - Implementar cifrado TLS/SSL para las comunicaciones
   - Hash seguro para contraseñas (bcrypt/PBKDF2)

2. **Funcionalidad**:
   - Salas de chat privadas/grupales
   - Notificaciones y mensajes privados
   - Historial de conversaciones

3. **Optimización**:
   - Compresión de mensajes/archivos
   - Mejor gestión de recursos y conexiones
   - Sistema de caché para avatares

4. **Experiencia de Usuario**:
   - Emojis y formato de texto enriquecido
   - Indicadores de estado (conectado/escribiendo)
   - Temas personalizables

## Contribución

Las contribuciones son bienvenidas. Por favor, enviar pull requests a la rama `develop` después de:

1. Discutir el cambio propuesto mediante un issue
2. Asegurar que todas las pruebas pasan
3. Mantener el estilo de código consistente

## Licencia

Este proyecto está licenciado bajo MIT License - ver el archivo [LICENSE](LICENSE) para más detalles.