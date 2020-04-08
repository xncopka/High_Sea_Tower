import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Plateforme extends Entity {

    private Color color;
    private String id;
    public static int counter = -5;

    public Plateforme() {
        Random rand = new Random();
        this.largeur =  rand.nextInt((175 - 80) + 1) + 80;
        this.hauteur = 10;
        this.y = counter * -100;
        this.x = rand.nextInt((Interface.WIDTH - 0) + 1);
        counter ++;

    }

    public Color getColor() {
        return this.color;
    }
    public String getId() {
        return id;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void setId(String typePlateforme) {
        this.id = typePlateforme;
    }

public boolean outsideLimite(double fenetreY){
    if(y > y-fenetreY){
        return true;
    } else return false;

}

    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
            context.setFill(color);
            context.fillRect(x, yAffiche, largeur, hauteur);
    }

    @Override
    public void update(double dt) {
        y = y;
    }

}

