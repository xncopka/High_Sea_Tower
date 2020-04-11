import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.Random;

public class Plateforme extends Entity {

    private Color color;
    private String id;


    public Plateforme(int counter) {
        Random rand = new Random();
        this.largeur =  rand.nextInt((175 - 80) + 1) + 80;
        this.hauteur = 10;
        this.y = HighSeaTower.HEIGHT - (100+10)*counter ;
        this.x = rand.nextInt((HighSeaTower.WIDTH - 0) + 1);

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


    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        double yAffiche = y - fenetreY;
        context.setFill(color);
        context.fillRect(x, yAffiche, largeur, hauteur);
    }
}
