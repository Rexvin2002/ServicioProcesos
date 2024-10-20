/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juego.com.xanxa.objectfight.colliders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


/**
 *
 * @author Manuel
 */
public class RectangleCollider implements Collider {
    private double x,y,width,height;
    private Rectangle collisionRect;

    private Color debugColor = Color.RED;
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
        collisionRect = new Rectangle((int)(x), (int)(y), (int)width, (int)height) ;
    }
    /**
     * 
     * @param x
     * @param y 
     */
    @Override
    public void updatePosition (double x, double y)
    {
        this.x = x;
        this.y = y;
        collisionRect.setLocation((int)(x-width/2), (int)(y-height/2));
    }
    
    Collider colliderDebug;
    /**
     * TODO: Habría que pensar si queremos crear un rectangle cada vez o mejor mantenemos uno.
     * @param collider
     * @return  
     */
    @Override
    public boolean collide(Collider collider) {
       boolean intersect = collisionRect.intersects(collider.getX(), collider.getY(), collider.getWidth(), collider.getHeight());
       colliderDebug = collider;
       if (intersect)
       {
           debugColor = Color.green;
       }else
       {
           debugColor = Color.MAGENTA;
       }
       return intersect;
    }

    /**
     * 
     * @return 
     */
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setX(double x) {
        this.x = x;
        collisionRect.setLocation((int)(x), (int)(y));
    }

    @Override
    public void setY(double y) {
        this.y = y;
        collisionRect.setLocation((int)(x), (int)(y));
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
        collisionRect.setSize((int)width, (int)height);
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
        collisionRect.setSize((int)width, (int)height);
    }

    @Override
    public void paintDebug(Graphics g) {
       /* //por si queremos ver el collider.
        g.setColor(debugColor);
        g.drawRect((int)collisionRect.getX(), (int)collisionRect.getY(), 
                (int)collisionRect.getWidth(), (int)collisionRect.getHeight());
        
        if (colliderDebug != null)
        {
            g.setColor (Color.yellow);
            g.drawRect((int)colliderDebug.getX(), (int)colliderDebug.getY(), 
                (int)colliderDebug.getWidth(), (int)colliderDebug.getHeight());
        }*/
    }

    @Override
    public void setDebugColor(Color c) {
        this.debugColor = c;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Point2D getLeft ()
    {
        double x = this.getX()-this.getWidth();
        Point2D point = new Point2D.Double(x, getY());
        return point;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Point2D getRight ()
    {
        double x = this.getX()+this.getWidth();
        Point2D point = new Point2D.Double(x, getY());
        return point;
    }
    
    /**
     *
     * @return 
     */
    @Override
    public Point2D getBottom ()
    {
        double y = this.getY()+this.getHeight();
        Point2D point = new Point2D.Double(getX(), y);
        return point;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Point2D getTop ()
    {
        double y = this.getY()-this.getHeight();
        Point2D point = new Point2D.Double(getX(), y);
        return point;
    }

    @Override
    public boolean collide(Point2D point) {
        return collisionRect.contains(point);
    }

    @Override
    public Rectangle getRectangle() {
        return collisionRect;
    }
}
