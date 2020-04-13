import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


/**
 * Classe representant une crevette dans l'océan, proie de la meduse.
 * Si la meduse attrape une crevette, le joueur gagne des points supplémentaires
 */
public class Shrimp extends Entity {


    private double rayon;
    private Image image;


    /**
     * Constructeur de Shrimp
     * @param x position x de shrimp
     * @param y position y de shrimp
     */
    public Shrimp(double x, double y) {
        this.x = x;
        this.y = y;
        this.rayon = 15;

        image = new Image("/shrimp.png");

    }




    /**
     * Accesseur de rayon
     * @return le rayon
     */
    public double getRayon() {
        return rayon;
    }




    /**
     * Met à jour la position de la crevette
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        super.update(dt);
    }




    /**
     * Methode qui dessine la crevette
     * @param context contexte contexte graphique du canvas
     * @param fenetreY position y par rapport au niveau du jeu
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.drawImage(image, getX(), yAffiche, getRayon()*2, getRayon()*2);
    }





    /**
     * Methode qui permet de verifier si la meduse intersecte la crevette
     * Trouve le point (x, y) à l'intérieur du carré le plus proche du
     * centre du cercle et vérifie s'il se trouve dans le rayon du cercle
     * @param other la meduse
     * @return vrai ou faux, selon si la meduse intersecte le rayon du cercle
     */
    public boolean intersects(Jellyfish other) {

        double deltaX = x - Math.max(
                other.getX() - other.getLargeur() / 2,
                Math.min(x, other.getX() + other.getLargeur() / 2));
        double deltaY = y - Math.max(
                other.getY() - other.getHauteur() / 2,
                Math.min(y, other.getY() + other.getLargeur() / 2));

        return deltaX * deltaX + deltaY * deltaY < rayon * rayon;
    }


}
