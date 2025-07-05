package gameobjects;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import manager.GameManager;

public class Heart extends GameObject {

    private static final int HEART_SIZE = 50;
    public static LinkedList<Heart> hearts = new LinkedList<>();
    private final Image heartImage;

    public Heart(double x, double y, double width, double height) {
        super(x, y, width, height, false);
        this.heartImage = new ImageIcon(GameManager.heart).getImage();
        Heart.hearts.add(this);
    }

    @Override
    public boolean paint(Graphics g) {
        g.drawImage(heartImage, (int) getX(), (int) getY(), HEART_SIZE, HEART_SIZE, null);
        super.paint(g);
        return true;
    }

    public static Heart addHeart(double x, double y) {
        return new Heart(x, y, HEART_SIZE, HEART_SIZE);
    }

    public static void removeHeart(Heart heart) {
        hearts.remove(heart);
    }

    @Override
    public boolean behaviour() {
        return super.behaviour();
    }

    @Override
    public boolean isAlive() {
        return hearts.contains(this);
    }

    @Override
    public String toString() {
        return "Heart [" + ((int) getX()) + ", " + ((int) getY()) + ", " + ((int) getWidth()) + ", " + ((int) getHeight()) + "]";
    }
}