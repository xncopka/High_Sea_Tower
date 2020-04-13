import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


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
    private Trampoline trampoline;


    private boolean modeDebug;

    private boolean gameOver;

    private boolean firstParterreAcc = true;
    
    private int counter = 5 ;

    private static int highScore = 0;

    private String score;

    private int nbCrustaces = 0;

    private boolean[] levels = new boolean[4];

    private boolean trampInGame = false;

    private int probTrampoline;

    private boolean firstTortue=true;




    public Jeu() {

        levels[0] = true;
        tortue = null;

        trampoline = null;




        plancher = new Plateforme(0);
        plancher.setX(0);
        plancher.setY(HighSeaTower.HEIGHT);
        plancher.setLargeur(HighSeaTower.WIDTH);
        plancher.setId("plancher");



        var counter = 1;
        boolean prevSolide;
        while(plateformes.size() < 4) {
            if (plateformes.size()==0) {
                prevSolide = false;
            } else {
                prevSolide = plateformes.get(plateformes.size() - 1).getId().equals("plateformeSolide");
            }



            generatePlateforme(counter, prevSolide, 5, 0, 15, 35, 0, 0);



                counter++;
        }


        jellyfish = new Jellyfish(width/2 - 25, height);


        bulles = new Bulle[0][0]; // pas de bulles au debut du jeu

        Random rand = new Random();
        shrimp = new Shrimp( rand.nextInt(HighSeaTower.WIDTH - 30 + 1),
                rand.nextInt(90+1));

        //tortue = new Tortue(0, -30);

        gameOver = false;

    }

    public void generateSimple(int counter) {
        Plateforme plateformeSimple = new Plateforme(counter);
        plateformeSimple.setColor(Color.rgb(230, 134, 58));
        plateformeSimple.setId("plateformeSimple");
        plateformes.add(plateformeSimple);
       
    }

    public void generateAcc(int counter) {
        Plateforme plateformeAcc = new Plateforme(counter);
        plateformeAcc.setColor(Color.rgb(230, 221, 58));
        plateformeAcc.setId("plateformeAcc");
        plateformes.add(plateformeAcc);

    }

    public void generateReb(int counter) {
        Plateforme plateformeRebon = new Plateforme(counter);
        plateformeRebon.setColor(Color.LIGHTGREEN);
        plateformeRebon.setId("plateformeRebon");
        plateformes.add(plateformeRebon);

    }

    public void generateSolide(int counter) {
        Plateforme plateformeSolide = new Plateforme(counter);
        plateformeSolide.setColor(Color.rgb(184, 15, 36));
        plateformeSolide.setId("plateformeSolide");
        plateformes.add(plateformeSolide);

    }

    public void generateMouvante(int counter) {
        Plateforme plateformeMouvante = new Plateforme(counter);
        plateformeMouvante.setColor(Color.PALETURQUOISE);
        plateformeMouvante.setVX(100);
        plateformeMouvante.setId("plateformeMouvante");
        plateformes.add(plateformeMouvante);

    }

    public void generateTemporaire(int counter) {
        Plateforme plateformeTemporaire = new Plateforme(counter);
        plateformeTemporaire.setColor(Color.GREY);
        plateformeTemporaire.setId("plateformeTemporaire");
        plateformes.add(plateformeTemporaire);

    }

    public void generateSurprise(int counter, boolean prevSolide) {
        Plateforme plateformeSurprise = new Plateforme(counter);
        plateformeSurprise.setColor(Color.PURPLE);

            Random random = new Random();
            int numero;
        if(prevSolide) {
            numero = random.nextInt(6);
        } else {
            numero = random.nextInt(5);
        }

        switch (numero) {
            case 0:
                plateformeSurprise.setId("plateformeAcc");
                plateformes.add(plateformeSurprise);
                break;
            case 1:
                plateformeSurprise.setId("plateformeTemporaire");
                plateformes.add(plateformeSurprise);
                break;
            case 2:
                plateformeSurprise.setId("plateformeMouvante");
                plateformeSurprise.setVX(100);
                plateformes.add(plateformeSurprise);
                break;
            case 3:
                plateformeSurprise.setId("plateformeSimple");
                plateformes.add(plateformeSurprise);
                break;
            case 4:
                plateformeSurprise.setId("plateformeRebon");
                plateformes.add(plateformeSurprise);
                break;

            case 5:
                plateformeSurprise.setId("plateformeSolide");
                plateformes.add(plateformeSurprise);
                break;
        }
        }


        public void generatePlateforme(int counter, boolean prevSolide, int pSolide, int pSurprise, int pAcc, int pReb, int pMouv, int pTemp){
            Random random = new Random();
            int pourcent = random.nextInt(101);
            
            if(pourcent <= pSolide) {
                if (!prevSolide) {
                    generateSolide(counter);

                } else {
                    generatePlateforme(counter, prevSolide, pSolide, pSurprise, pAcc,  pReb, pMouv, pTemp);
                }



            }else if(pourcent <= pSurprise){
                generateSurprise(counter, prevSolide);



            }else if(pourcent <= pAcc) {
                generateAcc(counter);


            }else if(pourcent <= pReb) {
                generateReb(counter);


            } else if(pourcent <= pMouv) {
                generateMouvante(counter);



            } else if(pourcent <= pTemp) {
                generateTemporaire(counter);




            }  else if(pourcent <= 100) {
                generateSimple(counter);

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
        Bulle.groupBulles(bulles, width, fenetreY + height);
    }

    public void debug() {
        modeDebug = !modeDebug;
    }

    public void removePlancher() {
        plancher = null;
    }


    public boolean getGameOver() {
        return this.gameOver;
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

    public String getLevel() {
        if (levels[0]) {
           return "Facile";
        } else if (levels[1]) {
            return "Moyen";
        } else if (levels[2]) {
            return "Difficile";
        } else {
            return "Hardcore";
        }
    }


    public boolean getFirstInterTortue() {
        return jellyfish.getFirstInterTortue(tortue);
    }



    public boolean getLastInterTortue() {
        return jellyfish.getLastInterTortue(tortue);
    }

    public int getNbVies() {
        return jellyfish.getNbVies();
    }
    public void setNbVies(int life) {
        jellyfish.setNbVies(life);
    }


     public boolean getTortue() {
        if (tortue == null) {
            return false;
        } else {
            return true;
        }
     }

















    public void update(double dt) {
    





        if ( (jellyfish.y > HighSeaTower.HEIGHT + fenetreY) || jellyfish.getNbVies() == 0 ) {

            gameOver = true;
            highScore = Math.max(highScore, -(int) fenetreY + nbCrustaces * 500);

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


        while (plateformes.get(plateformes.size() - 1).getY() > fenetreY) {

            boolean prevSolide = plateformes.get(plateformes.size() - 1).getId().equals("plateformeSolide");

            if (levels[0]) {
                generatePlateforme(counter, prevSolide, 5, 0, 15, 35, 0, 0);
            } else if (levels[1]) {
                generatePlateforme(counter, prevSolide, 5, 15, 30, 45, 60, 75);
            } else if (levels[2]) {
                generatePlateforme(counter, prevSolide, 10, 20, 35, 50, 65, 80);
            } else if (levels[3]) {
                generatePlateforme(counter, prevSolide, 0, 100, 0, 0, 0, 0);
            }




            counter++;
        }

        if (levels[2] && firstTortue) {
            tortue = new Tortue(0, plateformes.get(plateformes.size()-1).getY()-70);
            firstTortue = false;
        }

        while (plateformes.get(0).getY() > HighSeaTower.HEIGHT + fenetreY) {
            plateformes.remove(0);
        }


        for (Plateforme p : plateformes) {

            p.update(dt);


            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            jellyfish.testCollision(p);


            if (jellyfish.getParterreAcc()) {
                if (firstParterreAcc) {
                    firstParterreAcc = false;
                    setFenetreVY(3 * fenetreVY);
                }
            }

            if (!jellyfish.getParterreAcc()) {
                if (!firstParterreAcc) {
                    firstParterreAcc = true;
                    setFenetreVY(fenetreVY / 3);
                }
            }


            if (p.getId().equals("plateformeMouvante") && p.getPossedeTramp() ) {
                trampoline.setVX(p.getVX());
            }
            


        }

        for (Iterator<Plateforme> iterator = plateformes.iterator(); iterator.hasNext(); ) {
            Plateforme plateforme = iterator.next();
            if (plateforme.getId().equals("plateformeTemporaire")) {

                if (jellyfish.getIsJumping() && plateforme.getPlateformeSaute()) {

                    if(plateforme.getPossedeTramp()) {
                        trampoline = null;
                        trampInGame = false;
                    }
                    iterator.remove();


                }
            }
        }

        if (shrimp.getY() > fenetreY + shrimp.getRayon() + HighSeaTower.HEIGHT) {
            Random rand = new Random();
            double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
            double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT - 30 + 1) - 30;
            shrimp = new Shrimp(newX, newY);
        }

        jellyfish.testCollisionPiece(shrimp);
        if (jellyfish.aAttrape()) {
            Random rand = new Random();
            double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
            double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT - 30 + 1) - 30;
            shrimp = new Shrimp(newX, newY);
            nbCrustaces++;
            jellyfish.setAAttrape(false);
        }

        shrimp.update(dt);


        if(tortue != null) {
            jellyfish.testCollision(tortue);
            if (tortue.getY() > fenetreY + HighSeaTower.HEIGHT) {
                Random rand = new Random();
                double newX = rand.nextInt(HighSeaTower.WIDTH - 60 + 1);
                double newY = fenetreY - rand.nextInt(5)*110 -70;
                tortue = new Tortue(newX, newY);
            }

            tortue.update(dt);
        }


        Random random = new Random();
        probTrampoline = random.nextInt(201);
        if(probTrampoline == 0 && !trampInGame) {
            trampInGame = true;
            //System.out.println((plateformes.get(plateformes.size()-1).getLargeur()));
            trampoline = new Trampoline((plateformes.get(plateformes.size()-1).getX()+ plateformes.get(plateformes.size()-1).getLargeur() - 20 - plateformes.get(plateformes.size()-1).getX())*random.nextDouble()
                   + plateformes.get(plateformes.size()-1).getX(), plateformes.get(plateformes.size()-1).getY() - 30);
            plateformes.get(plateformes.size()-1).setPossedeTramp(true);
        }

        





             if(trampoline != null) {
                 jellyfish.testCollision(trampoline);
                 trampoline.update(dt);

                 if(trampoline.getY() > fenetreY  + HighSeaTower.HEIGHT) {
                     trampInGame = false;
                     trampoline = null;
                 }
             }







        jellyfish.update(dt);


        if (!modeDebug) {

            fenetreVY = (fenetreVY + 2 * dt);
            fenetreY -= fenetreVY * dt;
        }


        // Scrolling 75%

        if (jellyfish.y < fenetreY + 0.25 * HighSeaTower.HEIGHT) {
            fenetreY -= Math.abs(jellyfish.y - (fenetreY + 0.25 * HighSeaTower.HEIGHT));
        }

        int points = nbCrustaces * 500 - (int) fenetreY;
        score = points + "m";

        if (points < 2500) {
            levels[0] = true;
        } else if (points < 5000) {
            levels[0] = false;
            levels[1] = true;
        } else if (points < 7500) {
            levels[1] = false;
            levels[2] = true;
        } else {
            levels[2] = false;
            levels[3] = true;
        }








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

        shrimp.draw(context, fenetreY);


        if(tortue != null) {
            tortue.draw(context, fenetreY);
        }

        if(trampoline != null) {
            trampoline.draw(context, fenetreY);
        }

        for (Plateforme p : plateformes) {

            p.draw(context, fenetreY);

            if (modeDebug) {
                if  (jellyfish.intersects(p) && Math.abs(jellyfish.y + jellyfish.hauteur - p.y) < 10
                        && jellyfish.vy > 0) {
                    Color temp = p.getColor();
                    p.setColor(Color.YELLOW);
                    p.draw(context, fenetreY);
                    p.setColor(temp);
                }
            }
        }

        if (modeDebug) {
            context.setFill(Color.rgb(255, 0, 0, 0.4));
            context.fillRect(jellyfish.x, jellyfish.y - fenetreY, jellyfish.largeur, jellyfish.hauteur);

            if (tortue != null) {
                context.setFill(Color.rgb(0, 255, 0, 0.4));
                context.fillRect(tortue.x, tortue.y - fenetreY, tortue.largeur, tortue.hauteur);
            }

            context.setFont(Font.font(12));
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.fillText("Position = (" + (int)jellyfish.x + "," + Math.abs((int)jellyfish.y-HighSeaTower.HEIGHT) + ")\n"
                    + "v = (" + (int)jellyfish.vx + "," + (int)jellyfish.vy + ")\n"
                    + "a = (" + (int)jellyfish.ax + "," + (int)jellyfish.ay + ")\n"
                    + "Touche le sol : " + jellyfish.getParterreFr() + "\n"
                    + "Nombre de plateformes : " + plateformes.size() +"\n"
                    + "Mode debug :" + modeDebug  +"\n"
                            + "fenetreY :" + (int) fenetreY  +"\n"
                            + "fenetreVY :" + fenetreVY  +"\n"
                            + "highScore : " + highScore   +"\n"
                            + "isJumping :" + jellyfish.getIsJumping() +"\n"
                            + "position Shrimp : (" + (int) shrimp.getX() + ", " + (int)shrimp.getY()  + ")" +"\n"
                            + "nombre de crustacés: " + nbCrustaces +"\n"
                            //+ "probTrampo" +  probTrampoline



                    , 10, 20);

        }



        // Ajouter le score au contexte graphique
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);
        context.setTextAlign(TextAlignment.RIGHT);
        context.setFont(Font.font(12));
        context.fillText("High Score : " + highScore, HighSeaTower.WIDTH - 10, 20);
        context.fillText("Level : " + getLevel(), HighSeaTower.WIDTH-10,40 );
        context.fillText("Vies restantes : " + jellyfish.getNbVies(), HighSeaTower.WIDTH-10,60 );


    }


}
