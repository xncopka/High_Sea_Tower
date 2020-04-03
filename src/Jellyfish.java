import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Jellyfish extends Entity {

    private Image[] frames;
    private Image image;
    private double frameRate = 8; // 8 frame par sec
    private double tempsTotal = 0;

    private boolean parterre;

    public Jellyfish(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 50;
        this.hauteur = 50;
        this.ay = 1200;


        // Chargement des images
        frames = new Image[]{
                new Image("/jellyfish1.png"),
                new Image("/jellyfish2.png"),
                new Image("/jellyfish3.png"),
                new Image("/jellyfish4.png"),
                new Image("/jellyfish5.png"),
                new Image("/jellyfish6.png")
        };
        image = frames[0];
    }

    @Override
    public void update(double dt) {
        // Physique du personnage
        super.update(dt);

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        image = frames[frame % frames.length];
    }

    public void testCollision(Plateforme other) {
        /**
         * La collision avec une plateforme a lieu seulement si :
         *
         * - Il y a une intersection entre la plateforme et le personnage
         *
         * - La collision a lieu entre la plateforme et le *bas du personnage*
         * seulement
         *
         * - La vitesse va vers le bas (le personnage est en train de tomber,
         * pas en train de sauter)
         */
        if (intersects(other) && Math.abs(this.y + hauteur - other.y) < 10
                && vy > 0) {
            pushOut(other);
            this.vy = 0;
            this.parterre = true;
        }
    }

    public boolean intersects(Plateforme other) {
        return !( // Un des carrés est à gauche de l’autre
                x + largeur < other.x
                        || other.x + other.largeur < this.x
                        // Un des carrés est en haut de l’autre
                        || y + hauteur < other.y
                        || other.y + other.hauteur < this.y);
    }

    /**
     * Repousse le personnage vers le haut (sans déplacer la
     * plateforme)
     */
    public void pushOut(Plateforme other) {
        double deltaY = this.y + this.hauteur - other.y;
        this.y -= deltaY;
    }

    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    /**
     * Le personnage peut seulement sauter s'il se trouve sur une
     * plateforme
     */
    public void jump() {
        if (parterre) {
            vy = -600;

        }
    }

    public void left() {
        if (parterre) {
            setAX(-1200);

        }
    }

    public void right() {
        if (parterre) {
            setAX(1200);

        }
    }



    public void setAX(double ax) {
        this.ax = ax;
    }

    public void setVX(double vx) {
        this.vx = vx;
    }






    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y, largeur, hauteur);
    }
}


