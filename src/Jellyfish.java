import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represente une méduse dans l'océan
 */
public class Jellyfish extends Entity {

    private Image[] framesRight;
    private Image[] framesLeft;
    private Image image;
    private double frameRate = 8;
    private double tempsTotal = 0;

    private boolean parterre;
    private boolean parterreAcc;
    private boolean isJumping;
    private boolean aAttrape = false;
    private boolean firstInter = true;

    private boolean trampUsed = false;

    private int life = 3 ;

    /**
     * Constructeur de Jellyfish
     * @param x position x de la méduse
     * @param y position y de la méduse
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

       // Image de la méduse
        framesRight = new Image[]{
                new Image("/jellyfish1.png"),
                new Image("/jellyfish2.png"),
                new Image("/jellyfish3.png"),
                new Image("/jellyfish4.png"),
                new Image("/jellyfish5.png"),
                new Image("/jellyfish6.png")
        };
        image = framesRight[0];

        framesLeft = new Image[]{
                new Image("/jellyfish1g.png"),
                new Image("/jellyfish2g.png"),
                new Image("/jellyfish3g.png"),
                new Image("/jellyfish4g.png"),
                new Image("/jellyfish5g.png"),
                new Image("/jellyfish6g.png")
        };
    }

    /**
     * Accesseur de l'image de la meduse
     * @return l'image de la meduse
     */
    public Image getImageActuelle(){
        return this.image;
    }

    /**
     * Accesseur qui retourne vrai si la meduse touche le sol
     * @return un boolean vrai ou faux
     */
    public boolean getParterre() {
        return this.parterre;
    }

    /**
     * Accesseur
     * @return
     */
    public String getParterreFr() {
        if (getParterre()){
            return "oui";
        } else {
            return "non";
        }
    }

    /**
     * Mutateur qui change par vrai ou faux selon si la meduse touche au sol
     * @param parterre un boolean vrai ou faux
     */
    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    /**
     * Met à jour la position de la meduse
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */

    @Override
    public void update(double dt) {
        // Physique de meduse
        super.update(dt);

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        if(this.vx>0) {
            image = framesRight[frame % framesRight.length];
        }else if (this.vx < 0) {
            image = framesLeft[frame % framesLeft.length];
        } else {
            image = getImageActuelle();
        }
    }

    /**
     * La collision avec une plateforme a lieu seulement si
     * il y a une intersection entre la plateforme et le bas
     * de la meduse pour toutes les plateformes sauf dans le
     * cas ou la plateforme est solide. Celle-ci
     * aura une collision avec le haut de la meduse
     * (car celle-ci ne peut pas etre traversee)
     * @param other Plateforme
     */
    public void testCollision(Plateforme other) {

        if (intersects(other) && Math.abs(this.y + hauteur - other.y) < 10
                && other.getId().equals("plancher")) {
             pushOut(other);
             this.parterre = true;
             this.vy=0;
        }



        if (intersects(other) && Math.abs(this.y + hauteur - other.y) < 10
                && vy > 0) {

            pushOut(other);
            this.parterre = true;
            isJumping = false;


           if (other.getId().equals("plateformeRebon")) {
                this.vy *= -1.5;
                vy = Math.min(vy, -300);
                vy = Math.max(vy,-1000);

            } else {
               this.vy = 0;
           }


            if (other.getId().equals("plateformeAcc")) {
                this.parterreAcc = true;

            }

            if (other.getId().equals("plateformeTemporaire")) {
                other.setPlateformeSaute(true);
            }
        }

        if (other.getId().equals("plateformeSolide")) {
            if (intersects(other) && Math.abs(  other.y + other.getHauteur()
                    - this.y) < 10
                    && vy < 0) {
                this.vy *= -0.5;
            }
        }
    }

    /**
     * Accesseur de parterreAcc
     * @return un boolean vrai ou faux selon si la meduse touche
     * une plateforme accelerante
     */
    public boolean getParterreAcc(){
        return this.parterreAcc;
    }

    /**
     * Accesseur de is jumping
     * @return un boolean vrai ou faux selon si la meduse saute ou non
     */
    public boolean getIsJumping(){
        return this.isJumping;
    }

    /**
     * Mutateur de parterreAcc
     * @param parterreAcc un boolean qui mute plateformeAcc
     */
    public void setParterreAcc(boolean parterreAcc){
        this.parterreAcc = parterreAcc;
    }

    /**
     * Methode qui renvoit un boolean selon si la meduse intersecte un
     * autre object ou non
     * @param other n'importe quel enfant d'entite
     * @return un boolean selon si la meduse intersect avec l'object ou non
     */
    public boolean intersects(Entity other) {
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
    public void pushOut(Entity other) {
        double deltaY = this.y + this.hauteur - other.y;
        this.y -= deltaY;

    }

    /**
     * La meduse ne peut que sauter s'il se trouve sur une
     * plateforme
     */
    public void jump() {
        if (parterre) {
            vy = -600;
            isJumping = true;

        }
    }

    /**
     * La meduse ne peut qu'aller vers la gauche si elle se trouve sur une
     * plateforme
     */
    public void left() {
            setAX(-1200);
    }

    /**
     *La meduse ne peut qu'aller vers vers la droite si elle se trouve sur une
     * plateforme
     */
    public void right() {
            setAX(1200);
    }

    /**
     * La meduse arrete de se deplacer
     */
    public void stop() {
        setAX(0);
        setVX(0);
    }

    /**
     * Methode qui dessine la meduse
     * @param context contexte graphique du canvas
     * @param fenetreY position y par rapport au niveau du jeu
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.drawImage(image, x, yAffiche, largeur, hauteur);
    }

    /**
     * Methode qui indique si la meduse intersecte une crevette
     * @param other crevette
     * @return un boolean
     */
    public boolean intersects(Shrimp other) {
            return other.intersects(this);
    }

    /**
     * Methode qui set aAttrape selon si la meduse a intersecte la crevette
     * @param other crevette
     */
    public void testCollisionPiece(Shrimp other) {
        if (intersects(other)) {
            aAttrape = true;
        }
    }

    public boolean aAttrape() {
        return this.aAttrape;
    }

    /**
     * Mutateur de aAttrape
     * @param aAttrape un boolean qui indique si la crevette est attrapee
     */
    public void setAAttrape( boolean aAttrape) {
        this.aAttrape = aAttrape;
    }

    /**
     * Mutateur du nombre de vie de la meduse
     * @param life nombre de vie restante a la meduse
     */
    public void setNbVies(int life){
        this.life = life;
    }

    /**
     * Accesseur du nombre de vie de la meduse
     * @return
     */
    public int getNbVies(){
        return this.life;
    }

    /**
     * Test sil y a une collision avec la tortue.
     * Si le bas de la meduse touche la tortue, alors celle-ci peut rebondir
     * Si le haut de la meduse touche la tortue, alors la meduse tombe vers
     * le bas
     * @param other Tortue
     */
    public void testCollision(Tortue other) {
        if (intersects(other) && Math.abs(this.y + hauteur - other.y)
                < other.hauteur
                && vy > 0 ) {
            pushOut(other);
           
            this.parterre = true;
            isJumping = false;
            this.vy=0;                                               
        }
        if (intersects(other) && other.y <  this.y
                && vy < 0) {
            this.vy *= -0.1;
        }
    }

    /**
     * Test s'il y a une collision entre la meduse et la trampoline
     * @param other trampoline
     */

    public void testCollision(Trampoline other) {
        if (intersects(other) && Math.abs(this.y + hauteur - other.y) < 20
                && vy > 0) {
            pushOut(other);
            this.parterre = true;
            isJumping = false;
            this.vy=-750;
            trampUsed = true;
        }
    }

    /**
     * Methode qui renvoit un boolean selon si cest la premiere fois que la tortue
     * est touchee ou non
     * @param other tortue
     * @return un boolean vrai ou faux
     */
    public boolean getFirstInterTortue(Tortue other){
        if (firstInter && intersects(other) && vy <= 28 && other.y < this.y) {
            firstInter = false;
            return true;
        } else {
            return false;
        }
    }


    public boolean getLastInterTortue(Tortue other){
        if (!firstInter && !intersects(other)) {
            firstInter = true;
            return true;
        } else {
            return false;
        }
    }
}
