package collider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 *
 * @author kgv17
 */
public class RectangleCollider implements Collider {

    private double x, y, width, height;
    private final Rectangle collisionRect;
    private Color debugColor = Color.RED;
    private Collider colliderDebug;

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public RectangleCollider(double x, double y, double width, double height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collisionRect = new Rectangle((int) (x), (int) (y), (int) width, (int) height);

    }
    
    // PINTA EL RECTÁNGULO COLLIDER
    @Override
    public void paintDebug(Graphics g) {
        
        /*
        g.setColor(debugColor);
        g.drawRect((int) collisionRect.getX(), (int) collisionRect.getY(),
                (int) collisionRect.getWidth(), (int) collisionRect.getHeight());

        if (colliderDebug != null) {
            
            g.setColor(Color.yellow);
            g.drawRect((int) colliderDebug.getX(), (int) colliderDebug.getY(),
                    (int) colliderDebug.getWidth(), (int) colliderDebug.getHeight());
            
        }
       */
        
    }

    // MANEJO DE COLLISIÓN
    @Override
    public boolean collide(Collider collider) {
        
        // COMPRUEBA SI HAY COLISIÓN
        boolean intersect = this.collisionRect.intersects(collider.getX(), collider.getY(), collider.getWidth(), collider.getHeight());
        this.colliderDebug = collider;
        
        // PINTA EL RECTÁNGULO DE VERDE SI HAY COLLISIÓN
        this.debugColor = intersect ? Color.GREEN : Color.MAGENTA;
        
        // DEVUELVE TRUE SI HAY COLLISIÓN
        return intersect;

    }
    
    
    /**
     * ACTUALIZA LA POSICIÓN DEL COLLIDER EN EL CENTRO DEL RECTÁNGULO
     * 
     * @param x
     * @param y
     */
    @Override
    public void updatePosition(double x, double y) {

        this.x = x;
        this.y = y;
        this.collisionRect.setLocation((int) (x - this.width / 2), (int) (y - this.height / 2)); // LO POSICIONA EN EL CENTRO

    }

    
    @Override
    public boolean collide(Point2D point) { return this.collisionRect.contains(point); }

   
    @Override
    public Rectangle getRectangle() { return this.collisionRect; }

    
    
    @Override
    public double getX() { return this.x; }
    @Override
    public double getY() { return this.y; }
    @Override
    public double getHeight() { return this.height; }
    @Override
    public double getWidth() { return this.width; }
    @Override
    public Point2D getLeft() {
        
        double x = this.getX() - this.getWidth();
        Point2D point = new Point2D.Double(x, getY());
        return point;
        
    }
    @Override
    public Point2D getRight() {
        
        double x = this.getX() + this.getWidth();
        Point2D point = new Point2D.Double(x, getY());
        return point;
        
    }
    @Override
    public Point2D getBottom() {
        
        double y = this.getY() + this.getHeight();
        Point2D point = new Point2D.Double(getX(), y);
        return point;
        
    }
    @Override
    public Point2D getTop() {
        
        double y = this.getY() - this.getHeight();
        Point2D point = new Point2D.Double(getX(), y);
        return point;
        
    }
    
    
    
    @Override
    public void setDebugColor(Color c) { this.debugColor = c; }
    @Override
    public void setX(double x) {

        this.x = x;
        this.collisionRect.setLocation((int) (x), (int) (y));

    }
    @Override
    public void setY(double y) {

        this.y = y;
        this.collisionRect.setLocation((int) (this.x), (int) (y));

    }
    @Override
    public void setWidth(double width) {

        this.width = width;
        this.collisionRect.setSize((int) width, (int) this.height);

    }
    @Override
    public void setHeight(double height) {

        this.height = height;
        this.collisionRect.setSize((int) this.width, (int) height);

    }

}
