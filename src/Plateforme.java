import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;


/**
 * Classe representant les plateformes dans le jeu
 */
public class Plateforme extends Entity {

    // couleur de la plateforme
    private Color color;

    // ID de la plateforme
    private String id;

    // Si la plateforme possede un trampoline
    private boolean possedeTramp;

    // Si on a sauté sur la plateforme
    private boolean plateformeSaute = false;


    /**
     * Constructeur de Plateforme
     * @param counter  compteur du nombre de plateformes crees afin
     * de positionner la plateforme au bon endroit 0 signifie la
     * construction du plancher
     */
    public Plateforme(int counter) {
        Random rand = new Random();
        this.largeur =  rand.nextInt((175 - 80) + 1) + 80;
        this.hauteur = 10;
        this.y = HighSeaTower.HEIGHT - (100+10)*counter ;
        this.x = rand.nextInt(HighSeaTower.WIDTH + 1);
        this.possedeTramp = false;

    }

    /**
     * Accesseurs et mutateurs des attributs
     *
     */
    public Color getColor() {
        return this.color;
    }
    public String getId() {
        return id;
    }
    public boolean getPossedeTramp(){
        return this.possedeTramp;
    }
    public boolean getPlateformeSaute() {
        return this.plateformeSaute;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setId(String typePlateforme) {
        this.id = typePlateforme;
    }
    public void setPossedeTramp(boolean possedeTramp) {
        this.possedeTramp = possedeTramp;
    }
    public void setPlateformeSaute(boolean plateformeSaute) {
        this.plateformeSaute = plateformeSaute;
    }

    /**
     * Méthode qui dessine la plateforme
     * @param context contexte graphique du canvas
     * @param fenetreY origine de la fenêtre en Y
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.setFill(color);
        context.fillRect(x, yAffiche, largeur, hauteur);
    }
}
