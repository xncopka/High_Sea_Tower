import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Tortue extends Entity {

    private  Image[] framesRight;
    private Image imageRight;
    private  Image[] framesLeft;
    private Image imageLeft;
    private double frameRate = 8; // 8 frame par sec
    private double tempsTotal = 0;



    /**
     * Constructeur de Tortue
     *
     */
    public Tortue(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 60;
        this.hauteur = 40;
        this.vx = 100;
        this.vy = 0;
        this.ax = 0;
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



    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        if(this.vx>0) {
            context.drawImage(imageRight, x, yAffiche, largeur, hauteur);
        }else {
            context.drawImage(imageLeft, x, yAffiche, largeur, hauteur);
        }

    }

}