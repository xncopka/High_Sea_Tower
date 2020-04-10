import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Jellyfish extends Entity {

    private Image[] frames;
    private Image image;
    private double frameRate = 8; // 8 frame par sec
    private double tempsTotal = 0;


    private boolean parterre;

    private boolean parterreAcc;

    private boolean parterreTemp;

    private boolean isJumping;

    private boolean firstPlateforme;

    private boolean aAttrape = false;


    /**
     * Constructeur de Jellyfish
     *
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

    public void setImage(Image[] images) {
        this.frames = images;

    }


    // Getters & Setters
    public boolean getParterre() {
        return this.parterre;
    }

    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    public String getParterreFr() {
        if (getParterre() == true) {
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

    public void testCollision(Entity other) {
        /**
         * La collision avec une plateforme a lieu seulement si :
         *
         * - Il y a une intersection entre la plateforme et le personnage
         *
         * - La collision a lieu entre la plateforme et le *bas du personnage*
         * seulement

         */
        if (other instanceof Plateforme) {
            Plateforme plateforme = (Plateforme) other;
            if (intersects(plateforme) && Math.abs(this.y + hauteur - other.y) < 10
                    && plateforme.getId().equals("plancher")) {
                pushOut(plateforme);
                this.parterre = true;
                this.vy = 0;

            }
            if (intersects(plateforme) && Math.abs(this.y + hauteur - plateforme.y) < 10
                    && vy > 0) {
                pushOut(plateforme);

                if (plateforme.getId().equals("plateformeRebon")) {
                    this.vy *= -1.5;
                    vy = Math.min(vy, -100);

                } else if (plateforme.getId().equals("plateformeAcc")) {
                    this.parterreAcc = true;
                    this.vy = 0;
                    this.parterre = true;
                    isJumping = false;
                } else if (plateforme.getId().equals("plateformeTemporaire")) {
                    firstPlateforme = true;
                    this.vy = 0;
                    this.parterre = true;
                    isJumping = false;


                } else if(!(plateforme.getId().equals("plateformeSolide"))){
                    this.vy = 0;
                    this.parterre = true;
                    isJumping = false;
                }

                //other.y + other.hauteur - this.y
                // Math.abs( other.y - this.y) < 10

            } else if (plateforme.getId().equals("plateformeSolide")) {
                if (intersects(plateforme) && Math.abs(plateforme.y + plateforme.getHauteur() - this.y) < 10
                        && vy < 0) {
                    pushOutBas(plateforme);
                    this.vy *= -0.9;
                }
            }

        } else if (other instanceof Shrimp) {
            Shrimp shrimp = (Shrimp) other;
            if (this.intersects(shrimp)) {
                aAttrape = true;
                shrimp = null;
            }
        }

    }

    public boolean getParterreAcc() {
        return this.parterreAcc;
    }

    public void setParterreAcc(boolean parterreAcc) {
        this.parterreAcc = parterreAcc;
    }


    public boolean getIsJumping() {
        return this.isJumping;
    }


    public void setfirstPlateforme(boolean firstPlateforme) {
        this.firstPlateforme = firstPlateforme;
    }

    public boolean getfirstPlateforme() {
        return this.firstPlateforme;
    }


    public boolean intersects(Entity other) {


        if(other instanceof Plateforme){
            Plateforme plateforme = (Plateforme) other;
        return !( // Un des carrés est à gauche de l’autre
                x + largeur < plateforme.x
                        || plateforme.x + plateforme.largeur < this.x
                        // Un des carrés est en haut de l’autre
                        || y + hauteur < plateforme.y
                        || plateforme.y + plateforme.hauteur < this.y);
    } else if(other instanceof Shrimp){
            Shrimp shrimp = (Shrimp) other;
                /**
                 * Trouve le point (x, y) à l'intérieur du carré le plus proche du
                 * centre du cercle et vérifie s'il se trouve dans le rayon du cercle
                 */
                double deltaX = shrimp.x - Math.max(
                        this.getX() - this.getLargeur() / 2,
                        Math.min(other.x, this.getX() + this.getLargeur() / 2));
                double deltaY = shrimp.y - Math.max(
                        this.getY() - this.getHauteur() / 2,
                        Math.min(other.y, this.getY() + this.getLargeur() / 2));

                return Math.pow(deltaX,2) + Math.pow(deltaY,2) < Math.pow(shrimp.getRayon(), 2);
            }
    return false;
        }




    /**
     * Repousse le personnage vers le haut (sans déplacer la
     * plateforme)
     */
    public void pushOut(Plateforme other) {
        double deltaY = this.y + this.hauteur - other.y;
        this.y -= deltaY;

    }

    public void pushOutBas (Plateforme other) {
        double deltaY =  other.y + other.getHauteur() - this.y;
        this.y += deltaY;
    }


    /**
     * Jellyfish peut seulement sauter s'il se trouve sur une
     * plateforme
     */
    public void jump() {
        if (parterre) {
            vy = -600;
            isJumping = true;
            


        }
    }

    /**
     * Jellyfish peut seulement aller a gauche s'il se trouve sur une
     * plateforme
     */
    public void left() {

            setAX(-1200);


    }

    /**
     * Jellyfish peut seulement aller a droite s'il se trouve sur une
     * plateforme
     */
    public void right() {

            setAX(1200);

        
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






    public boolean aAttrape() {
        return this.aAttrape;
    }

    public void setAAttrape( boolean aAttrape) {
        this.aAttrape = aAttrape;
    }



}


