import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 */
public class Jeu {

    // Largeur, hauteur du niveau
    private double width = 350, height = 480;

    // Origine de la fenêtre
    private double fenetreY = 0;
    private double fenetreVY = 50;


    // Entités dans le jeu
    private Jellyfish jellyfish;
    private Bulle[][] bulles;
    ArrayList<Plateforme> plateformes= new ArrayList<Plateforme>();
    private Plateforme plancher;
    private Shrimp shrimp;
    private Tortue tortue;

    // Score du jeu


    //= (int)fenetreY + "m";

    private boolean modeDebug;

    private boolean gameOver;

    private boolean imageRight;

    private boolean firstParterreAcc = true;
    
    private int counter = 5 ;

    private static int highScore = 0;

    private String score;

    private int nbCrustaces = 0;



    private int nombreVies = 3;







    public Jeu() {




        plancher = new Plateforme(0);
        plancher.setX(0);
        plancher.setY(HighSeaTower.HEIGHT);
        plancher.setLargeur(HighSeaTower.WIDTH);
        plancher.setId("plancher");



        var counter = 1;

        while(plateformes.size() < 4) {

            plateformes.add(newPlateforme(counter));
            counter ++;
        }
        tortue = new Tortue(0, 250);

        jellyfish = new Jellyfish(width/2 - 25, height);


        bulles = new Bulle[0][0]; // pas de bulles au debut du jeu

        Random rand = new Random();
        shrimp = new Shrimp( rand.nextInt(HighSeaTower.WIDTH - 30 + 1),
                rand.nextInt(90+1));


        gameOver = false;

    }


    public void generateSurprise(int counter, boolean prevSolide) {


            Random random = new Random();
            int numero = random.nextInt(6);
            if (numero == 0) {
                Plateforme plateformeSurprise = new Plateforme(counter);
                plateformeSurprise.setColor(Color.PURPLE);
                plateformeSurprise.setId("plateformeAcc");
                plateformes.add(plateformeSurprise);


            } else if (numero == 1) {
                Plateforme plateformeSurprise = new Plateforme(counter);
                plateformeSurprise.setColor(Color.PURPLE);
                plateformeSurprise.setId("plateformeTemporaire");
                plateformes.add(plateformeSurprise);


            } else if (numero == 2) {
                Plateforme plateformeSurprise = new Plateforme(counter);
                plateformeSurprise.setColor(Color.PURPLE);
                plateformeSurprise.setId("plateformeMouvante");

                plateformeSurprise.setVX(100);
                plateformes.add(plateformeSurprise);

            } else if (numero == 3) {
                if (!prevSolide) {
                    Plateforme plateformeSurprise = new Plateforme(counter);
                    plateformeSurprise.setColor(Color.PURPLE);
                    plateformeSurprise.setId("plateformeSolide");
                    plateformes.add(plateformeSurprise);


                } else {

                    generateSurprise(counter, true);


                }
                // plateformeSurprise.setId("plateformeRebon");
            } else if (numero == 4) {
                Plateforme plateformeSurprise = new Plateforme(counter);
                plateformeSurprise.setColor(Color.PURPLE);
                plateformeSurprise.setId("plateformeRebon");
                plateformes.add(plateformeSurprise);


             } else if (numero == 5) {
                Plateforme plateformeSurprise = new Plateforme(counter);
                plateformeSurprise.setColor(Color.PURPLE);
                plateformeSurprise.setId("plateformeSimple");
                plateformes.add(plateformeSurprise);

        }


        }


        public void generationPlateformes(int counter, boolean prevSolide){

    }


    public Plateforme newPlateforme(int counter) {
        boolean prevSolide;

        if (plateformes.size()==0) {
            prevSolide = false;
        } else {
            if (plateformes.get(plateformes.size() - 1).getId() == "plateformeSolide") {
                prevSolide = true;
            } else {
                prevSolide = false;
            }
        }

        Random random = new Random();
        int pourcent = random.nextInt(101);


            if(pourcent <= 5) {
            if (!prevSolide) {
                Plateforme plateforme = new Plateforme(counter);
                plateforme.setColor(Color.rgb(184, 15, 36));
                plateforme.setId("plateformeSolide");
                return plateforme;


            } else {

                return newPlateforme( counter);
            }


        }else if( 5 < pourcent && pourcent<= 10 ){
            generateSurprise(counter, prevSolide);





        }else if(10< pourcent && pourcent<= 20 ) {
                Plateforme plateforme = new Plateforme(counter);
            plateforme.setColor(Color.rgb(230, 221, 58));
            plateforme.setId("plateformeAcc");
                return plateforme;



        }else if(20 <pourcent && pourcent <=40) {
                Plateforme plateforme = new Plateforme(counter);
            plateforme.setColor(Color.LIGHTGREEN);
            plateforme.setId("plateformeRebon");
                return plateforme;



        } else if(40 <pourcent && pourcent <=60) {
                Plateforme plateforme = new Plateforme(counter);
            plateforme.setColor(Color.PALETURQUOISE);
            plateforme.setVX(100);
            plateforme.setId("plateformeMouvante");
                return plateforme;



        } else if(60 <pourcent && pourcent <=70) {
                Plateforme plateforme = new Plateforme(counter);
            plateforme.setColor(Color.GREY);
            plateforme.setId("plateformeTemporaire");
                return plateforme;




        }  else if (70 <pourcent && pourcent <=100) {
                Plateforme plateforme = new Plateforme(counter);
            plateforme.setColor(Color.rgb(230, 134, 58));
            plateforme.setId("plateformeSimple");
            return plateforme;

        }
    return null;

    };









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
        Bulle.groupBulles(bulles, width, fenetreY + height);
    }

    public void debug() {
        if (modeDebug) {
            modeDebug = false;
        } else {
            modeDebug = true;
        }
    }

    public void removePlancher() {
        plancher = null;
    }


    public boolean getGameOver() {
        return this.gameOver;
    }

    public void imageRight() {
        imageRight = true;
    }

    public void imageLeft() {
        imageRight = false;
    }

    public void setFenetreVY(double fenetreVY) {
        this.fenetreVY = fenetreVY;
    }


    public void updateInit() {
        if (plancher != null) {
            jellyfish.testCollision(plancher);
        }
        for (Plateforme p : plateformes) {
            p.update(0);
        }
        jellyfish.update(0);
        score = -(int) fenetreY + "m";

    }

    public int getNbVies() {
        return this.nombreVies;
    }




    public void update(double dt) {

        if (jellyfish.y > HighSeaTower.HEIGHT + fenetreY) {
        //{ || nombreVies ==0) {
            gameOver = true;
            highScore = Math.max(highScore, -(int) fenetreY + nbCrustaces*500 );

        }
        if(jellyfish.intersects(tortue)){


                gameOver =true;
                highScore = Math.max(highScore, -(int) fenetreY + nbCrustaces*500 );


        }




        if (imageRight == false) {
            jellyfish.setImage(new Image[]{
                    new Image("/jellyfish1g.png"),
                    new Image("/jellyfish2g.png"),
                    new Image("/jellyfish3g.png"),
                    new Image("/jellyfish4g.png"),
                    new Image("/jellyfish5g.png"),
                    new Image("/jellyfish6g.png")
            });
        }

        if (imageRight == true) {
            jellyfish.setImage(new Image[]{
                    new Image("/jellyfish1.png"),
                    new Image("/jellyfish2.png"),
                    new Image("/jellyfish3.png"),
                    new Image("/jellyfish4.png"),
                    new Image("/jellyfish5.png"),
                    new Image("/jellyfish6.png")
            });


        }


        

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
            jellyfish.setParterreAcc(false);
          

        if (plancher != null) {
            jellyfish.testCollision(plancher);
        }


        while (plateformes.get(plateformes.size() - 1).getY() > fenetreY   ) {
            plateformes.add(newPlateforme(counter));

        }

        while (plateformes.get(0).getY() > HighSeaTower.HEIGHT + fenetreY ) {
            

                plateformes.remove(0);
        
        }


        for (Plateforme p : plateformes) {

                p.update(dt);


                // Si le personnage se trouve sur une plateforme, ça sera défini ici
                jellyfish.testCollision(p);


                if (jellyfish.getParterreAcc() == true) {
                    if (firstParterreAcc) {
                        firstParterreAcc = false;
                        setFenetreVY(3 * fenetreVY);
                    }
                }

                if (jellyfish.getParterreAcc() == false) {
                    if (firstParterreAcc == false) {
                        firstParterreAcc = true;
                        setFenetreVY(fenetreVY / 3);
                    }
                }





        }

        for (Iterator<Plateforme> iterator = plateformes.iterator(); iterator.hasNext();) {
            Plateforme plateforme = iterator.next();
            if (plateforme.getId().equals("plateformeTemporaire")) {
                if (jellyfish.getIsJumping() && jellyfish.getfirstPlateforme()) {
                    iterator.remove();
                    jellyfish.setfirstPlateforme(false);
                }
            }
        }

            if(shrimp.getY() > fenetreY + shrimp.getRayon() + HighSeaTower.HEIGHT)
            { Random rand = new Random();
                double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
                double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT - 30 + 1) + 30;
                shrimp = new Shrimp(newX, newY);
            }

        if(tortue.getY() > fenetreY  + HighSeaTower.HEIGHT)
        { Random rand = new Random();
            double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
            double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT - 30 + 1) + 30;
            tortue = new Tortue(newX, newY);
        }

            jellyfish.testCollision(shrimp);
            if(jellyfish.aAttrape()) {
                Random rand = new Random();
                double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
                double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT -30 + 1) + 30;
                shrimp = new Shrimp(newX, newY);
                //nombreVies--;
                nbCrustaces++;
                jellyfish.setAAttrape(false);            }



            shrimp.update(dt);

            tortue.update(dt);




        




            jellyfish.update(dt);



                 if (modeDebug == false) {

                     fenetreVY = (fenetreVY + 2 * dt);
                     fenetreY -= fenetreVY * dt;
                 }




            // Scrolling 75%

            if (jellyfish.y < fenetreY + 0.25 * HighSeaTower.HEIGHT) {
                fenetreY -= Math.abs(jellyfish.y - (fenetreY + 0.25 * HighSeaTower.HEIGHT));
            }


            score =nbCrustaces*500  -(int) fenetreY + "m";


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
        tortue.draw(context, fenetreY);

        shrimp.draw(context, fenetreY);

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
            context.fillRect(jellyfish.x, jellyfish.y - fenetreY, jellyfish.largeur, jellyfish.hauteur);

            context.setFont(Font.font(12));
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.fillText("Position = (" + (int)jellyfish.x + "," + Math.abs((int)jellyfish.y-HighSeaTower.HEIGHT) + ")\n"
                    + "v = (" + (int)jellyfish.vx + "," + (int)jellyfish.vy + ")\n"
                    + "a = (" + (int)jellyfish.ax + "," + (int)jellyfish.ay + ")\n"
                    + "Touche le sol : " + jellyfish.getParterreFr() + "\n"
                    + "Nombre de plateformes : " + plateformes.size() +"\n"
                    + "Mode debug :" + modeDebug  +"\n"
                            + "fenetreY :" + fenetreY  +"\n"
                            + "fenetreVY :" + fenetreVY  +"\n"
                            + "highScore : " + highScore   +"\n"
                            + "isJumping :" + jellyfish.getIsJumping() +"\n"
                            + "firstPlateformeTemp :" + jellyfish.getfirstPlateforme() +"\n"
                            + "position black Hole : (" + (int) shrimp.getX() + ", " + (int)shrimp.getY()  + ")"
                            + "nombre de crustacés: " + nbCrustaces


                    , 10, 20);

        }



        // Ajouter le score au contexte graphique
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);
        context.setTextAlign(TextAlignment.RIGHT);
        context.setFont(Font.font(12));
        context.fillText("highScore : " + highScore, HighSeaTower.WIDTH - 10, 20);
        context.fillText("nombre de vies: " + nombreVies, HighSeaTower.WIDTH-10,40 );

    }


}
