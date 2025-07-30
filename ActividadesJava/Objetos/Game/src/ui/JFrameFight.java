package ui;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import manager.GameManager;
import gameobjects.Ball;
import gameobjects.Heart;
import gameobjects.Player;
import gameobjects.Wall;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.PointLabel;

public class JFrameFight extends JFrame {

    // FPS
    private static final double FPMILLIS = 165.0 / 1000.0;
    private static final double TIME_DIFF_STANDARD = 1 / FPMILLIS;
    private long lastUpdateTime = 0;

    // GAME MANAGER
    public GameManager manager;
    public GameOverDialog gameOverDialog;

    // LEVEL
    private int nivel = 0;
    private static final int MAX_LEVEL = 5;

    // ROW
    private final int alturaFila = 60;

    // PLAYER
    private int anchoJugador = 700;
    private final int alturaJugador = 15;

    // BALL
    private int tamañoBola = 150;

    // MURO
    private int anchoMuro = 225;
    private final int alturaMuro = 50;

    // CONTADOR
    private boolean countdownActive = false;
    private int countdown = 3;

    // PUNTOS
    private int puntos = 0;// PUNTOS GANADOS
    private String puntosGanadosLabel = ""; // TEXTO PUNTOS GANADOS
    private int labelYPosition = -100; // POSICION INICIAL DE ETIQUETA 
    private boolean labelVisible = false; // VISIBILIDAD DE ETIQUETA
    private float labelAlpha = 1.0f; // OPACIDAD ETIQUETA
    private final ArrayList<PointLabel> pointLabels = new ArrayList<>();

    // PAUSA
    private boolean paused = false;
    private Timer countdownTimer;

    private JFrame mainMenu;

    public JFrameFight(JFrame mainMenu) {

        super();
        this.mainMenu = mainMenu;
        initialize();

    }

    // INICIALIZAR EL JFRAME, EL MANAGER Y ESTABLECER ICONO
    private void initialize() {

        if (this.manager == null) {
            this.manager = new GameManager(this);
        }

        JPanel panel = new GamePanel();
        // panel.setSize(GameManager.pantalla.width, GameManager.pantalla.height);
        panel.addKeyListener(new GameKeyListener());
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        // ESTABLECER ICONO AL FRAME
        try {

            Image imageIcon = ImageIO.read(new File(GameManager.icon));
            setIconImage(imageIcon);

        } catch (IOException e) {

            System.err.println("\nError: " + e.getMessage());

        }

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // this.setSize(GameManager.pantalla.width, GameManager.pantalla.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.add(panel);
        gameOverDialog = new GameOverDialog(this, false);
        this.setVisible(true);

    }

    // INCREMENTAR LOS PUNTOS GANADOS
    public void incrementPoints(int value) {

        this.puntos += value;
        showPointsLabel(value);

    }

    // MOSTRAR ETIQUETA DE PUNTOS
    private void showPointsLabel(int value) {
        String pointsText = "+" + value + " POINTS";

        // La posición Y de cada etiqueta comienza desde un valor fijo
        int yPos = getHeight() / 2; // Iniciar en la mitad de la pantalla

        // Añadir nueva etiqueta a la lista
        pointLabels.add(new PointLabel(pointsText, yPos));

        // Crear un temporizador para actualizar todas las etiquetas en la lista
        Timer timer = new Timer(50, (ActionEvent e) -> {
            for (int i = 0; i < pointLabels.size(); i++) {
                PointLabel label = pointLabels.get(i);
                label.updatePositionAndAlpha(); // Actualizar posición y opacidad

                // Remover la etiqueta de la lista si se desvaneció completamente
                if (label.isFaded()) {
                    pointLabels.remove(i);
                    i--; // Ajustar índice después de la eliminación
                }
            }
            repaint(); // Redibujar el panel
            if (pointLabels.isEmpty()) {
                ((Timer) e.getSource()).stop(); // Detener el temporizador si no quedan etiquetas
            }
        });
        timer.start();
    }

    // INICIALIZAR COMPONENTES
    public void initGameObjects() {

        if (!paused) {
            double ancho = this.getWidth();
            double altura = this.getHeight();

            if (Wall.muros.isEmpty()) {

                // EN CASO DE SER EL ULTIMO NIVEL
                if (this.nivel >= JFrameFight.MAX_LEVEL) {
                    GameManager.stopBackgroundMusic();
                    setVisible(false);

                    if (!GameManager.stopSound) {
                        GameManager.playEffectSound(GameManager.victory);
                    }

                    VictoryDialog victoryDialog = new VictoryDialog(this, true);
                    victoryDialog.setVisible(true);
                }

                // SI EL MANEGER ES NULL SE AÑADE UNO
                if (this.manager != null) {
                    this.manager.clearGameObjects();
                    this.manager = new GameManager(this);
                }

                // SUENA EL SIGUIENTE NIVEL MENOS EN EL PRIMERO
                if (this.nivel != 0) {

                    if (!GameManager.stopSound) {
                        GameManager.playEffectSound(GameManager.nextlevel);
                    }

                }

                this.nivel++; // INCREMENTA EL NIVEL
                this.anchoJugador -= 100;
                this.tamañoBola -= 25;
                this.anchoMuro -= 35;

                // ELIMINATODOS LOS OBJETOS
                this.manager.clearGameObjects();

                // AÑADE LOS CORAZONES
                for (int i = 0, x = 25; i < GameManager.MAX_TOUCHES; i++, x += 50) {

                    Heart heart = Heart.addHeart(x, 25);
                    if (heart instanceof Heart) { // Verificación
                        this.manager.addGameObject(heart);
                    }
                }

                // CREA EL JUGADOR
                double playerX = (ancho - this.anchoJugador) / 2;
                double playerY = altura - this.alturaJugador;
                Player jugador = new Player(playerX, playerY, this.anchoJugador, this.alturaJugador, new Color(255, 255, 255, 150));
                jugador.setSpeedX(jugador.getSpeedX() + this.nivel);
                this.manager.addGameObject(jugador);

                // CREA UNA BOLA
                double ballX = ancho / 2;
                double ballY = altura / 1.1;

                if (this.nivel == 5) {
                    Ball bola = new Ball(ballX - 75, ballY, this.tamañoBola, this.tamañoBola, Color.RED, 5, 13);
                    bola.setSpeedX(bola.getSpeedX() + this.nivel); // Increase ball speed
                    bola.setSpeedY(bola.getSpeedY() + this.nivel);
                    this.manager.addGameObject(bola);
                    Ball bola2 = new Ball(ballX + 75, ballY, this.tamañoBola, this.tamañoBola, Color.RED, 5, 13);
                    bola2.setSpeedX(bola.getSpeedX() + this.nivel); // Increase ball speed
                    bola2.setSpeedY(bola.getSpeedY() + this.nivel);
                    this.manager.addGameObject(bola2);
                } else {
                    Ball bola = new Ball(ballX, ballY, this.tamañoBola, this.tamañoBola, Color.RED, 5, 13);
                    bola.setSpeedX(bola.getSpeedX() + this.nivel); // Increase ball speed
                    bola.setSpeedY(bola.getSpeedY() + this.nivel);
                    this.manager.addGameObject(bola);
                }

                // CREA LOS MUROS
                double murosPorFila = Math.floor(ancho / this.anchoMuro); // Walls per row
                double separacionBase = (ancho - (murosPorFila * this.anchoMuro)) / (murosPorFila + 1); // Initial separation
                double multiplicadorSeparacion = 1.5; // Separation multiplier
                double separacion = separacionBase * multiplicadorSeparacion;

                // MANEJA LOS MUROS
                while (murosPorFila * this.anchoMuro + (murosPorFila + 1) * separacion > ancho) {
                    murosPorFila--;
                    separacion = (ancho - (murosPorFila * this.anchoMuro)) / (murosPorFila + 1);
                }

                // COLOCA LOS MUROS
                int numRows = 1 + this.nivel;
                int rowHeight = this.alturaFila;

                Random random = new Random(); // Para la generación aleatoria de los muros
                int maxBreakableWalls = 1 + this.nivel; // El número máximo de muros rompibles
                int breakableCount = 0; // Contador de muros rompibles

                for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
                    for (double i = 0, e = separacion; i < murosPorFila; i++, e += this.anchoMuro + separacion) {

                        boolean breackable = random.nextBoolean();
                        boolean previousUnbreakable = false; // Nueva variable para rastrear si el último muro fue no rompible

                        if (!breackable && breakableCount >= maxBreakableWalls) {
                            breackable = true; // Forzar a que el muro sea rompible si se alcanzó el máximo de muros rompibles
                        } else if (breackable) {
                            breakableCount++; // Contar los muros rompibles
                        }

                        // Asegurar que no haya dos muros no rompibles seguidos
                        if (!breackable && previousUnbreakable) {
                            breackable = true; // Forzar a que sea rompible si el muro anterior no era rompible
                        }

                        // Actualizar el estado del último muro
                        previousUnbreakable = !breackable;

                        if (rowIndex == numRows - 1) {
                            boolean dosBloques = (e == separacion || e == this.anchoMuro * 2 + separacion * 3);
                            boolean tresBloques = (e == separacion || e == this.anchoMuro * 2 + separacion * 3 || e == this.anchoMuro * 4 + separacion * 5);

                            if (this.nivel == 2 && dosBloques) {
                                Wall muro = new Wall(e, 100 + this.alturaFila + (rowIndex * rowHeight), this.anchoMuro, this.alturaMuro, numRows - rowIndex, false, true, this);
                                this.manager.addGameObject(muro);
                            } else if (this.nivel == 3 && tresBloques) {
                                Wall muro = new Wall(e, 200 + this.alturaFila + (rowIndex * rowHeight), this.anchoMuro, this.alturaMuro, numRows - rowIndex, false, true, this);
                                this.manager.addGameObject(muro);
                            } else if (this.nivel == 4 && tresBloques) {
                                Wall muro = new Wall(e, 200 + this.alturaFila + (rowIndex * rowHeight), this.anchoMuro, this.alturaMuro, numRows - rowIndex, false, true, this);
                                this.manager.addGameObject(muro);
                            } else if (this.nivel == 5 && tresBloques) {
                                Wall muro1 = new Wall(e, 100 + this.alturaFila + (rowIndex * rowHeight), this.anchoMuro, this.alturaMuro, numRows - rowIndex, false, true, this);
                                this.manager.addGameObject(muro1);
                                Wall muro2 = new Wall(e, 200 + this.alturaFila + (rowIndex * rowHeight), this.anchoMuro, this.alturaMuro, numRows - rowIndex, false, true, this);
                                this.manager.addGameObject(muro2);
                            }
                        }

                        // Crear el muro
                        Wall muro = new Wall(e, 100 + (rowIndex * rowHeight), this.anchoMuro, this.alturaMuro, numRows - rowIndex, breackable, false, this);
                        this.manager.addGameObject(muro);

                    }

                }

            }
        }

    }

    // EMPIEZA EL JUEGO
    public void startGame() {

        initGameObjects();
        repaint();

        // INICIAR CUENTA Y MUSICA DE FONDO
        if (!GameManager.stopSound) {

            if (!GameManager.stopMusic) {

                GameManager.initBackgroundMusic(GameManager.music);

            }

            startCountdown();

        } else {

            startCountdown();

        }

    }

    // CERRAR TODAS LAS VENTANAS ABIERTAS
    public void closeAllFrames() {

        Window[] windows = Window.getWindows(); // Obtener todas las ventanas abiertas

        for (Window window : windows) {

            if (window instanceof JFrame && window.isVisible()) {
                window.dispose();
            }

        }

    }

    // EMPEZAR CUENTA ATRÁS
    private void startCountdown() {

        this.countdownActive = true;
        this.countdown = 3;

        countdownTimer = new Timer(1000, (ActionEvent e) -> {
            this.countdown--;

            if (this.countdown < 0) {
                this.countdownActive = false;
                countdownTimer.stop();
            }

            repaint();
        });

        countdownTimer.start();
    }

    // CREA Y PINTA EL PANEL EN EL QUE SE VA HA JUGAR
    private class GamePanel extends JPanel {

        // CARGA LA FUENTE
        private Font loadFont(String path, float size) {

            try {

                return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);

            } catch (FontFormatException | IOException e) {

                return new Font("Arial", Font.BOLD, (int) size);

            }

        }

        // DIBUJA EL NIVEL
        private void drawLevel(Graphics g) {

            Font pixelFont = loadFont(GameManager.font, 50f);
            g.setFont(pixelFont);
            g.setColor(Color.WHITE);

            String levelText = "LEVEL " + nivel;
            int textWidth = g.getFontMetrics().stringWidth(levelText);
            int x = (getWidth() - textWidth) / 2;
            int y = 60;

            g.drawString(levelText, x, y);

        }

        // DIBUJA LOS PUNTOS TOTALES GANADOS
        private void drawPoints(Graphics g) {

            Font pixelFont = loadFont(GameManager.font, 40f);
            g.setFont(pixelFont);
            g.setColor(Color.WHITE);

            String pointsText = puntos + " POINTS";
            int textWidth = g.getFontMetrics().stringWidth(pointsText);
            int x = getWidth() - textWidth - 20; // Right-aligned with margin
            int y = 60; // Adjusted Y position for font size

            g.drawString(pointsText, x, y);

        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            // DIBUJA EL FONDO
            GameManager.paintBackground(g);

            if (paused) {
                // Establece el color de fondo en negro con transparencia
                g.setColor(new Color(0, 0, 0, 128)); // El último valor (128) es la transparencia
                g.fillRect(0, 0, getWidth(), getHeight()); // Cubre todo el fondo con el rectángulo negro transparente

                // Establece el color y la fuente para el texto
                g.setColor(Color.WHITE); // Cambia el color del texto a blanco para que contraste con el fondo
                Font pixelFont = loadFont(GameManager.font, 100f);
                g.setFont(pixelFont);

                // Centra el texto "Game Paused"
                FontMetrics metrics = g.getFontMetrics(pixelFont);
                String text = "Game Paused";
                int x = (getWidth() - metrics.stringWidth(text)) / 2; // Calcula la posición X centrada
                int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent(); // Calcula la posición Y centrada

                g.drawString(text, x, y); // Dibuja el texto centrado

            } else {
                // EJECUTAR fixedUpdate() DESPUÉS DE LA CUENTA ATRÁS
                if (!countdownActive) {

                    manager.fixedUpdate();

                } else {

                    // DIBUJA LA CUENTA ATRÁS EN LA PANTALLA
                    Font pixelFont = loadFont(GameManager.font, 150f);
                    g.setFont(pixelFont);
                    g.setColor(Color.WHITE);

                    String displayText;

                    if (countdown == 0) {

                        g.setColor(Color.RED);
                        displayText = "GO!";

                    } else {

                        displayText = String.valueOf(countdown);

                    }

                    // DIBUJAR Y CENTRAR TEXTO
                    int textWidth = g.getFontMetrics().stringWidth(displayText);
                    int x = (getWidth() - textWidth) / 2;
                    int y = getHeight() / 2;
                    g.drawString(displayText, x, y);

                }

                manager.update(g);
                drawLevel(g);
                drawPoints(g);

                // DIBUJA LA ETIQUETA DE POUNTOS GANADOS POR TOQUE
                // Dibuja todas las etiquetas de puntos en la lista
                for (PointLabel label : pointLabels) {
                    g.setColor(new Color(1.0f, 1.0f, 1.0f, label.alpha)); // Ajusta el color con opacidad
                    g.drawString(label.text, getWidth() / 2, label.yPosition);
                }

                checkFPSToRepaint();

                // SI NO HAY MUROS CAMBIA DE NIVEL
                if (Wall.muros.isEmpty()) {
                    initGameObjects();
                }
            }

        }

    }

    // CONTROLA EL FUNCIONAMIENTO DE LAS TECLAS
    private class GameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_RIGHT) {

                manager.rightPushed(true);

            } else if (keyCode == KeyEvent.VK_LEFT) {

                manager.leftPushed(true);

            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                togglePause();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_RIGHT) {

                manager.rightPushed(false);

            } else if (keyCode == KeyEvent.VK_LEFT) {

                manager.leftPushed(false);

            }

        }

    }

    // CHEQUEA LOS FPS PARA REPINTAR
    private void checkFPSToRepaint() {

        long actualTime = System.currentTimeMillis();
        double timeDiff = actualTime - this.lastUpdateTime;
        this.lastUpdateTime = actualTime;

        if (timeDiff > JFrameFight.TIME_DIFF_STANDARD) {

            repaint();

        } else {

            double timeToWait = JFrameFight.TIME_DIFF_STANDARD - timeDiff;

            try {

                Thread.sleep((long) timeToWait);

            } catch (InterruptedException ex) {

                Logger.getLogger(JFrameFight.class.getName()).log(Level.SEVERE, null, ex);

            }

            repaint();
        }

    }

    // RESETEA EL JUEGO
    public void resetGame() {

        this.nivel = 0;
        this.anchoJugador = 700;
        this.tamañoBola = 150;
        this.anchoMuro = 225;
        this.puntos = 0;

        if (!Wall.muros.isEmpty()) {
            Wall.muros.clear();
        }

        if (this.manager != null) {
            this.manager.clearGameObjects();
            this.manager = new GameManager(this);
        }

        this.countdownActive = false;
        this.countdown = 3;
        this.labelVisible = false;
        this.labelYPosition = -100;
        this.labelAlpha = 1.0f;

    }

    // MOSTRAR UN DIALOGO DE GAME OVER
    public void gameOver() {
        setVisible(false);
        if (!GameManager.stopSound) {
            // SONAR GAME OVER
            GameManager.playEffectSound(GameManager.gameovereffect);
        }

        // ABRIR DIALOGO GAME OVER
        gameOverDialog.setVisible(true);

    }

    public void togglePause() {
        this.paused = !this.paused;

        if (this.paused) {
            System.out.println("Game Paused");
            if (countdownTimer != null && countdownTimer.isRunning()) {
                countdownTimer.stop();
            }
            if (!GameManager.stopMusic) {
                GameManager.pauseBackgroundMusic();
            }
        } else {
            System.out.println("Game Resumed");
            if (countdownTimer != null && this.countdownActive) {
                countdownTimer.start();
            }
            if (!GameManager.stopMusic && !GameManager.stopSound) {
                GameManager.resumeBackgroundMusic();
            }
        }

        repaint();
    }

}
