import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Shrimp extends Entity {


    private double rayon;
    private Image image;


    // Constructeur de Bulle
    public Shrimp(double x, double y) {
        this.x = x;
        this.y = y;
        this.rayon = 15;

        image = new Image("/shrimp.png");

    }




    // Getters & Setters


    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }



    /**
     * Met à jour la position Y de la balle
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        super.update(dt);
    }




    /**
     * Dessine la bulle
     * @param context contexte graphique du canvas
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.setFill(Color.CORAL);
        context.drawImage(image, getX(), yAffiche, getRayon()*2, getRayon()*2);
    }






    public boolean intersects(Jellyfish other) {
        /**
         * Trouve le point (x, y) à l'intérieur du carré le plus proche du
         * centre du cercle et vérifie s'il se trouve dans le rayon du cercle
         */
        double deltaX = x - Math.max(
                other.getX() - other.getLargeur() / 2,
                Math.min(x, other.getX() + other.getLargeur() / 2));
        double deltaY = y - Math.max(
                other.getY() - other.getHauteur() / 2,
                Math.min(y, other.getY() + other.getLargeur() / 2));

        return deltaX * deltaX + deltaY * deltaY < rayon * rayon;
    }


}
