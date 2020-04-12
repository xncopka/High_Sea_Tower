import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Trampoline extends Entity {


    private Image image;


    // Constructeur de Bulle
    public Trampoline(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 20;
        this.hauteur = 30;


        image = new Image("/trampoline.png");

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
        context.drawImage(image, getX(), yAffiche, largeur, hauteur);
    }







}
