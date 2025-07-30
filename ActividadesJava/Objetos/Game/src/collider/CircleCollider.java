package collider;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * Collider para objetos de tipo círculo.
 */
public class CircleCollider implements Collider {

    private double x, y, radius;
    private Color debugColor = Color.RED;
    private Collider colliderDebug;

    public CircleCollider(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void paintDebug(Graphics g) {
        /*
        g.setColor(debugColor);
        g.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
        if (colliderDebug != null) {
            g.setColor(Color.yellow);
            g.drawRect((int) colliderDebug.getX(), (int) colliderDebug.getY(),
                    (int) colliderDebug.getWidth(), (int) colliderDebug.getHeight());
        }
         */
    }

    @Override
    public boolean collide(Collider collider) {
        // Lógica de detección de colisiones entre círculos
        switch (collider) {
            case CircleCollider otherCircle -> {
                double dx = this.x - otherCircle.x;
                double dy = this.y - otherCircle.y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                return distance <= this.radius + otherCircle.radius;
            }
            case RectangleCollider rect -> {
                return rect.collide(this);
            }
            default -> {
            }
        }
        return false;
    }

    @Override
    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean collide(Point2D point) {
        double dx = point.getX() - this.x;
        double dy = point.getY() - this.y;
        return Math.sqrt(dx * dx + dy * dy) <= radius;
    }

    @Override
    public double getX() {
        return this.x - radius;
    }

    @Override
    public double getY() {
        return this.y - radius;
    }

    @Override
    public double getHeight() {
        return radius * 2;
    }

    @Override
    public double getWidth() {
        return radius * 2;
    }

    @Override
    public void setX(double x) {
        this.x = x + radius;
    }

    @Override
    public void setY(double y) {
        this.y = y + radius;
    }

    @Override
    public void setWidth(double width) {
        this.radius = width / 2;
    }

    @Override
    public void setHeight(double height) {
        this.radius = height / 2;
    }

    @Override
    public void setDebugColor(Color c) {
        this.debugColor = c;
    }

    @Override
    public Point2D getLeft() {
        return new Point2D.Double(x, y);
    }

    @Override
    public Point2D getRight() {
        return new Point2D.Double(x + 2 * radius, y);
    }

    @Override
    public Point2D getBottom() {
        return new Point2D.Double(x, y + 2 * radius);
    }

    @Override
    public Point2D getTop() {
        return new Point2D.Double(x, y);
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }
}
