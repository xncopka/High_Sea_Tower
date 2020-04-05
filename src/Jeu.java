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

    // Largeur, hauteur du niveau
    private double width = 350, height = 480;

    // Origine de la fenêtre
    private double fenetreX = 0, fenetreY = 0;

    // Entités dans le jeu
    private Jellyfish jellyfish;
    private Bulle[][] bulles;
    ArrayList<Plateforme> plateformes= new ArrayList<Plateforme>();

    // Score du jeu
    private int points = 0;
    private String score = points + "m";
    // Initialiser les plateformes

    public Jeu() {

        var counter = 1;
        boolean prevCheck = true;
        while(plateformes.size() < 5){
            double random = Math.random() * 100;
            if(random <= 5) {
                if(prevCheck) {
                    Plateforme plateformeSolide = new Plateforme(counter);
                    plateformeSolide.setColor(Color.rgb(184, 15, 36));
                    plateformeSolide.setId("plateformeSolide");
                    plateformes.add(plateformeSolide);
                    prevCheck = false;

                }

            }else if( 5 < random && random<= 15 ){
                Plateforme plateformeAcc = new Plateforme(counter);
                plateformeAcc.setColor(Color.rgb(230, 221, 58));
                plateformeAcc.setId("plateformeAcc");
                plateformes.add(plateformeAcc);
                prevCheck = true;


            }else if(15< random && random<= 35 ) {
                Plateforme plateformeRebon = new Plateforme(counter);
                plateformeRebon.setColor(Color.rgb(0, 255, 51));
                plateformeRebon.setId("plateformeRebon");
                plateformes.add(plateformeRebon);
                prevCheck = true;


            }else if(35 <random && random <=100) {
                Plateforme plateformeSimple = new Plateforme(counter);
                plateformeSimple.setColor(Color.rgb(230, 134, 58));
                plateformeSimple.setId("plateformeSimple");
                plateformes.add(plateformeSimple);
                prevCheck = true;

            }
            counter++;
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
            if(p.getId() == "plateformAcc") {
            } else if(p.getId() == "plateformRebon") {
                double oldViteY = jellyfish.getVitesVer();
                double newViteY = oldViteY*1.5;
                if(Math. abs(newViteY) < 100) {
                    newViteY = -100;
                }
                jellyfish.setVitesVer(newViteY);

            } else if(p.getId() == "plateformSolide") {
                double oldViteY = jellyfish.getVitesVer();
                double newViteY = oldViteY*-1;
                jellyfish.setVitesVer(newViteY);
            };

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
