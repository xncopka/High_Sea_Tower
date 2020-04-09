import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class BlackHole extends Entity {


    private double rayon;


    // Constructeur de Bulle
    public BlackHole(double x, double y) {
        this.x = x;
        this.y = y;
        this.rayon = 15;

    }

   /* public BlackHole() {
        Random rand = new Random();
        this.x = rand.nextInt((Interface.WIDTH-50 - 50) + 1) + 50;
        this.y = rand.nextInt((Interface.HEIGHT-50 - 50) + 1) + 50;
        this.rayon = 50;

    }*/


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
        context.setFill(Color.BLACK);
        context.fillOval(getX(), yAffiche, getRayon()*2, getRayon()*2);
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
