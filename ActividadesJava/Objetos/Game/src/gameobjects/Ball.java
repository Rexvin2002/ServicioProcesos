package gameobjects;

import collider.Collider;
import manager.GameManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Random;

public class Ball extends GameObject {

    private final boolean alive = true;
    private final double initialSpeedX;
    private final double initialSpeedY;
    public double speedX, speedY;
    public static boolean hitBottom = false;
    public GameManager gm;

    public Ball(double x, double y, double width, double height, Color color, double initialSpeedX, double initialSpeedY) {

        super(x, y, height, width, true); 
        this.initialSpeedX = initialSpeedX;
        this.initialSpeedY = initialSpeedY;
        this.speedX = 0;
        this.speedY = initialSpeedY;

    }
    
    @Override
    public boolean paint(Graphics g) {

        Color previousColor = g.getColor();
        Random rand = new Random();
        Color randomColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        g.setColor(randomColor);
        double tmpX = col.getX();
        double tmpY = col.getY();
        double width = col.getWidth();
        double height = col.getHeight();

        g.fillOval((int) tmpX, (int) tmpY, (int) width, (int) height);

        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(5));

        g.setColor(Color.BLACK);
        g.drawOval((int) tmpX, (int) tmpY, (int) width, (int) height);

        g2d.setStroke(oldStroke);
        g.setColor(previousColor);
        super.paint(g);
        return isAlive();

    }

    @Override
    public boolean behaviour() {
        Ball.hitBottom = false;
        boolean result = super.behaviour();

        // Calcula la nueva posición de la pelota
        double newX = getX() + this.speedX;
        double newY = getY() + this.speedY;

        // Actualiza la posición de la pelota
        updatePosition(newX, newY);

        return result;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    // MANEJA LAS COORDENADAS TRAS LA COLISIÓN
    public boolean goAway(GameObject block) {
        if (!GameManager.stopSound) {
            GameManager.playEffectSound(GameManager.ballmovement);
        }

        boolean normalCollision = false;
        this.col.setDebugColor(Color.RED);

        Collider blockCollider = block.getCollider();

        if (blockCollider.collide(getBottom())) {

            this.speedY = -Math.abs(this.speedY);
            
            if (block instanceof Player) {
                normalCollision = true;
            }

        } else if (blockCollider.collide(getTop())) {

            this.speedY = Math.abs(this.speedY);
            

        } else if (blockCollider.collide(getLeft())) {

            this.speedX = Math.abs(this.speedX);

        } else if (blockCollider.collide(getRight())) {

            this.speedX = -Math.abs(this.speedX);

        }

        return normalCollision;

    }

    // TOCAR PARTE INFERIOR DE PANTALLA
    public boolean hasHitBottom() {
        return Ball.hitBottom;
    }

    public void resetHitBottom() {
        Ball.hitBottom = false;
    }

    public double getInitialSpeedX() {
        return this.initialSpeedX;
    }

    public double getInitialSpeedY() {
        return this.initialSpeedY;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    @Override
    public String toString() {

        return "Ball [" + ((int) getX()) + ", " + ((int) getY()) + ", "
                + ((int) getWidth()) + ", " + ((int) getHeight()) + "]";

    }
}
