
package gameobjects;

import collider.CircleCollider;
import collider.Collider;
import collider.RectangleCollider;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kgv17
 */
public abstract class GameObject{ 
    
    protected Collider col;

    public GameObject(double x, double y, double width, double height, boolean isBall) {
        
        if (isBall) {
            this.col = new CircleCollider(x, y, width/2);
        } else {
            this.col = new RectangleCollider(x, y, width, height);
        }
        
    }
    
    
    public static List<GameObject> collision(GameObject actual, List<GameObject> gameObjects){
        
        List<GameObject> result = new ArrayList<>();
        for (GameObject gameObject : gameObjects){
            
            if (actual != gameObject && actual.collides (gameObject)){
                result.add(gameObject);
            }
            
        }
        
        return result;
        
    }
    

    public boolean paint (Graphics g){
        
        this.col.paintDebug (g);
        return true;
        
    }
    
    public boolean behaviour (){ return isAlive(); }
    
    public abstract boolean isAlive ();
    
    public boolean collides (GameObject checkCollision){

        boolean collides = this.col.collide(checkCollision.getCollider());
        
        return collides;
        
    }

    public Collider getCollider() { return this.col; }

    public double getX (){ return this.col.getX(); }
    public double getY (){ return this.col.getY(); }
    public double getWidth() { return this.col.getWidth(); }
    public double getHeight (){ return this.col.getHeight(); }
    
    public void updatePosition (double x, double y){
        
        setX(x);
        setY(y);
        
    }
    
    public void setX (double x){ this.col.setX(x); }
    public void setY (double y){ this.col.setY(y); }
    public void setWidth (double width){ this.col.setWidth(width); }
    public void setHeight (double height){ this.col.setHeight(height); }
    
    public Point2D getLeft (){
        
        double x = this.getX()-this.getWidth();
        Point2D point = new Point2D.Double(x, getY());
        return point;
        
    }
    public Point2D getRight (){
        
        double x = this.getX()+this.getWidth();
        Point2D point = new Point2D.Double(x, getY());
        return point;
        
    }
    public Point2D getBottom (){
        
        double y = this.getY()+this.getHeight();
        Point2D point = new Point2D.Double(getX(), y);
        return point;
        
    }
    public Point2D getTop (){
        
        double y = this.getY();
        Point2D point = new Point2D.Double(getX(), y);
        return point;
        
    }
    
}
