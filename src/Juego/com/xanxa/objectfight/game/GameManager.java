package Juego.com.xanxa.objectfight.game;

import Juego.com.xanxa.objectfight.game.gameobject.Ball;
import Juego.com.xanxa.objectfight.game.gameobject.GameObject;
import Juego.com.xanxa.objectfight.game.gameobject.Player;
import Juego.com.xanxa.objectfight.game.gameobject.Wall;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kgv17
 */
public class GameManager {

    List<GameObject> gameObjects;
    /**
     * Ancho y alto del escenario
     */
    Rectangle gameZone;
    double rightPushed = 0;
    double leftPushed = 0;

    public GameManager() {

        super();
        this.gameObjects = new ArrayList<GameObject>();
        //this.gameZone = gameZone;

    }

    public void update(Graphics g) {

        //Moving
        for (GameObject enemy : gameObjects) {
            enemy.paint(g);
        }

    }
    
    public void fixedUpdate() {

        boolean isInside = true;
        List<Player> players = new ArrayList<>();

        for (int i = gameObjects.size() - 1; i >= 0; i--) {

            GameObject actual = gameObjects.get(i);
            actual.behaviour();
            List<GameObject> collisions = GameObject.collision(actual, gameObjects);
            boolean isInsideTmp = solveCollision(actual, collisions);

            if (!isInsideTmp) {
                isInside = isInsideTmp;
            }

            if (actual instanceof Player player) {
                player.updateSpeed(rightPushed, leftPushed);
                players.add(player);
            }

            if (!actual.isAlive()) {
                //TODO se podría hacer una animación de desaparición.
                gameObjects.remove(i);
            }

        }

        if (!isInside && !players.isEmpty()) {

            for (Player player : players) {
                player.touched();
            }

        }

    }

    /**
     *
     * @param actual
     * @param collided
     * @return
     */
    private boolean solveCollision(GameObject actual, List<GameObject> collided) {
        boolean inside = true;
        
        if (actual instanceof Ball ball) {
            
            for (GameObject gameObject : collided) {
                
                switch (gameObject) {
                    case Wall block -> {
                        block.touched();
                        ball.goAway(block);
                    }
                    case Player player -> {
                        //TODO se podría hacer que si el player se mueve la velocidad de la pelota cambiase.
                        if (ball.goAway(player)) {
                            double speedXBall = ball.getSpeedX();
                            double speedXPlayer = player.getSpeedX();
                            double speedX = speedXBall + speedXPlayer;
                            ball.setSpeedX(speedX);
                        }
                    }
                    default -> {
                    }
                    
                }
                
            }
            
            inside = checkBallInside(ball);

        } else if (actual instanceof Wall) { /*DO NOTHING*/ }
        
        return inside;
        
    }

    /**
     * Check if the ball is in the windows. 0,0 is on the top left.
     *
     * @param ball
     */
    private boolean checkBallInside(Ball ball) {
        boolean isInside = true;
        //TODO verificar que esto funciona ya que no he implementado todos los métodos.
        if (!gameZone.contains(ball.getCollider().getRectangle())) {
            Point2D ballLeft = ball.getLeft();
            if (ballLeft.getX() < 0) {
                //Se sale por la izquierda
                double ballSpeed = ball.getSpeedX();
                if (ballSpeed < 0) {
                    ballSpeed = -ballSpeed;
                    ball.setSpeedX(ballSpeed);
                }
            } else if (ball.getTop().getY() < 0) {
                //Se sale por la izquierda
                double ballSpeed = ball.getSpeedY();
                if (ballSpeed < 0) {
                    ballSpeed = -ballSpeed;
                    ball.setSpeedY(ballSpeed);
                }
            } else if (ball.getRight().getX() > gameZone.getWidth()) {
                //Fuera por la derecha (Cuidado si el rectange x no es 0 TODO)
                double ballSpeed = ball.getSpeedX();
                if (ballSpeed > 0) {
                    ballSpeed = -ballSpeed;
                    ball.setSpeedX(ballSpeed);
                }
            } else if (ball.getBottom().getY() > gameZone.getHeight()) {
                //Fuera por abajo (Cuidado si el rectange y no es 0 TODO)
                double ballSpeed = ball.getSpeedY();
                if (ballSpeed > 0) {
                    ballSpeed = -ballSpeed;
                    ball.setSpeedY(ballSpeed);
                }
                isInside = false;
            }
        }
        return isInside;
    }

    public Rectangle getGameZone() {
        return gameZone;
    }

    public void setGameZone(Rectangle gameZone) {
        this.gameZone = gameZone;
    }

    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void rightPushed(boolean pushed) {
        if (pushed) {
            rightPushed = 1;
        } else {
            rightPushed = 0;
        }
    }

    public void leftPushed(boolean pushed) {
        if (pushed) {
            leftPushed = 1;
        } else {
            leftPushed = 0;
        }
    }

}
