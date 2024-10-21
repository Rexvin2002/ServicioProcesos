
package Juego.com.xanxa.objectfight.game.gameobject;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author kgv17
 */
public class Wall extends GameObject {
    
    int lives;
    int maxLives;
    Color color;
    Color borderColor;
    
    public Wall(double x, double y, double width, double height, int lives, Color color) {
        
        super(x, y, width, height);
        this.lives = lives;
        this.maxLives = lives;
        this.color = color;
        this.borderColor  = color;
        
    }

    @Override
    public boolean paint(Graphics g) {
        
        Color tmp = g.getColor();
        g.setColor (color);
        double tmpX = getX();//-(col.getWidth()/2);
        double tmpY = getY();//-(col.getHeight()/2);
        double width = getWidth();
        double height = getHeight();
        g.fillRect((int)tmpX, (int)tmpY, (int)width, (int)height);
        g.setColor(borderColor);
        g.drawRect((int)tmpX, (int)tmpY, (int)width, (int)height);
        /*
        g.drawString("Wall   x "+getX()+", y "+getY(), 10, 330);
        g.drawString("CollWa x "+col.getX()+", y "+col.getY(), 10, 350);
        */
        super.paint(g);
        g.setColor (tmp);
        return isAlive();
       
    }

    @Override
    public boolean isAlive() {  return lives > 0;  }

    /**
     * Ha sido tocada por una Ball
     */
    public void touched() {
        
        lives--;
        int alpha = (int)(255f*(float)lives/(float)maxLives);
        
        if (alpha < 0)
            alpha = 0;
        else if (alpha > 255)
            alpha = 255;
        
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        //System.out.println ("Touched "+lives);
        
    }

    public Color getBorderColor() { return this.borderColor; }
    public void setBorderColor(Color borderColor) {  this.borderColor = borderColor;  }

    @Override
    public String toString() {
        
        return "Wall ["+((int)getX())+", "+((int)getY())+", "+((int)getWidth())+", "+((int)getHeight())+"]";
        
    }
    
}
