import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

 /**
  * Classe qui représente une tortue dans l'océan qui est un
  * ennemi de la méduse.Si la méduse touche 3 fois la tortue,
  * la meduse meurt.
  */
public class Tortue extends Entity {

    private  Image[] framesRight;
    private Image imageRight;
    private  Image[] framesLeft;
    private Image imageLeft;
    private double frameRate = 8;
    private double tempsTotal = 0;



    /**
     * Constructeur de Tortue
     *
     */
    public Tortue(double x, double y) {
        //Position x de la tortue
        this.x = x;
        //Position y de la tortue
        this.y = y;
        //largeur de la tortue
        this.largeur = 60;
        //hauteur de la tortue
        this.hauteur = 40;
        //composante x de la vitesse de la tortue
        this.vx = 50;
        //composante y de la vitesse de la tortue
        this.vy = 0;
        //composante x de l'acceleration de la tortue
        this.ax = 0;
        //composante y de l'acceleration de la tortue
        this.ay = 0;


        // Chargement des images

        framesRight = new Image[]{
                new Image("/Turtle1.png"),
                new Image("/Turtle2.png"),
                new Image("/Turtle3.png"),
                new Image("/Turtle4.png"),
        };


        framesLeft = new Image[]{
                new Image("/Turtle5.png"),
                new Image("/Turtle6.png"),
                new Image("/Turtle7.png"),
                new Image("/Turtle8.png"),
        };


        imageRight = framesRight[0];
        imageLeft = framesLeft[0];

    }


     /**
      * Met à jour la position de la méduse, sa vitesse et son image
      * @param deltaTime Temps écoulé depuis le dernier update() en
      * secondes
      */
    @Override
    public void update(double deltaTime) {

        // Physique du personnage
        super.update(deltaTime);

        // Mise à jour de l'image affichée
        tempsTotal += deltaTime;
        int frame = (int) (tempsTotal * frameRate);
        if(this.vx>0) {
            imageRight = framesRight[frame % framesRight.length];
        }else {
            imageLeft = framesLeft[frame % framesLeft.length];
        }
    }


     /**
      * Dessine la tortue
      * @param context contexte graphique du canvas
      * @param fenetreY origine de la fenêtre en Y
      */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        // si la tortue va vers la droite utiliser cette image
        if(this.vx>0) {
            context.drawImage(imageRight, x, yAffiche, largeur, hauteur);
        }else {
            // si la tortue va vers la gauche utiliser cette image
            context.drawImage(imageLeft, x, yAffiche, largeur, hauteur);
        }

    }

}