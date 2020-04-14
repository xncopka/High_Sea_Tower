import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



/**
 * Classe qui représente un trampoline, un objet dans le jeu
 * se situant sur une plateforme et permettant à la méduse
 * de sauter plus haut
 */
public class Trampoline extends Entity {


    private Image image;


    // Constructeur de Trampoline
    public Trampoline(double x, double y) {
        this.ay = 0;
        this.x = x;
        this.y = y;
        this.largeur = 20;
        this.hauteur = 30;


        image = new Image("/trampoline.png");

    }


    /**
     * Met à jour la position du trampoline
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    @Override
    public void update(double dt) {
        super.update(dt);
    }



    /**
     * Dessine le trampoline
     * @param context contexte graphique du canvas
     * @param fenetreY origine de la fenêtre en Y
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.drawImage(image, x, yAffiche, largeur, hauteur);
    }

}
