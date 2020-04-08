import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 */
public class Jeu {

    // Largeur, hauteur du niveau
    private double width = 350, height = 480;

    // Origine de la fenêtre
    private double fenetreY = 0;
    private double fenetreVY;

    // Entités dans le jeu
    private Jellyfish jellyfish;
    private Bulle[][] bulles;
    private Plateforme plancher;
    Queue<Plateforme> plateformes= new LinkedList<>();

    // Score du jeu
    private int points = 0;
    private String score = points + "m";

    private boolean modeDebug;

    private boolean gameOver;

    public Jeu() {

        plancher = new Plateforme();
        plancher.setX(0);
        plancher.setY(Interface.HEIGHT);
        plancher.setLargeur(Interface.WIDTH);
        plancher.setId("plancher");





        while(plateformes.size()<5) {
            plateformes.add(newPlateforme());
        }



        jellyfish = new Jellyfish(width / 2 - 25, height);


        bulles = new Bulle[0][0]; // pas de bulles au debut du jeu

        gameOver = false;


    }

    public Plateforme newPlateforme() {
        double random = Math.random() * 100 + 1;
        Plateforme plateforme = new Plateforme();

        if (random <= 5) {
            plateforme.setColor(Color.rgb(184, 15, 36));
            plateforme.setId("plateformeSolide");

        }else if(5 <random &&random <=15) {
            plateforme.setColor(Color.rgb(230, 221, 58));
            plateforme.setId("plateformeAcc");

        }else if(15<random &&random<=35) {
            plateforme.setColor(Color.LIGHTGREEN);
            plateforme.setId("plateformeRebon");

        }else if(35 <random &&random <=100) {
            plateforme.setColor(Color.rgb(230, 134, 58));
            plateforme.setId("plateformeSimple");
        }
        return plateforme;
    };

    public boolean outsideLimite(Plateforme p){
            if( Math.abs(fenetreY - p.getY()) > 480){
                return true;
            } else {
                return false;
            }
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

    public void debug() {
        if (modeDebug) {
            modeDebug = false;
        } else {
            modeDebug = true;
        }
    }

    public void plancher() {
        plancher.setLargeur(0);
    }


    public boolean getGameOver() {
        return this.gameOver;
    }






        public void update(double dt) {

        if (jellyfish.y > Interface.HEIGHT) {
            gameOver = true;
        }


        if (dt != 0) {

            // Pour chaque groupe de bulle
            for (int i = 0; i < bulles.length; i++) {
                // Pour chaque bulles dans un groupe
                for (int j = 0; j < bulles[0].length; j++) {
                    // mettre a jour la vitesse de la bulle
                    Bulle bulle = bulles[i][j];
                    bulle.update(dt);
                }
            }

            /**
             * À chaque tour, on recalcule si le personnage se trouve parterre ou
             * non
             */

            jellyfish.setParterre(false);
        }

        jellyfish.testCollision(plancher);

        Plateforme head = this.plateformes.peek();
        if (outsideLimite(head)){
            plateformes.add(newPlateforme());
            plateformes.remove();
        };


        for (Plateforme p : plateformes) {
            p.update(dt);



            if (p.getId().equals("plateformeSolide")) {
                if (jellyfish.intersectsVert(p) && !(jellyfish.getParterre())) {
                    double vy = jellyfish.getVY();
                    jellyfish.setVY(vy * -1);
                } else if (jellyfish.intersectsVert(p) && (jellyfish.getParterre())) {
                    jellyfish.setVY(0);
                }
            }


            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            jellyfish.testCollision(p);

        }

        jellyfish.update(dt);

        fenetreVY =  (50 + 2*dt);
        fenetreY -=  fenetreVY*dt;



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
                bulle.draw(context, fenetreY);
            }
        }


        jellyfish.draw(context, fenetreY);



        for (Plateforme p : plateformes) {


            p.draw(context, fenetreY);


            if (modeDebug == true) {
                if  (jellyfish.intersects(p) && Math.abs(jellyfish.y + jellyfish.hauteur - p.y) < 10
                        && jellyfish.vy > 0) {
                    Color temp = p.getColor();
                    p.setColor(Color.YELLOW);
                    p.draw(context, fenetreY);
                    p.setColor(temp);
                }
            }
        }


        if (modeDebug == true) {
            context.setFill(Color.rgb(255, 0, 0, 0.4));
            context.fillRect(jellyfish.x, jellyfish.y, jellyfish.largeur, jellyfish.hauteur);

            context.setFont(Font.font(12));
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.fillText("Position = (" + (int)jellyfish.x + "," + (int)jellyfish.y + ")\n"
                    + "v = (" + (int)jellyfish.vx + "," + (int)jellyfish.vy + ")\n"
                    + "a = (" + (int)jellyfish.ax + "," + (int)jellyfish.ay + ")\n"
                    + "Touche le sol : " + jellyfish.getParterreFr(), 10, 20);

        }



        // Ajouter le score au contexte graphique
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);
    }


}

