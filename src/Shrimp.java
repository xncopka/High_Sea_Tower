import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



/**
 * Classe representant une crevette dans l'océan, proie de la meduse.
 * Si la meduse attrape une crevette, le joueur gagne des points
 * supplémentaires
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
        this.ay = 0;
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
     * @param fenetreY origine de la fenêtre en Y
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.drawImage(image, x, yAffiche, rayon*2,
                rayon*2);
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
                other.x - other.largeur / 2,
                Math.min(x, other.x + other.largeur / 2));
        double deltaY = y - Math.max(
                other.y - other.hauteur / 2,
                Math.min(y, other.y + other.largeur / 2));

        return deltaX * deltaX + deltaY * deltaY < rayon * rayon;
    }


}
