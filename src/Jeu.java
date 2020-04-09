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

    // Score du jeu
    //private int points = 0;
    private String score;
    //= (int)fenetreY + "m";

    private boolean modeDebug;

    private boolean gameOver;

    private boolean imageRight;

    private boolean firstParterreAcc = true;
    
    private int counter = 5 ;

    private static int highScore = 0;








    public Jeu() {




        plancher = new Plateforme(0);
        plancher.setX(0);
        plancher.setY(Interface.HEIGHT);
        plancher.setLargeur(Interface.WIDTH);
        plancher.setId("plancher");



        var counter = 1;
        boolean prevSolide;
        while(plateformes.size() < 4) {
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
                    generateSolide(counter);

                } else {
                   continue;
                }



           }else if( 5 < pourcent && pourcent<= 10 ){
                    generateSurprise(counter, prevSolide);



            }else if(10< pourcent && pourcent<= 20 ) {
                generateAcc(counter);


            }else if(20 <pourcent && pourcent <=40) {
                    generateReb(counter);


            } else if(40 <pourcent && pourcent <=60) {
                    generateMouvante(counter);



        } else if(60 <pourcent && pourcent <=70) {
                    generateTemporaire(counter);




        }  else if(70 <pourcent && pourcent <=100) {
                    generateSimple(counter);

                }



                counter++;
        }


        jellyfish = new Jellyfish(width/2 - 25, height);


        bulles = new Bulle[0][0]; // pas de bulles au debut du jeu

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
            int numero = random.nextInt(6);
            if (numero == 0) {
                plateformeSurprise.setId("plateformeAcc");
                plateformes.add(plateformeSurprise);

            } else if (numero == 1) {
                plateformeSurprise.setId("plateformeTemporaire");
                plateformes.add(plateformeSurprise);

            } else if (numero == 2) {
                plateformeSurprise.setId("plateformeMouvante");

                plateformeSurprise.setVX(100);
                plateformes.add(plateformeSurprise);
            } else if (numero == 3) {
                if (!prevSolide) {
                    plateformeSurprise.setId("plateformeSolide");
                    plateformes.add(plateformeSurprise);

                } else {

                    generateSurprise(counter, prevSolide);


                }
                // plateformeSurprise.setId("plateformeRebon");
            } else if (numero == 4) {
                plateformeSurprise.setId("plateformeRebon");
                plateformes.add(plateformeSurprise);

             } else if (numero == 5) {
        plateformeSurprise.setId("plateformeSimple");
        plateformes.add(plateformeSurprise);

    }


        }


        public void generationPlateformes(int counter, boolean prevSolide){

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

  






    public void update(double dt) {

        if (jellyfish.y > Interface.HEIGHT + fenetreY) {
            gameOver = true;
            highScore = Math.max(highScore, -(int) fenetreY );

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

        boolean prevSolide;
        while (plateformes.get(plateformes.size() - 1).getY() > fenetreY   ) {

                if (plateformes.get(plateformes.size() - 1).getId() == "plateformeSolide") {
                    prevSolide = true;
                } else {
                    prevSolide = false;
                }

            Random random = new Random();
            int pourcent = random.nextInt(101);
            if(pourcent <= 5) {
                if (!prevSolide) {
                    generateSolide(counter);

                } else {
                    continue;
                }



            }else if( 5 < pourcent && pourcent<= 10 ){
                generateSurprise(counter, prevSolide);



            }else if(10< pourcent && pourcent<= 20 ) {
                generateAcc(counter);


            }else if(20 <pourcent && pourcent <=40) {
                generateReb(counter);


            } else if(40 <pourcent && pourcent <=60) {
                generateMouvante(counter);



            } else if(60 <pourcent && pourcent <=70) {
                generateTemporaire(counter);




            }  else if(70 <pourcent && pourcent <=100) {
                generateSimple(counter);

            }


            counter++;
        }

        while (plateformes.get(0).getY() > Interface.HEIGHT + fenetreY ) {
            

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


            
        
        
        




            jellyfish.update(dt);



                 if (modeDebug == false) {

                     fenetreVY = (fenetreVY + 2 * dt);
                     fenetreY -= fenetreVY * dt;
                 }




            // Scrolling 75%

            if (jellyfish.y < fenetreY + 0.25 * Interface.HEIGHT) {
                fenetreY -= Math.abs(jellyfish.y - (fenetreY + 0.25 * Interface.HEIGHT));
            }


            score = -(int) fenetreY + "m";


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
            context.fillRect(jellyfish.x, jellyfish.y - fenetreY, jellyfish.largeur, jellyfish.hauteur);

            context.setFont(Font.font(12));
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.fillText("Position = (" + (int)jellyfish.x + "," + Math.abs((int)jellyfish.y-Interface.HEIGHT) + ")\n"
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


                    , 10, 20);

        }



        // Ajouter le score au contexte graphique
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);
        context.setTextAlign(TextAlignment.RIGHT);
        context.setFont(Font.font(12));
        context.fillText("highScore : " + highScore, Interface.WIDTH - 10, 20);

    }
}
