import javafx.scene.canvas.GraphicsContext;

/**
 * Classe abstraite entité
 */
public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;

    protected double vx, vy;
    protected double ax, ay;

    /**
     * Met à jour la position et la vitesse du modele
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {

        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        // Force à rester dans les bornes de l'écran
        if (x + largeur > HighSeaTower.WIDTH || x < 0) {
            vx *= -0.5;
        }

        x = Math.min(x, HighSeaTower.WIDTH - largeur);
        x = Math.max(x, 0);
        if (dt == 0) {
            y = Math.min(y, HighSeaTower.HEIGHT - hauteur);
        }
    }
    /**
     * Méthode abstraite pour dessiner la forme
     * @param surface surface de dessin
     */
    /**
     * Méthode abstraite pour dessiner le modèle dans
     * le contexte graphique du canvas
     * @param context contexte graphique du canvas
     * @param fenetreY position y par rapport au niveau du jeu
     */
    public abstract void draw(GraphicsContext context, double fenetreY);

    /**
     * Accesseur de la position x du modèle
     * @return position x
     */

    public double getX() {
        return this.x;
    }
    /**
     * Accesseur de la position y du modèle
     * @return position y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Accesseur de la largeur du modèle
     * @return largeur du modèle
     */
    public double getLargeur() {
        return this.largeur;
    }

    /**
     * Accesseur de la hauteur du modèle
     * @return hauteur du modèle
     */
    public double getHauteur() {
        return this.hauteur;
    }
    /**
     * Accesseur de la composante x de la vitesse du modèle
     * @return composante x de la vitesse
     */
    public double getVX() {
        return this.vx;
    }

    /**
     * Mutateur de la position x du modèle
     * @param x position x du modèle
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Mutateur de la position y du modèle
     * @param y position y du modèle
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Mutateur de la largeur du modèle
     * @param largeur du modèle
     */
    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    /**
     * Mutateur de la composante x de la vitesse du modèle
     * @param vx composante x de la vitesse
     */
    public void setVX(double vx) {
        this.vx = vx;
    }

    /**
     * Mutateur de la composante x de l'accélération du modèle
     * @param ax composante x de l'accélération
     */

    public void setAX(double ax) {
        this.ax = ax;
    }
}

