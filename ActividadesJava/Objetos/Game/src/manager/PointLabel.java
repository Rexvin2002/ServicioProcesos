package manager;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
public class PointLabel {

    public String text;
    public int yPosition;
    public float alpha;
    private final int LABEL_ANIMATION_SPEED = 2; // VELOCIDAD DE ANIMACIÓN

    public PointLabel(String text, int yPosition) {
        this.text = text;
        this.yPosition = yPosition;
        this.alpha = 1.0f; // Opacidad inicial
    }

    public void updatePositionAndAlpha() {
        this.yPosition -= LABEL_ANIMATION_SPEED; // Mover hacia arriba
        this.alpha -= 0.03f; // Reducir opacidad

        if (this.alpha < 0) {
            this.alpha = 0; // Asegurar que no sea menor que 0
        }
    }

    public boolean isFaded() {
        return this.alpha <= 0; // Indica si la etiqueta se desvaneció completamente
    }
}
