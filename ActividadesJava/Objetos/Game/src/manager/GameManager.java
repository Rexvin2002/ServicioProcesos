package manager;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import gameobjects.Ball;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.Wall;
import ui.JFrameFight;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import ui.PauseMenu;

public class GameManager {

    public static Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize(); // PANTALLA
    private Rectangle gameZone; // ZONA DE JUEGO
    private JFrameFight jFrameFight; // FRAME DEL JUEGO
    private List<GameObject> gameObjects; // ALMACENAR OBJETOS
    private static BufferedImage backgroundImage; // IMAGEN DE FONDO

    // PAUSA
    private boolean isPaused = false;

    // URLS
    // IMG
    public static String background = "src\\img\\background.jpg";
    public static String icon = "src\\img\\icon.png";
    public static String heart = "src\\img\\heart.png";
    public static String gameover = "src\\img\\gameover.png";
    public static String victory = "src\\img\\victory.gif";

    //  AUDIO
    public static String ballhit = "src\\audio\\ball_hit_4.wav";
    public static String gameovereffect = "src\\audio\\gameoverEffect.wav";
    public static String brickhit = "src\\audio\\brick_hit_1.wav";
    public static String music = "src\\audio\\background_music_2.wav";
    public static String count = "src\\audio\\count.wav";
    public static String nextlevel = "src\\audio\\nextlevel.wav";
    public static String lesslife = "src\\audio\\lesslife.wav";
    public static String ballmovement = "src\\audio\\ball_movement_1.wav";
    public static String pointsEarned = "src\\audio\\pointsEarned_2.wav";

    // FONTS
    public static String font = "src\\font\\PixelFont.ttf";
    public static String fontBold = "src\\font\\PixelFontBold.ttf";

    // PUNTOS JUGADOR
    int points = 0;

    // TECLAS
    double rightPushed = 0;
    double leftPushed = 0;

    // FALLOS
    private int touchCount = 0;
    public static final int MAX_TOUCHES = 5;

    // MUSICA DE FONDO
    public static Clip backgroundMusic;
    private static long backgroundMusicPosition = 0;
    private static final Map<String, Clip> soundEffects = new HashMap<>();
    private static final Map<String, Long> soundEffectPositions = new HashMap<>();

    public static boolean stopMusic;
    public static boolean stopSound;

    // CONSTRUCTOR
    public GameManager(JFrameFight jFrameFight) {

        this.jFrameFight = jFrameFight;
        this.gameObjects = new ArrayList<>();
        this.gameZone = new Rectangle(0, 0, GameManager.pantalla.width, GameManager.pantalla.height);

        try {

            GameManager.backgroundImage = ImageIO.read(new File(GameManager.background));

        } catch (IOException e) {

            System.err.println("\nError: " + e.getMessage());

        }

    }

    // GAMEZONE
    public Rectangle getGameZone() {
        return this.gameZone;
    }

    public void setGameZone(Rectangle gameZone) {
        this.gameZone = gameZone;
    }

    // BACKGROUND IMAGE
    public static void paintBackground(Graphics g) {

        if (GameManager.backgroundImage != null) {

            g.drawImage(GameManager.backgroundImage, 0, 0, GameManager.pantalla.width, GameManager.pantalla.height, null);

        }

    }

    public static BufferedImage getBackgroundImage() {
        return GameManager.backgroundImage;
    }

    // Inicializa y reproduce la música de fondo en bucle
    public static void initBackgroundMusic(String url) {
        try {
            File musicFile = new File(url);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al cargar la música de fondo: " + e.getMessage());
        }
    }

    public static void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusicPosition = backgroundMusic.getMicrosecondPosition();
            backgroundMusic.stop();
        }
    }

    public static void resumeBackgroundMusic() {
        if (backgroundMusic != null && !backgroundMusic.isRunning()) {
            backgroundMusic.setMicrosecondPosition(backgroundMusicPosition);
            backgroundMusic.start();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    // Reproduce un efecto de sonido
    public static void playEffectSound(String url) {
        try {
            File soundFile = new File(url);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Guardar el Clip y su posición inicial
            soundEffects.put(url, clip);
            soundEffectPositions.put(url, 0L);

            // Cerrar el clip automáticamente cuando termine
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                    soundEffects.remove(url);
                    soundEffectPositions.remove(url);
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir el sonido: " + e.getMessage());
        }
    }

    // Pausa el efecto de sonido
    public static void pauseEffectSound(String url) {
        Clip clip = soundEffects.get(url);
        if (clip != null && clip.isRunning()) {
            long position = clip.getMicrosecondPosition();
            soundEffectPositions.put(url, position);
            clip.stop();
        }
    }

    // Reanuda el efecto de sonido
    public static void resumeEffectSound(String url) {
        Clip clip = soundEffects.get(url);
        Long position = soundEffectPositions.get(url);

        if (clip != null && position != null && !clip.isRunning()) {
            clip.setMicrosecondPosition(position);
            clip.start();
        }
    }

    // Detener el efecto de sonido
    public static void stopEffectSound(String url) {
        Clip clip = soundEffects.get(url);
        if (clip != null) {
            clip.stop();
            clip.close();
            soundEffects.remove(url);
            soundEffectPositions.remove(url);
        }
    }

    // TECLAS
    public void rightPushed(boolean pushed) {
        this.rightPushed = pushed ? 1 : 0;
    }

    public void leftPushed(boolean pushed) {
        this.leftPushed = pushed ? 1 : 0;
    }

    // OBJETOS
    // AÑADIR OBJETOS
    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    // PINTA LOS OBJETOS
    public void update(Graphics g) {

        for (GameObject enemy : this.gameObjects) {

            enemy.paint(g);

        }

    }

    // ACTUALIZA LOS OBJETOS PINTADOS
    public void fixedUpdate() {

        boolean isInside = true;
        List<Player> players = new ArrayList<>();

        for (int i = this.gameObjects.size() - 1; i >= 0; i--) {

            GameObject actual = gameObjects.get(i);
            actual.behaviour();

            List<GameObject> collisions = GameObject.collision(actual, this.gameObjects);
            boolean isInsideTmp = solveCollision(actual, collisions);

            if (!isInsideTmp) {
                isInside = isInsideTmp;
            }
            if (actual instanceof Player player) {

                player.updateSpeed(this.rightPushed, this.leftPushed);
                players.add(player);

            }

            if (!actual.isAlive()) {
                this.gameObjects.remove(i);
            }

        }

        // MANEJA LOS FALLOS DEL JUGADOR
        if (!isInside && !players.isEmpty()) {

            for (Player player : players) {
                player.touched();
            }

        }

    }

    // OBTENER MUROS
    public List<Wall> getWalls() {

        List<Wall> walls = new ArrayList<>();

        for (GameObject obj : this.gameObjects) {

            if (obj instanceof Wall wall) {
                walls.add(wall);
            }

        }

        return walls;

    }

    // MANEJA LA COLLISION DE LOS OBJETOS
    private boolean solveCollision(GameObject actual, List<GameObject> collided) {

        boolean inside = true;

        if (actual instanceof Ball ball) {

            for (GameObject gameObject : collided) {

                switch (gameObject) {
                    case Wall block -> {
                        if (!GameManager.stopSound) {

                            if (block.breackable) {
                                GameManager.playEffectSound(GameManager.brickhit);
                                GameManager.playEffectSound(GameManager.pointsEarned);
                            }

                        }

                        if (block.breackable) {
                            block.touched();
                        }

                        ball.goAway(block);

                        if (ball.getSpeedY() < 0) {
                            ball.setSpeedY(-ball.getInitialSpeedY());
                        } else {
                            ball.setSpeedY(ball.getInitialSpeedY());
                        }

                        if (ball.getSpeedX() < 0) {
                            ball.setSpeedX(-ball.getInitialSpeedX());
                        } else {
                            ball.setSpeedX(ball.getInitialSpeedX());
                        }

                    }

                    case Player player -> {

                        if (ball.goAway(player)) {
                            if (!GameManager.stopSound) {
                                GameManager.playEffectSound(GameManager.ballhit);
                            }

                            double newSpeedY = -(Math.abs(ball.getInitialSpeedY() * 1.5));
                            ball.setSpeedY(newSpeedY);

                            double speedXPlayer = player.getSpeedX();
                            ball.setSpeedX(ball.getSpeedX() + (speedXPlayer * 0.2));

                        }

                    }

                    default -> {

                        if (ball.getSpeedY() < 0) {
                            ball.setSpeedY(-ball.getInitialSpeedY());
                        } else {
                            ball.setSpeedY(ball.getInitialSpeedY());
                        }

                        if (ball.getSpeedX() < 0) {
                            ball.setSpeedX(-ball.getInitialSpeedX());
                        } else {
                            ball.setSpeedX(ball.getInitialSpeedX());
                        }

                    }

                }

            }

            inside = checkBallInside(ball);

        } else if (actual instanceof Wall) {
            this.points++;
        }

        return inside;

    }

    // COMPROBAR QUE LA BOLA ESTA DENTRO DE LA ZONA DE JUEGO
    private boolean checkBallInside(Ball ball) {
        boolean isInside = true;

        if (!this.gameZone.contains(ball.getCollider().getRectangle())) {
            double ballSpeedX = ball.getSpeedX();
            double ballSpeedY = ball.getSpeedY();

            // Verifica si la bola toca la parte inferior
            if (ball.getBottom().getY() > GameManager.pantalla.height && ballSpeedY > 0) {
                ball.setSpeedY(-ballSpeedY);
                isInside = false;
                objectTouched(); // Incrementa el contador de fallos
            }

            // Verifica si la bola toca la izquierda, arriba o derecha
            if (ball.getLeft().getX() < 0 && ballSpeedX < 0) {
                ball.setSpeedX(-ballSpeedX);
            } else if (ball.getTop().getY() < 0 && ballSpeedY < 0) {
                ball.setSpeedY(-ballSpeedY);
            } else if (ball.getRight().getX() > GameManager.pantalla.width && ballSpeedX > 0) {
                ball.setSpeedX(-ballSpeedX);
            }
        }

        return isInside;
    }

    // ELIMINAR TODOS LOS OBJETOS
    public void clearGameObjects() {
        this.gameObjects.clear();
    }

    // MANEJO DE FALLOS
    public void setTouchCount(int touchCount) {
        this.touchCount = touchCount;
    }

    // INCREMENTA LOS FALLOS
    public void objectTouched() {

        this.touchCount++;

        // SI SON 5 FALLOS GAME OVER
        if (this.touchCount >= GameManager.MAX_TOUCHES) {

            this.jFrameFight.gameOver();

        }

    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void togglePause() {
        if (isPaused) {
            resume();
        } else {
            pause();
            showPauseMenu();
        }
    }

    private void showPauseMenu() {
        PauseMenu pauseMenu = new PauseMenu(jFrameFight, true);
        pauseMenu.setVisible(true);
    }

}
