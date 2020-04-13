import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Classe reprentant le jeu et la logique interne de
 * l’application (modèle)
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
    private Trampoline trampoline;

    // si le mode debug est activé
    private boolean modeDebug;

    // si la partie est terminée
    private boolean gameOver;

    // si c'est la premiere fois qu'on a une plateforme accelerante
    private boolean firstParterreAcc = true;

    // compteur du nombre de plateformes apres debut du jeu
    private int counter = 5 ;

    // Plus haut score comptabilisé
    private static int highScore = 0;

    // Score du jeu
    private String score;

    // Nombre de crevettes mangés
    private int nbCrustaces = 0;

    // quel niveau on est dans le jeu
    private boolean[] levels = new boolean[4];

    // si un trampoline est dans le jeu
    private boolean trampInGame = false;

    // Si une tortue n'est pas encore apparut dans le jeu
    private boolean firstTortue=true;


    /**
     * Constructeur de Jeu
     */
    public Jeu() {

        // Au debut on est au niveau facile
        levels[0] = true;
        // Pas de tortue
        tortue = null;
        // Ni de trampoline
        trampoline = null;



        // Plancher servant a positionner sur l'axe des x la meduse ou on
        // le souhaite au debut du jeu
        plancher = new Plateforme(0);
        plancher.setX(0);
        plancher.setY(HighSeaTower.HEIGHT);
        plancher.setLargeur(HighSeaTower.WIDTH);
        plancher.setId("plancher");





        // Plateformes du debut du jeu
        var counter = 1;
        boolean prevSolide;
        while(plateformes.size() < 4) {
            if (plateformes.size()==0) {
                prevSolide = false;
            } else {
                prevSolide = plateformes.get(plateformes.size() -
                        1).getId().equals("plateformeSolide");
            }

            generatePlateforme(counter, prevSolide, 5, 0,
                    15, 35, 0, 0);

                counter++;
        }
        for (Plateforme p : plateformes) {
            p.update(0);
        }

        // initialiser la méduse
        jellyfish = new Jellyfish(width/2 - 25, height);
        jellyfish.update(0);

        // pas de bulles au debut du jeu
        bulles = new Bulle[0][0];

        // initialiser la crevette
        Random rand = new Random();
        shrimp = new Shrimp( rand.nextInt(HighSeaTower.WIDTH - 30 + 1),
                rand.nextInt(90+1));

        // le jeu n'est pas terminée
        gameOver = false;

        // initialise le score
        score = -(int) fenetreY + "m";

    }

    /**
     * Permet de generer une plateforme simple
     * @param counter compteur du nombre de plateformes crees afin
     * de positionner la plateforme au bon endroit
     */
    public void generateSimple(int counter) {
        Plateforme plateformeSimple = new Plateforme(counter);
        plateformeSimple.setColor(Color.rgb(230, 134, 58));
        plateformeSimple.setId("plateformeSimple");
        plateformes.add(plateformeSimple);
    }


    /**
     * Permet de generer une plateforme accélérante
     * @param counter compteur du nombre de plateformes crees
     *               afin de positionner la plateforme au bon endroit
     */
    public void generateAcc(int counter) {
        Plateforme plateformeAcc = new Plateforme(counter);
        plateformeAcc.setColor(Color.rgb(230, 221, 58));
        plateformeAcc.setId("plateformeAcc");
        plateformes.add(plateformeAcc);

    }

    /**
     * Permet de generer une plateforme rebondissante
     * @param counter compteur du nombre de plateformes crees afin de
     * positionner la plateforme au bon endroit
     */
    public void generateReb(int counter) {
        Plateforme plateformeRebon = new Plateforme(counter);
        plateformeRebon.setColor(Color.LIGHTGREEN);
        plateformeRebon.setId("plateformeRebon");
        plateformes.add(plateformeRebon);

    }

    /**
     * Permet de generer une plateforme solide
     * @param counter compteur du nombre de plateformes crees
     * afin de positionner la plateforme au bon endroit
     */
    public void generateSolide(int counter) {
        Plateforme plateformeSolide = new Plateforme(counter);
        plateformeSolide.setColor(Color.rgb(184, 15, 36));
        plateformeSolide.setId("plateformeSolide");
        plateformes.add(plateformeSolide);

    }

    /**
     * Permet de generer une plateforme mouvante
     * @param counter compteur du nombre de plateformes crees afin de
     * positionner la plateforme au bon endroit
     */
    public void generateMouvante(int counter) {
        Plateforme plateformeMouvante = new Plateforme(counter);
        plateformeMouvante.setColor(Color.PALETURQUOISE);
        plateformeMouvante.setVX(100);
        plateformeMouvante.setId("plateformeMouvante");
        plateformes.add(plateformeMouvante);

    }

    /**
     * Permet de generer une plateforme temporaire
     * @param counter compteur du nombre de plateformes crees afin de
     * positionner la plateforme au bon endroit
     */
    public void generateTemporaire(int counter) {
        Plateforme plateformeTemporaire = new Plateforme(counter);
        plateformeTemporaire.setColor(Color.GREY);
        plateformeTemporaire.setId("plateformeTemporaire");
        plateformes.add(plateformeTemporaire);

    }


    /**
     * Permet de generer une plateforme aléatoire qui est sensé
     * piéger le joueur parce que toutes ces plateformes ont la
     * même couleur
     * @param counter compteur du nombre de plateformes crees afin de
     * positionner la plateforme au bon endroit
     * @param prevSolide boolean si la plateforme precedente est solide
     */
    public void generateSurprise(int counter, boolean prevSolide) {
        Plateforme plateformeSurprise = new Plateforme(counter);
        plateformeSurprise.setColor(Color.PURPLE);
        Random random = new Random();
        int numero;

        if(!prevSolide) {  // Si la plateforme precedente n'est pas
            // solide, on peut genererer n'importe quel plateforme
            numero = random.nextInt(6);
        } else { // sinon toutes les plateformes sauf la solide
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

    /**
     * Permet de generer une plateforme suivant les probabilites en
     * paramètres des differents types de plateformes
     * @param counter compteur du nombre de plateformes crees afin de
     * positionner la plateforme au bon endroit
     * @param prevSolide  boolean si la plateforme precedente
     * est solide
     * @param pSolide nombre de fois moyen qu'une plateforme solide
     * apparait sur 100
     * @param pSurprise nombre de fois moyen qu'une plateforme
     * surprise apparait sur 100 - pSolide
     * @param pAcc nombre de fois moyen qu'une plateforme accélérante
     * apparait sur 100 - pSolide - pSurprise
     * @param pReb nombre de fois moyen qu'une plateforme rebondissante
     * apparait sur 100  - pSolide - pSurprise - pAcc
     * @param pMouv nombre de fois moyen qu'une plateforme mouvante apparait
     * sur 100 - pSolide - pSurprise - pAcc - pReb
     * @param pTemp nombre de fois moyen qu'une plateforme temporaire apparait
     * sur 100 - pSolide - pSurprise - pAcc - pReb - pMouv
     */
        public void generatePlateforme(int counter, boolean prevSolide,
                                       int pSolide, int pSurprise, int pAcc,
                                       int pReb, int pMouv, int pTemp){
            Random random = new Random();
            int pourcent = random.nextInt(101);
            
            if(pourcent <= pSolide) {
                if (!prevSolide) {  //si la plateforme precedente n'est pas
                    // solide, on genere une solide
                    generateSolide(counter);

                } else { // sinon reappeler la fonction recursivement
                    generatePlateforme(counter, prevSolide, pSolide, pSurprise,
                            pAcc,  pReb, pMouv, pTemp);
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


    /**
     * La méduse saute
     */
    public void jump() {
        jellyfish.jump();
    }

    /**
     * La méduse va à gauche
     */
    public void left() {
        jellyfish.left();
    }

    /**
     * La méduse va à droite
     */
    public void right() {
        jellyfish.right();
    }

    /**
     * La méduse arrête de se deplacer
     */
    public void stop() {
        jellyfish.stop();
    }

    /**
     * Initialise un groupe de bulles
     */
    public void groupBulles() {
        bulles = new Bulle[3][5];
        Bulle.groupBulles(bulles, width, fenetreY + height);
    }

    /**
     * Passe du mode debug au mode normal et inversement
     */
    public void debug() {
        modeDebug = !modeDebug;
    }

    /**
     * Efface le plancher
     */
    public void removePlancher() {
        plancher = null;
    }


    /**
     * Savoir si le jeu est terminée
     * @return un boolean
     */
    public boolean getGameOver() {
        return this.gameOver;
    }

    /**
     * Mutateur de la vitesse en Y de l'ecran
     * @param fenetreVY position y par rapport au niveau du jeu
     */
    public void setFenetreVY(double fenetreVY) {
        this.fenetreVY = fenetreVY;
    }



    /**
     * Represente le niveau de difficulté du jeu
     * @return un String
     */
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

    /**
     * Si la meduse a touche pour la premiere fois la tortue
     * @return un boolean
     */
    public boolean getFirstInterTortue() {
        return jellyfish.getFirstInterTortue(tortue);
    }


    /**
     * Si la meduse a touche pour la derniere fois la tortue
     * @return un boolean
     */
    public boolean getLastInterTortue() {
        return jellyfish.getLastInterTortue(tortue);
    }

    /**
     * Getter du nombre de vies de la méduse
     * @return le nombre de vie restant
     */
    public int getNbVies() {
        return jellyfish.getNbVies();
    }

    /**
     * Accesseur du nombre de vies de la meduse
     * @param life
     */
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


    /**
     * Met a jour les donnes du jeu
     * @param dt Temps écoulé depuis le dernier update()
     */
    public void update(double dt) {

        // Si la meduse tombe au fond de l'ocean ou qu'elle n'a plus de
        //vies alors Game Over
        // et actualiser le plus haut score
        if ( (jellyfish.y > HighSeaTower.HEIGHT + fenetreY) ||
                jellyfish.getNbVies() == 0 ) {

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

        // À chaque tour, on recalcule si la meduse se trouve parterre ou non
        jellyfish.setParterre(false);
        jellyfish.setParterreAcc(false);

         // teste la collision avec le plancher
        if (plancher != null) {
            jellyfish.testCollision(plancher);
        }

        // Tant que la derniere plateforme est en dessous de l'origine de
        // l'ecran, generer une plateforme au dessus de l'ecran
        while (plateformes.get(plateformes.size() - 1).getY() > fenetreY) {

            // savoir si la derniere plateforme est solide ou pas
            boolean prevSolide = plateformes.get(plateformes.size() -
                    1).getId().equals("plateformeSolide");

            // generer une plateforme suivant le niveau auquel on est dans le jeu
            // au niveau facile, on a les plateformes conformes aux probabilités de l'enoncé
            // au niveau normal, on a toutes les plateformes
            // au niveau difficile, pareil avec une nuance des probabilites
            // afin que ce soit plus diffile
            // au niveau hardcore, on a que des plateformes surprises
            if (levels[0]) {
                generatePlateforme(counter, prevSolide, 5,
                        0, 15, 35, 0, 0);
            } else if (levels[1]) {
                generatePlateforme(counter, prevSolide, 5,
                        15, 30, 45, 60, 75);
            } else if (levels[2]) {
                generatePlateforme(counter, prevSolide, 10,
                        20, 35, 50, 65, 80);
            } else if (levels[3]) {
                generatePlateforme(counter, prevSolide, 0,
                        100, 0, 0, 0, 0);
            }

            counter++;
        }

        // A partir du niveau 2, la tortue apparait
        if (levels[2] && firstTortue) {
            tortue = new Tortue(0,
                    plateformes.get(plateformes.size()-1).getY()-70);
            firstTortue = false;
        }

        // Tant que la premiere plateforme est en dessous de l'ecran,
        // la supprimer par soucis de memoire
        while (plateformes.get(0).getY() > HighSeaTower.HEIGHT + fenetreY) {
            plateformes.remove(0);
        }


        // mettre a jour la position des plateformes
        for (Plateforme p : plateformes) {

            p.update(dt);


            // teste la collision de la meduse avec les plateformes
            jellyfish.testCollision(p);

            // Si la meduse vient de toucher une plateforme
            // accelerante alors augmenter la vitesse de l'ecran
            if (jellyfish.getParterreAcc()) {
                if (firstParterreAcc) {
                    firstParterreAcc = false;
                    setFenetreVY(3 * fenetreVY);
                }
            }

            // quand la meduse a quitte la plateforme accelerante,
            // remettre la vitesse de l'ecran comme avant
            if (!jellyfish.getParterreAcc()) {
                if (!firstParterreAcc) {
                    firstParterreAcc = true;
                    setFenetreVY(fenetreVY / 3);
                }
            }

            // Si une plateforme mouvante possede un trampoline alors
            // donner une vitesse au trampoline de la meme vitesse
            // que celle de la plateforme afin que le trampoline reste
            // tout le long sur la plateforme
            if (p.getId().equals("plateformeMouvante") && p.getPossedeTramp() ) {
                trampoline.setVX(p.getVX());
            }
        }

        // Permet de pouvoir supprimer les plateformes temporaires apres
        // que la meduse
        // saute depuis ces plateformes
        // On utilise un iterator afin d'eviter l'exception de
        // type ConcurrentModificationException
        // parce que on enleve
        // une plateforme avant que l'iteration de la loop se termine
        for (Iterator<Plateforme> iterator = plateformes.iterator();
             iterator.hasNext(); ) {
            Plateforme plateforme = iterator.next();
            if (plateforme.getId().equals("plateformeTemporaire")) {

                if (jellyfish.getIsJumping() && plateforme.getPlateformeSaute()) {

                    // Si la plateforme temporaire possede aussi un
                    // trampoline dessus, il
                    // faut aussi supprimer le trampoline
                    if(plateforme.getPossedeTramp()) {
                        trampoline = null;
                        trampInGame = false;
                    }
                    iterator.remove();


                }
            }
        }

        // Si la crevette se retrouve au dessous de l'ecran generer
        // une nouvelle crevette
        // se trouvant au dessus de l'ecran
        if (shrimp.getY() > fenetreY + shrimp.getRayon() + HighSeaTower.HEIGHT) {
            Random rand = new Random();
            double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
            double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT - 30 + 1)
                    - 30;
            shrimp = new Shrimp(newX, newY);
        }

        // tester la collision de la meduse avec la crevette
        jellyfish.testCollision(shrimp);
        // Si la meduse a attrape la crevette, generer une nouvelle crevette
        // au dessus de l'ecran
        if (jellyfish.aAttrape()) {
            Random rand = new Random();
            double newX = rand.nextInt(HighSeaTower.WIDTH - 30 + 1);
            double newY = fenetreY - rand.nextInt(HighSeaTower.HEIGHT -
                    30 + 1) - 30;
            shrimp = new Shrimp(newX, newY);
            nbCrustaces++;
            jellyfish.setAAttrape(false);
        }

        // met a jour la position de la crevette
        shrimp.update(dt);

        // Si il y a une tortue dans le jeu, tester la collision
        // de la meduse avec la tortue
        if(tortue != null) {
            jellyfish.testCollision(tortue);

            // Si la tortue se retrouve sous l'ecran, generer
            // une nouvelle tortue au dessus de l'ecran et la positionner
            // de maniere qu'elle soit entre les plateformes
            if (tortue.getY() > fenetreY + HighSeaTower.HEIGHT) {
                Random rand = new Random();
                double newX = rand.nextInt(HighSeaTower.WIDTH - 60 + 1);
                double newY = fenetreY - rand.nextInt(5)*110 -70;
                tortue = new Tortue(newX, newY);
            }

            // mettre a jour la position de la tortue
            tortue.update(dt);
        }

        // generer un trampoline de maniere aleatoire
        Random random = new Random();
        int probTrampoline = random.nextInt(201);
        if(probTrampoline == 0 && !trampInGame) {
            trampInGame = true;
            // generer un trampoline sur la derniere plateforme
            // (au dessus de l'ecran) avec une position tel que ce
            // trampoline soit toujours sur la plateforme
            trampoline = new Trampoline((plateformes.get(
                    plateformes.size()-1).getX()+
                    plateformes.get(plateformes.size()-1).getLargeur() - 20 -
                    plateformes.get(plateformes.size()-1).getX())*
                    random.nextDouble()
                   + plateformes.get(plateformes.size()-1).getX(),
                    plateformes.get(plateformes.size()-1).getY() - 30);
            plateformes.get(plateformes.size()-1).setPossedeTramp(true);
        }

        

        // Si il y a un trampoline dans le jeu, tester la collision
        // de la meduse avec le trampoline et mettre a jour la
        // position du trampoline
        if(trampoline != null) {
            jellyfish.testCollision(trampoline);
            trampoline.update(dt);

            // Si le trampoline se retrouve au dessous de l'ecran,
            // mettre a jour les données
            if(trampoline.getY() > fenetreY  + HighSeaTower.HEIGHT) {
                trampInGame = false;
                trampoline = null;
            }
        }


        // mettre a jour la position de la meduse
        jellyfish.update(dt);


        // Si on est dans le mode normal
        if (!modeDebug) {

            // mettre a jour la vitesse et position de l'ecran
            fenetreVY = (fenetreVY + 2 * dt);
            fenetreY -= fenetreVY * dt;
        }


        // La meduse ne peut jamais aller au delas de 75%
        // de la hauteur de l'ecran
        // Et fait monter l'ecran adequatement
        if (jellyfish.y < fenetreY + 0.25 * HighSeaTower.HEIGHT) {
            fenetreY -= Math.abs(jellyfish.y - (fenetreY
                    + 0.25 * HighSeaTower.HEIGHT));
        }

        // Nombre de points gagnes qui est le nombre de
        // pixels dont l'ecran a monte depuis le debut de partie
        // additionnes des points supplementaires gagnes grace aux
        // crevettes mangés
        int points = nbCrustaces * 500 - (int) fenetreY;
        score = points + "m";

        // Au debut du jeu on est au niveau facile, puis apres un
        // certain nombre de points, on est au niveau normal,
        // puis difficile et enfin hardcore
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

    /**
     * Dessine toutes les formes de l'animation dans le contexte
     * graphique du canvas
     * @param context contexte graphique du canvas
     */
    public void draw(GraphicsContext context) {

        // Background bleu du jeu
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

        // dessine la meduse
        jellyfish.draw(context, fenetreY);

        // dessine la crevette
        shrimp.draw(context, fenetreY);

        // dessine la tortue
        if(tortue != null) {
            tortue.draw(context, fenetreY);
        }

        // dessine le trampoline
        if(trampoline != null) {
            trampoline.draw(context, fenetreY);
        }

        // dessine les plateformes
        for (Plateforme p : plateformes) {

            p.draw(context, fenetreY);

            // Si on est en mode debug,
            if (modeDebug) {
                // S'il y a une collision avec une plateforme
                // alors rendre cette plateforme jaune le temps de la collision
                if  (jellyfish.intersects(p) && Math.abs(jellyfish.y +
                        jellyfish.hauteur - p.y) < 10
                        && jellyfish.vy > 0) {
                    Color temp = p.getColor();
                    p.setColor(Color.YELLOW);
                    p.draw(context, fenetreY);
                    p.setColor(temp);
                }
            }
        }


        // Si on est en mode debug
        if (modeDebug) {
            // dessiner un carre rouge dans la boite englobante de la meduse
            context.setFill(Color.rgb(255, 0, 0, 0.4));
            context.fillRect(jellyfish.x, jellyfish.y - fenetreY,
                    jellyfish.largeur, jellyfish.hauteur);

            if (tortue != null) {
                // dessiner un rectangle vert dans la boite
                // englobante de la tortue
                context.setFill(Color.rgb(0, 255, 0, 0.4));
                context.fillRect(tortue.x, tortue.y - fenetreY,
                        tortue.largeur, tortue.hauteur);
            }

            // dessiner les informations de debuggages
            context.setFont(Font.font(12));
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.fillText("Position = (" + (int)jellyfish.x + "," +
                            Math.abs((int)jellyfish.y-HighSeaTower.HEIGHT) +
                            ")\n"
                    + "v = (" + (int)jellyfish.vx + "," + (int)jellyfish.vy +
                            ")\n"
                    + "a = (" + (int)jellyfish.ax + "," + (int)jellyfish.ay +
                            ")\n"
                    + "Touche le sol : " + jellyfish.getParterreFr() + "\n"
                    + "Nombre de plateformes : " + plateformes.size() +"\n"
                            + "fenetreY :" + (int) fenetreY  +"\n"
                            + "fenetreVY :" + (int)fenetreVY  +"\n"
                            + "isJumping :" + jellyfish.getIsJumping() +"\n"
                            + "position Shrimp : (" + (int) shrimp.getX() + ", "
                            + (int)shrimp.getY()  + ")" +"\n"
                            + "nombre de crustacés: " + nbCrustaces +"\n"

                    , 10, 20);

        }



        // dessine le score
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);

        // dessine des informations diverses comme le plus
        // haut score, le niveau de difficulté et le nombre de vies restantes
        // de la meduse
        context.setTextAlign(TextAlignment.RIGHT);
        context.setFont(Font.font(12));
        context.fillText("High Score : " + highScore,
                HighSeaTower.WIDTH - 10, 20);
        context.fillText("Level : " + getLevel(),
                HighSeaTower.WIDTH-10,40 );
        context.fillText("Vies restantes : " +
                jellyfish.getNbVies(), HighSeaTower.WIDTH-10,60 );

    }

}
