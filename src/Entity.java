import javafx.scene.canvas.GraphicsContext;


public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;

    protected double vx, vy;
    protected double ax, ay;





    /**
     * Met à jour la position et la vitesse de l'object
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {

        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        // Force à rester dans les bornes de l'écran
        if (x + largeur > HighSeaTower.WIDTH || x < 0) {
            vx *= -1;
        }
/*        if (y + hauteur > HighSeaTower.HEIGHT || y < 0) {
            vy *= -1;
        }*/
        x = Math.min(x, HighSeaTower.WIDTH - largeur);
        x = Math.max(x, 0);
        if (dt == 0) {
            y = Math.min(y, HighSeaTower.HEIGHT - hauteur);
        }
        /*y = Math.max(y, 0);*/
    }

    /**
     * Methode abstraite qui permet de dessiner l'object sur un canvas
     * @param context canvas sur lequel on dessine
     * @param fenetreY  position Y par rapport au niveau du jeu
     */
    public abstract void draw(GraphicsContext context, double fenetreY);


    /** Getter de la position x de l'object
     *
     * @return position x de l'object
     */
    public double getX() {
        return this.x;
    }

    /**
     * Getter de la position y de l'bject
     * @return position y
     */
    public double getY() {
        return this.y;
    }

    public double getLargeur() {
        return this.largeur;
    }

    /**
     * Getter de hauteur
     * @return la hauteur de l'object
     */
    public double getHauteur() {
        return this.hauteur;
    }

    public double getVX() {
        return this.vx;
    }

    public double getVY() {
        return this.vy;
    }

    public double getAX() {
        return this.ax;
    }

    public double getAY() {
        return this.ay;
    }

    /**
     * Setter de x
     * @param x position x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter de Y
     * @param y position y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Setter de largeur
     * @param largeur de l'object
     */
    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    /**
     * Setter de la vitesse de l'object
     * @param vx
     */
    public void setVX(double vx) {
        this.vx = vx;
    }

    public void setVY(double vy) {
        this.vy = vy;
    }

    /**
     * Setter de l'acceleration
     * @param ax acceleration de x
     */
    public void setAX(double ax) {
        this.ax = ax;
    }

    public void setAY(double ay) {
        this.ay = ay;
    }

}

