
package Juego.com.xanxa.objectfight.game.gameobject;

import Juego.com.xanxa.objectfight.colliders.Collider;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author kgv17
 */
public class Ball extends GameObject{
    
    private Color color;
    private boolean alive = true;
    private double speedX = -1, speedY = -1;

    public Ball(double x, double y, double width, double height, Color color) {
        
        super(x, y, width, height);
        this.color = color;
        
    }
    
    /**
     * 
     * @param g
     * @return 
     */
    @Override
    public boolean paint (Graphics g){
        
        Color previousColor = g.getColor();
        g.setColor(color);
        double tmpX = col.getX();//-(col.getWidth()/2);
        double tmpY = col.getY();//-(col.getHeight()/2);
        double width = col.getWidth();
        double height = col.getHeight();
        g.fillOval((int)tmpX, (int)tmpY, (int)width, (int)height);
        
        /*
        g.drawString("Ball   x "+getX()+", y "+getY(), 10, 270);
        g.drawString("CollBl x "+col.getX()+", y "+col.getY(), 10, 300);
        */
        g.setColor (previousColor);
        super.paint(g);
        return isAlive();
        
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean behaviour() {
        
        boolean result = super.behaviour();
        double x = getX();
        double y = getY();
        x = x + speedX;
        y = y + speedY;
        updatePosition (x, y);
        return result;
        
    }
    
    /**
     *
     * @return true si está dentro del tablero. TODO
     */
    @Override
    public boolean isAlive () { return alive; }

    /**
     * Su velocidad cambia a sentido contrario.
     * @param block con lo que se ha chocado.
     * @return true si se ha chocado con arriba.
     */
    public boolean goAway(GameObject block) {
        
        boolean normalCollision = false;
        col.setDebugColor (Color.CYAN);
        double x = this.col.getX();
        double y = this.col.getY();
        double width = this.col.getWidth();
        double height = this.col.getHeight();
        
        Collider blockCollider = block.getCollider();
        if (blockCollider.collide(getBottom())) {
            
            //Hemos chocado por la abajo de la pelota.
            if (speedY > 0) {
                speedY = -speedY;
            }
            normalCollision = true;
            
        }else if (blockCollider.collide(getTop())){
            
            //hemos chocado con arriba de nuestra pelota.
            if (speedY < 0){
                speedY = -speedY;
            }
            
        }else if (blockCollider.collide(getLeft())){
            
            //Hemos chocado por la izquierda de la pelota.
            if (speedX < 0){
                speedX = -speedX;
            }
            
        }else if (blockCollider.collide(getRight())){
            
            //hemos chocado con la derecha de nuestra pelota.
            if (speedX > 0){
                speedX = -speedX;
            }
            
        }
        
        return normalCollision;
    }

    public double getSpeedX() { return this.speedX; }
    public void setSpeedX(double speedX) { this.speedX = speedX; }
    public double getSpeedY() { return this.speedY; }
    public void setSpeedY(double speedY) { this.speedY = speedY; }
    
    @Override
    public String toString() {
        
        return "Ball ["+((int)getX())+", "+((int)getY())+", "+((int)getWidth())+", "+((int)getHeight())+"]";
        
    }
    
}
