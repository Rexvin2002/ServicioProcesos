package collider;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public interface Collider {

    /**
     *
     * @param collider
     * @return
     */
    public boolean collide(Collider collider);

    public boolean collide(Point2D point);

    /**
     *
     * @param x
     * @param y
     */
    public void updatePosition(double x, double y);

    /**
     *
     * @return
     */
    public double getWidth();

    /**
     *
     * @return
     */
    public double getHeight();

    /**
     *
     * @return
     */
    public double getX();

    /**
     *
     * @return
     */
    public double getY();

    /**
     *
     * @param x
     */
    public void setX(double x);

    /**
     *
     * @param y
     */
    public void setY(double y);

    /**
     *
     * @param width
     */
    public void setWidth(double width);

    /**
     *
     * @param height
     */
    public void setHeight(double height);

    public void paintDebug(Graphics g);

    public void setDebugColor(Color c);

    /**
     *
     * @return
     */
    public Point2D getLeft();

    /**
     *
     * @return
     */
    public Point2D getRight();

    /**
     *
     * @return
     */
    public Point2D getBottom();

    /**
     *
     * @return
     */
    public Point2D getTop();

    public Rectangle getRectangle();

}
