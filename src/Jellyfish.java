import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Jellyfish extends Entity {

    private Image[] frames;
    private Image image;
    private double frameRate = 8; // 8 frame par sec
    private double tempsTotal = 0;


    private boolean parterre;


    /**
     * Constructeur de Jellyfish
     * @param x position x
     * @param y position y
     */
    public Jellyfish(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 50;
        this.hauteur = 50;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 1200;
        this.parterre = true;


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

    // Getters & Setters
    public boolean getParterre() {
        return this.parterre;
    }

    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    public String getParterreFr() {
        if (getParterre() == true){
            return "oui";
        } else {
            return "non";
        }
    }


    @Override
    public void update(double deltaTime) {
        // Physique du personnage
        super.update(deltaTime);

        // Mise à jour de l'image affichée
        tempsTotal += deltaTime;
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
            System.out.println(other.getId());
           if (other.getId().equals("plateformeRebon")) {
                this.vy *= -1.5;
            } else {
               this.vy = 0;
           }
           this.parterre = true;
        }
    }

    public boolean intersectsVert(Plateforme other) {
        return !(y + hauteur < other.y
                || other.y + other.hauteur < this.y) && !( x + largeur < other.x || other.x + other.largeur < this.x);
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


    /**
     * Jellyfish peut seulement sauter s'il se trouve sur une
     * plateforme
     */
    public void jump() {
        if (parterre) {
            vy = -600;

        }
    }

    /**
     * Jellyfish peut seulement aller a gauche s'il se trouve sur une
     * plateforme
     */
    public void left() {
        if (parterre) {
            setAX(-1200);

        }
    }

    /**
     * Jellyfish peut seulement aller a droite s'il se trouve sur une
     * plateforme
     */
    public void right() {
        if (parterre) {
            setAX(1200);

        }
    }

    /**
     * Jellyfish arrete de se deplacer
     *
     */
    public void stop() {
        setAX(0);
        setVX(0);
    }

    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        
        double yAffiche = y - fenetreY;


        
        context.drawImage(image, x, yAffiche, largeur, hauteur);
    }








}
