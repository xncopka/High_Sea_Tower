import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Shrimp extends Entity {


    private double rayon;


    // Constructeur de Bulle
    public Shrimp(double x, double y) {
        this.x = x;
        this.y = y;
        this.rayon = 15;

    }

   /* public shrimp() {
        Random rand = new Random();
        this.x = rand.nextInt((High.SeaTower.WIDTH-50 - 50) + 1) + 50;
        this.y = rand.nextInt((High.SeaTower.HEIGHT-50 - 50) + 1) + 50;
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
        context.setFill(Color.CORAL);
        context.fillOval(getX(), yAffiche, getRayon()*2, getRayon()*2);
    }




}
