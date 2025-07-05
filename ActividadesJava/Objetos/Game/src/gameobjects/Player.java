package gameobjects;

import manager.GameManager;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;

public class Player extends GameObject {

    private Color color;
    private Color borderColor;
    private double speedX = 0;
    private static final double BASE_SPEED_FACTOR = 0.006; // Factor ajustable
    private static final double MAX_SPEED = BASE_SPEED_FACTOR * Math.sqrt(Math.pow(GameManager.pantalla.width, 2) + Math.pow(GameManager.pantalla.height, 2));
    private Timer blinkTimer;

    public Player(double x, double y, double width, double height, Color color) {
        super(x, y, width, height, false);
        this.color = color;
        this.borderColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 150);
    }

    @Override
    public boolean paint(Graphics g) {
        Color tmpColor = g.getColor();
        g.setColor(this.color);
        g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        g.setColor(this.borderColor);
        g.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        g.setColor(tmpColor);
        super.paint(g);
        return true;
    }

    @Override
    public boolean behaviour() {
        super.behaviour();
        double newX = getX() + this.speedX;
        int dimension = (int) GameManager.pantalla.getWidth();
        newX = Math.max(0, Math.min(newX, dimension - getWidth()));
        setX(newX);
        return true;
    }

    @Override
    public boolean isAlive() {
        return this.color.getAlpha() > 0;
    }

    public void updateSpeed(double rightPushed, double leftPushed) {
        if (rightPushed > 0) this.speedX = MAX_SPEED;
        else if (leftPushed > 0) this.speedX = -MAX_SPEED;
        else this.speedX = 0;
    }

    public void touched() {
        if (!GameManager.stopSound) {
            GameManager.playEffectSound(GameManager.lesslife);
        }
        
        if (!Heart.hearts.isEmpty()) {
            Heart.hearts.remove(Heart.hearts.size() - 1);
        }

        if (blinkTimer != null && blinkTimer.isRunning()) {
            blinkTimer.stop();
        }

        Color originalColor = this.color;
        final int[] blinkCount = {0};

        blinkTimer = new Timer(100, e -> {
            this.color = (blinkCount[0] % 2 == 0) ? Color.RED : Color.WHITE;
            blinkCount[0]++;
            if (blinkCount[0] >= 6) {
                ((Timer) e.getSource()).stop();
                this.color = originalColor;
                this.borderColor = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha());
            }
        });
        blinkTimer.start();
    }

    @Override
    public String toString() {
        return "Player [" + ((int) getX()) + ", " + ((int) getY()) + ", " + ((int) getWidth()) + ", " + ((int) getHeight()) + "]";
    }
    
    public double getSpeedX() { return this.speedX; }
    public void setSpeedX(double speedX) { this.speedX = speedX; }
}