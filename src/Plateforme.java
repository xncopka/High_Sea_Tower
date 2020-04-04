import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;


public class Plateforme extends Entity {
    /**
     * Constructeur de plateforme
     */
private Color color;
private String id;

    public Plateforme(int counter) {
        Random rand = new Random();
        this.largeur =  rand.nextInt((175 - 80) + 1) + 80;
        this.hauteur = 10;
        this.y = counter * 100;
        this.x = rand.nextInt((Interface.WIDTH - 0) + 1);

    }


    public void setColor(Color color) {
        this.color = color;
    }
    public void setId(String typePlateforme) {
        this.id = typePlateforme;
    }
    public String getId() {
        return id;
    }


    public  void draw(GraphicsContext context){
        context.setFill(color);
        context.fillRect(x, y, largeur, hauteur);
    }
}


