package Juego.com.xanxa.objectfight.ui;

import Juego.com.xanxa.objectfight.game.GameManager;
import Juego.com.xanxa.objectfight.game.gameobject.Ball;
import Juego.com.xanxa.objectfight.game.gameobject.GameObject;
import Juego.com.xanxa.objectfight.game.gameobject.Player;
import Juego.com.xanxa.objectfight.game.gameobject.Wall;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 *
 * @author kgv17
 */
public class JFrameFight extends JFrame {

    private final double FPMILLIS = 165.0 / 1000.0; //165fps
    private final double TIME_DIFF_STANDARD = 1 / FPMILLIS;//Constant value.
    private long lastUpdateTime = 0;
    private GameManager manager;

    // NIVEL
    private int nivel = 0;

    // FILAS COLUMNAS
    private int alturaFila = 60;

    // JUGADOR
    private int anchoJugador = 700;
    private int alturaJugador = 20;

    // BOLA
    private int tamañoBola = 250;

    // MURO
    private int anchoMuro = 225;
    private int alturaMuro = 50;
    private int anchoMuroTotal = 232;

    public JFrameFight() throws HeadlessException {

        super();

        JPanel panel = new JPanel() {

            //private int position; TODO BORRAR TEST PARA ALUMNADO.
            @Override
            public void paint(Graphics g) {

                super.paint(g);

                initGameObjects(this);

                manager.fixedUpdate();
                manager.update(g);

                checkFPSToRepaint();

            }

            /**
             * Chequea los Frame por segundo y en caso de necesitar llama a
             * repaint o espera.
             */
            public void checkFPSToRepaint() {

                long actualTime = System.currentTimeMillis();
                double timeDiff = actualTime - lastUpdateTime;
                lastUpdateTime = actualTime;

                if (timeDiff > TIME_DIFF_STANDARD) {

                    repaint();

                } else {

                    double timeToWait = TIME_DIFF_STANDARD - timeDiff;
                    new Thread() {
                        @Override

                        public void run() {

                            super.run();

                            try {

                                Thread.sleep((long) timeToWait);

                            } catch (InterruptedException ex) {

                                Logger.getLogger(JFrameFight.class.getName()).log(Level.SEVERE, null, ex);

                            }

                            repaint();
                        }

                    }.start();

                }

            }

        };

        panel.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

                int keyCode = e.getKeyCode(); //TODO vas por aquí no se llama al keypressed desde el panel.

                if (keyCode == KeyEvent.VK_RIGHT) {

                    JFrameFight.this.manager.rightPushed(true);

                } else if (keyCode == KeyEvent.VK_LEFT) {

                    JFrameFight.this.manager.leftPushed(true);

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_RIGHT) {

                    JFrameFight.this.manager.rightPushed(false);

                } else if (keyCode == KeyEvent.VK_LEFT) {

                    JFrameFight.this.manager.leftPushed(false);

                }

            }

        });

        //Para que se puedan escuchar las teclas:
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there

        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true); // <-- the title bar is removed here
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, pantalla.width, pantalla.height);
        this.setVisible(true);

    }

    public void initGameObjects(JPanel panel) {

        double ancho = this.getWidth();
        double altura = this.getHeight();

        if (manager == null) {
            manager = new GameManager();
        }

        if (Wall.muros.isEmpty()) {

            // NIVEL
            nivel++;

            anchoJugador -= 100;
            tamañoBola -= 25;
            anchoMuro -= 35;

            manager.clearGameObjects(); // Limpiar los objetos actuales

            // Crear una bola en el centro
            double ballX = (ancho - tamañoBola) / 2;
            double ballY = (int) ((altura - tamañoBola) / 1.1);
            Ball bola = new Ball(ballX, ballY, tamañoBola, tamañoBola, Color.RED);
            bola.setSpeedX(2 + nivel); // Aumenta la velocidad de la bola con cada nivel
            bola.setSpeedY(2 + nivel);
            manager.addGameObject(bola);

            // Crear nuevos muros con mayor cantidad según el nivel
            double murosPorFila = Math.floor(ancho / anchoMuro); // Número de muros por fila
            double separacion = (ancho - (murosPorFila * anchoMuro)) / (murosPorFila + 1); // Separación inicial

            // Verificar si se superponen y ajustar el número de muros por fila
            while (murosPorFila * anchoMuro + (murosPorFila + 1) * separacion > ancho) {
                murosPorFila--; // Reducir el número de muros por fila
                separacion = (ancho - (murosPorFila * anchoMuro)) / (murosPorFila + 1); // Recalcular separación
            }

            int numRows = 1; // Agrega filas de muros en cada nivel
            int rowHeight = alturaFila;

            for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
                for (double i = 0, e = separacion; i < murosPorFila; i++, e += anchoMuro + separacion) {
                    Wall muro = new Wall(e, 100 + (rowIndex * rowHeight), anchoMuro, alturaMuro, 1, Color.GREEN);
                    muro.setBorderColor(Color.BLACK);
                    manager.addGameObject(muro);
                }
            }

            // Crear el jugador
            double playerX = (ancho - anchoJugador) / 2;
            double playerY = altura - alturaJugador;
            Player jugador = new Player(playerX, playerY, anchoJugador, alturaJugador, Color.WHITE);
            manager.addGameObject(jugador);
        }

        manager.setGameZone(new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
        panel.setBackground(Color.DARK_GRAY);
    }

}
