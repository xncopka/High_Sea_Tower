import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;

    protected double vx, vy;
    protected double ax, ay;

    protected Color color;



    /**
     * Met à jour la position et la vitesse de la balle
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        // Force à rester dans les bornes de l'écran
        if (x + largeur > Interface.WIDTH || x < 0) {
            vx *= -1;
        }
/*        if (y + hauteur > Interface.HEIGHT || y < 0) {
            vy *= -1;
        }*/
        x = Math.min(x, Interface.WIDTH - largeur);
        x = Math.max(x, 0);
        if (dt == 0) {
            y = Math.min(y, Interface.HEIGHT - hauteur);
        }
        y = Math.max(y, 0);
    }

    public abstract void draw(GraphicsContext context);


    // Getters & Setters
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getLargeur() {
        return this.largeur;
    }

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



    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public void setVX(double vx) {
        this.vx = vx;
    }

    public void setVY(double vy) {
        this.vy = vy;
    }

    public void setAX(double ax) {
        this.ax = ax;
    }

    public void setAY(double ay) {
        this.ay = ay;
    }


}

