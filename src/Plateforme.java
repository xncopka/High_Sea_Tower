import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.Random;

public class Plateforme extends Entity {

    private Color color;
    private String id;
    private boolean possedeTramp;
    private boolean plateformeSaute = false;


    public Plateforme(int counter) {
        Random rand = new Random();
        this.largeur =  rand.nextInt((175 - 80) + 1) + 80;
        this.hauteur = 10;
        this.y = HighSeaTower.HEIGHT - (100+10)*counter ;
        this.x = rand.nextInt(HighSeaTower.WIDTH + 1);
        this.possedeTramp = false;

    }

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


    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.setFill(color);
        context.fillRect(x, yAffiche, largeur, hauteur);
    }
}
