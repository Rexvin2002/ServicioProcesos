
package Juego.com.xanxa.objectfight.game.gameobject;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author kgv17
 */
public class Player extends GameObject{

    Color color;
    Color borderColor;
    double speedX = 0;
    double accelerationX = 0.1;
    int lessLife = 50;
    
    public Player(double x, double y, double width, double height, Color color) {
        
        super(x, y, width, height);
        this.color = color;
        this.borderColor = new Color (color.getRed(), color.getGreen(), color.getBlue(),150);
        
    }

    @Override
    public boolean paint(Graphics g) {
        
        Color tmpColor = g.getColor();
        g.setColor(color);
        double x = getX();//-(col.getWidth()/2);
        double y = getY();//-(col.getHeight()/2);
        double width = getWidth();
        double height = getHeight();
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        g.setColor(borderColor);
        g.drawRect((int)x, (int)y, (int)width, (int)height);
        
        g.setColor(tmpColor);
        
        /*
        g.drawString("Player x "+x+", y "+y, 10, 200);
        g.drawString("CollPl x "+col.getX()+", y "+col.getY(), 10, 250);
        */
        
        super.paint(g);
        return true;
        
    }

    @Override
    public boolean behaviour() {
        
        boolean result = super.behaviour();
        this.setX(this.getX()+speedX);
        return result;
        
    }

    @Override
    public boolean isAlive() {
        
        return color.getAlpha() > 0;//Morirá cuando el gameManager vea que no hay balls.
        
    }
    
    public void updateSpeed(double rightPushed, double leftPushed) {
        
        if (rightPushed > 0){
            
            speedX = 10;
            // speedX += accelerationX;
            
        }else if (leftPushed > 0){
            
            speedX = -10;
            // speedX -= accelerationX;
            
        }else{
            
            speedX = 0;
            
        }
        
    }
    
    @Override
    public String toString() {
        
        return "Player ["+((int)getX())+", "+((int)getY())+", "+((int)getWidth())+", "+((int)getHeight())+"]";
        
    }

    public void touched() {
        
        int alpha = color.getAlpha()-lessLife;
        
        if (alpha < 0){
            
            alpha = 0;
            
        }
        
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        
    }

    public double getSpeedX() { return this.speedX; }
    public void setLessLife(int lessLife) { this.lessLife = lessLife; }
    
}
