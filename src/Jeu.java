import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 */
public class Jeu {
    public static ArrayList<Plateforme> plateformes;
    // Largeur, hauteur du niveau
    private double width = 350, height = 480;

    // Origine de la fenêtre
    private double fenetreX = 0, fenetreY = 0;

    // Entités dans le jeu
    private Jellyfish jellyfish;
    private Bulle[][] bulles;
    private  Plateforme[] plateformes;

    // Score du jeu
    private int points = 0;
    private String score = points + "m";

    public Jeu() {
        plateformes = new Plateforme[5];
        for (int i = 0; i < plateformes.length; i++) {
            plateformes[i] = new Plateforme((double) i / plateformes.length * width, Math.random() * height);
        }

        jellyfish = new Jellyfish(width/2 - 25, height);

        bulles = new Bulle[0][0]; // pas de bulles au debut du jeu

            }





    public void jump() {
        jellyfish.jump();
    }

    public void left() {
        jellyfish.left();
    }

    public void right() {
        jellyfish.right();
    }

    public void stop() {
        jellyfish.stop();
    }

    public void groupBulles() {
        bulles = new Bulle[3][5];
        Bulle.groupBulles(bulles, width, height);
    }







    public void update(double dt) {



        if(dt != 0) {

            // Pour chaque groupe de bulle
            for (int i = 0; i < bulles.length; i++) {
                // Pour chaque bulles dans un groupe
                for (int j = 0; j < bulles[0].length; j++) {
                    // mettre a jour la vitesse de la bulle
                    Bulle bulle = bulles[i][j];
                    bulle.update(dt);
                }
                ;
            }


            /**
             * À chaque tour, on recalcule si le personnage se trouve parterre ou
             * non
             */

            jellyfish.setParterre(false);
        }

        for (Plateforme p : plateformes) {
            p.update(dt);
            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            jellyfish.testCollision(p);
        }
        jellyfish.update(dt);
    }

    public void draw(GraphicsContext context) {

        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, width, height);



        // Pour chaque groupe de bulle
        for (int i = 0; i < bulles.length ; i++) {
            // Pour chaque bulles dans un groupe
            for (int j = 0; j < bulles[0].length; j++) {
                Bulle bulle = bulles[i][j];
        // dessiner la bulle
                bulle.draw(context);
            }
        }

        jellyfish.draw(context);

        for (Plateforme p : plateformes) {
            p.draw(context);
        }



        // Ajouter le score au contexte graphique
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);
    }
}
