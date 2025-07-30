package gameobjects;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import ui.JFrameFight;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wall extends GameObject {

    public int lives;
    public int maxLives;
    public boolean breackable;
    public boolean movible;
    public Color color;
    public Color borderColor;
    public static List<Wall> muros = new ArrayList<>();
    private static final Random random = new Random();
    private final JFrameFight jFrameFight;

    // Variables de velocidad y dirección
    private double speed = 2; // Velocidad inicial del muro
    private double direction = 1; // 1 para moverse a la derecha, -1 para moverse a la izquierda

    public Wall(double x, double y, double width, double height, int lives, boolean breackable, boolean movible, JFrameFight jFrameFight) {

        super(x, y, width, height, false);
        this.lives = lives;
        this.maxLives = lives;
        this.breackable = breackable;
        float hue = random.nextFloat();
        float saturation = 0.7f + random.nextFloat() * 0.3f;
        float brightness = 0.5f + random.nextFloat() * 0.3f;
        this.jFrameFight = jFrameFight;
        this.movible = movible;

        if (breackable) {
            this.color = Color.getHSBColor(hue, saturation, brightness);
            this.borderColor = this.color;
            Wall.muros.add(this);
        } else {
            this.color = Color.getHSBColor(0f, 0f, 0f);
            this.borderColor = Color.WHITE;
        }

    }

    @Override
    public boolean paint(Graphics g) {
        Color tmp = g.getColor();
        Graphics2D g2d = (Graphics2D) g;
        double tmpX = getX();
        double tmpY = getY();
        double width = getWidth();
        double height = getHeight();

        if (movible) {
            // Ajustar la velocidad dependiendo del ancho de la ventana
            speed = jFrameFight.getWidth() * 0.005; // Ajusta el factor 0.005 según tus necesidades

            // Mover el muro de izquierda a derecha
            double newX = getX() + speed * direction;

            // Limitar movimiento dentro de la pantalla
            if (newX + width > jFrameFight.getWidth()) {
                newX = jFrameFight.getWidth() - width;  // Evitar que se salga del borde derecho
                direction = -1;  // Cambiar dirección a izquierda
            } else if (newX < 0) {
                newX = 0;  // Evitar que se salga del borde izquierdo
                direction = 1;  // Cambiar dirección a derecha
            }

            setX(newX);  // Actualizar la posición del muro
        }

        // Crear variaciones más oscuras para los bordes
        Color darkShade = new Color(
                Math.max(0, color.getRed() - 50),
                Math.max(0, color.getGreen() - 50),
                Math.max(0, color.getBlue() - 50),
                color.getAlpha()
        );

        // Variación aún más oscura para los bordes inferiores
        Color darkerShade = new Color(
                Math.max(0, color.getRed() - 80),
                Math.max(0, color.getGreen() - 80),
                Math.max(0, color.getBlue() - 80),
                color.getAlpha()
        );

        // Rectángulo principal
        g2d.setColor(color);
        g2d.fillRect((int) tmpX, (int) tmpY, (int) width, (int) height);

        int bevelSize = 12;  // Ajustado el tamaño del bisel

        // Borde superior (oscuro)
        int[] xPointsTop = {
            (int) tmpX,
            (int) (tmpX + width),
            (int) (tmpX + width - bevelSize),
            (int) (tmpX + bevelSize)
        };

        int[] yPointsTop = {
            (int) tmpY,
            (int) tmpY,
            (int) (tmpY + bevelSize),
            (int) (tmpY + bevelSize)
        };

        g2d.setColor(darkShade);
        g2d.fillPolygon(xPointsTop, yPointsTop, 4);

        // Borde derecho (más oscuro)
        int[] xPointsRight = {
            (int) (tmpX + width),
            (int) (tmpX + width),
            (int) (tmpX + width - bevelSize),
            (int) (tmpX + width - bevelSize)
        };

        int[] yPointsRight = {
            (int) tmpY,
            (int) (tmpY + height),
            (int) (tmpY + height - bevelSize),
            (int) (tmpY + bevelSize)
        };

        g2d.setColor(darkerShade);
        g2d.fillPolygon(xPointsRight, yPointsRight, 4);

        // Borde inferior (más oscuro)
        int[] xPointsBottom = {
            (int) tmpX,
            (int) (tmpX + width),
            (int) (tmpX + width - bevelSize),
            (int) (tmpX + bevelSize)
        };

        int[] yPointsBottom = {
            (int) (tmpY + height),
            (int) (tmpY + height),
            (int) (tmpY + height - bevelSize),
            (int) (tmpY + height - bevelSize)
        };

        g2d.setColor(darkerShade);
        g2d.fillPolygon(xPointsBottom, yPointsBottom, 4);

        // Borde izquierdo (oscuro)
        int[] xPointsLeft = {
            (int) tmpX,
            (int) tmpX,
            (int) (tmpX + bevelSize),
            (int) (tmpX + bevelSize)
        };

        int[] yPointsLeft = {
            (int) tmpY,
            (int) (tmpY + height),
            (int) (tmpY + height - bevelSize),
            (int) (tmpY + bevelSize)
        };

        g2d.setColor(darkShade);
        g2d.fillPolygon(xPointsLeft, yPointsLeft, 4);

        // Dibujar borde blanco solo si el muro NO es rompible
        g2d.setColor(this.borderColor);
        g2d.drawRect((int) tmpX, (int) tmpY, (int) width, (int) height);

        if (!this.breackable) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawRect((int) tmpX, (int) tmpY, (int) width, (int) height);
        }

        // Dibujar las líneas del bisel
        g2d.drawPolygon(xPointsTop, yPointsTop, 4);
        g2d.drawPolygon(xPointsRight, yPointsRight, 4);
        g2d.drawPolygon(xPointsBottom, yPointsBottom, 4);
        g2d.drawPolygon(xPointsLeft, yPointsLeft, 4);

        // Restaurar el color original
        g.setColor(tmp);
        return isAlive();
    }

    @Override
    public boolean isAlive() {
        return this.lives > 0;
    }

    public boolean isBreakable() {
        return this.breackable;
    }

    // MANEJAR AL SER TOCADO EL MURO
    public void touched() {

        if (this.breackable) {

            this.lives--;

            if (this.lives == 0) {
                // INCREMENTA 20 PUNTOS AL ELIMINAR EL MURO POR COMPLETO
                this.jFrameFight.incrementPoints(20);
                Wall.muros.remove(this);

            } else {
                // INCREMENTA 10 PUNTOS AL SER TOCADO
                this.jFrameFight.incrementPoints(10);

            }

            int alpha = (int) (255f * (float) this.lives / (float) this.maxLives);

            if (alpha < 0) {

                alpha = 0;

            } else if (alpha > 255) {

                alpha = 255;

            }

            this.color = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha);

        }

    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public String toString() {
        return this.color.toString();
    }

}
