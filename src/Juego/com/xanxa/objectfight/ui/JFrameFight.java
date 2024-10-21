
package Juego.com.xanxa.objectfight.ui;

import Juego.com.xanxa.objectfight.game.GameManager;
import Juego.com.xanxa.objectfight.game.gameobject.Ball;
import Juego.com.xanxa.objectfight.game.gameobject.GameObject;
import Juego.com.xanxa.objectfight.game.gameobject.Player;
import Juego.com.xanxa.objectfight.game.gameobject.Wall;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author kgv17
 */
public class JFrameFight extends JFrame {
    
    private final double FPMILLIS = 60.0/1000.0; //60fps
    private final double TIME_DIFF_STANDARD = 1/FPMILLIS;//Constant value.
    private long lastUpdateTime = 0;
    private GameManager manager;
    
    public JFrameFight() throws HeadlessException {
        
        super();
        
        JPanel panel = new JPanel (){
            
            //private int position; TODO BORRAR TEST PARA ALUMNADO.
            @Override
            public void paint(Graphics g) {
                
                super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
               
                /* TODO BORRAR TEST PARA ALUMNADO.
                g.setColor(new Color(255,0,0));
                g.drawOval(position, position, 20, 20);
                position++;
                */
                initGameObjects (this);
                
                manager.fixedUpdate();
                manager.update(g);
                
                checkFPSToRepaint ();
                
            }  
            
            /**
             * Chequea los Frame por segundo y en caso de necesitar llama a repaint o espera.
             */
            public void checkFPSToRepaint (){
                
                long actualTime = System.currentTimeMillis();
                double timeDiff = actualTime - lastUpdateTime;
                lastUpdateTime = actualTime;
                /*
                            60f --> 1000millis
                            1f  --> x
                
                */
                if (timeDiff > TIME_DIFF_STANDARD){ 
                    
                    repaint();
                    
                }else{
                    
                    double timeToWait = TIME_DIFF_STANDARD-timeDiff;
                    new Thread(){
                        @Override
                        
                        public void run() {
                            
                            super.run(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                            
                            try {
                                
                                Thread.sleep((long)timeToWait);
                                
                            } catch (InterruptedException ex) {
                                
                                Logger.getLogger(JFrameFight.class.getName()).log(Level.SEVERE, null, ex);
                                
                            }
                            
                            repaint();
                        }
                        
                    }.start();
                    
                }
                
            }
            
        };
        
        
        
        panel.addKeyListener(new KeyListener (){
            
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                int keyCode = e.getKeyCode(); //TODO vas por aquí no se llama al keypressed desde el panel.
                
                if (keyCode == KeyEvent.VK_RIGHT){
                    
                    JFrameFight.this.manager.rightPushed(true);
                    
                }else if (keyCode == KeyEvent.VK_LEFT){
                    
                    JFrameFight.this.manager.leftPushed(true);
                    
                }  
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                int keyCode = e.getKeyCode();
                
                if (keyCode == KeyEvent.VK_RIGHT){
                    
                    JFrameFight.this.manager.rightPushed(false);
                    
                }else if (keyCode == KeyEvent.VK_LEFT){
                    
                    JFrameFight.this.manager.leftPushed(false);
                    
                }
                
            }
        
        });
        
        //Para que se puedan escuchar las teclas:
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true); // <-- the title bar is removed here
        //this.setBounds(300, 300, 700, 400);
        this.setVisible(true);
        
    }
        
    public void initGameObjects (JPanel panel){
        
        if (manager == null){
            
            manager = new GameManager();
            
        }
        
        if (manager.getGameZone() == null){
            
            Ball bola1 = new Ball(0, 0, 50, 50, Color.RED);
            bola1.setSpeedY(50);
            Wall muro1 = new Wall(100, 100, 50, 50, 1, Color.GREEN);
            Wall muro2 = new Wall(100, 100, 50, 50, 1, Color.GREEN);
            
            GameObject gameobject = new GameObject(100, 100, 50, 50) {
                
                @Override
                public boolean isAlive() {
                    
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    
                }
                
            };
            
            Player jugador = new Player(100, 100, 50, 50, Color.GREEN);
            muro1.setBorderColor(Color.BLACK);
            
            manager.addGameObject(bola1);
            manager.addGameObject(muro1);
            manager.addGameObject(muro2);
            manager.addGameObject(jugador);

            manager.setGameZone(new Rectangle (this.getX(), this.getY(), this.getWidth(), this.getHeight()));
            panel.setBackground(Color.DARK_GRAY);
            
        }
        
    }
    
}
