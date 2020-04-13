import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


/**
 * 13 avril 2020
 * @author Thomas Bui, 20055825
 * @author Vanda Gaonac'h-Lovejoy, 1018781
 * Jeu en interface graphique qui tente de remonter une meduse
 * le plus dans l’océan en sautant de plateforme en plateforme.
 * L’écran monte graduellement automatiquement, etle but est
 * de ne pas tomber au fond de l’océan. La classe HighSeaTower
 * est la main de notre programme.
 */
 
/**
 * Classe qui sert à définir ce qui doit être affiché (Vue)
 */
public class HighSeaTower extends Application {



    // Largeur et hauteur de la fenêtre
    public static final int WIDTH = 350, HEIGHT = 480;

    // Contrôleur de l'application
    private Controleur controleur;

    //  Contexte graphique du canvas
    private GraphicsContext context;

    // Classe anonyme servant à creer des animations
    private AnimationTimer timer;

    // Conteneur générique
    private Pane root;

    // Texte du Game Over
    private Text over;

    // Texte pour recommencer
    private Text again;

    // boolean qui indique si la fleche gauche ou
    // droite est appuyée
    private boolean gauche;
    private boolean droite;

    // Temps qui s’est écoulé depuis le dernier appel de
    // la fonction handle
    private double deltaTime;

    // Texte du debut de partie
    private Text begin;

    /** Méthode main de HighSeaTower
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Méthode start qui est redéfinie et sert à construire l’interface
     * @param primaryStage représente la fenetre de l'application
     * @throws Exception // FXMLLoader.load(...) throws an IOException
     */
    @Override
    public void start(Stage primaryStage) throws Exception {


        // icone de la barre de tache
        Image icone = new Image("/jellyfish1.png");


        // racine
        root = new Pane();

        //  contenu graphique à afficher dans la fenêtre
        Scene scene = new Scene(root, WIDTH, HEIGHT);



        // Fenêtre de jeu
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        // Contexte graphique du canvas
        context = canvas.getGraphicsContext2D();

        // Debut du jeu
        startGame();
        textDebut();
        newTimer();



        // Actions sur le clavier en appuyant sur la touche
        scene.setOnKeyPressed((event) -> {

            // Quitter l'application si on appuie sur Echap
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }

            // Lancer le timer et faire saute jellyfish si on appuie
            // sur Espace ou Haut
            if (event.getCode() == KeyCode.SPACE || event.getCode() ==
                    KeyCode.UP) {
                root.getChildren().remove(over);
                root.getChildren().remove(again);
                root.getChildren().remove(begin);
                timer.start();
                controleur.jump();
                controleur.removePlancher();

            }


            // Lancer le timer et faire bouger lateralement vers la gauche
            // jellyfish si on appuie sur Left
            if (event.getCode() == KeyCode.LEFT) {
                root.getChildren().remove(begin);
                root.getChildren().remove(over);
                root.getChildren().remove(again);
                timer.start();
                controleur.left();
                gauche = true;



            }

            // Lancer le timer et faire bouger lateralement vers la droite
            // jellyfish si on appuie sur Right
            if (event.getCode() == KeyCode.RIGHT) {
                root.getChildren().remove(begin);
                root.getChildren().remove(over);
                root.getChildren().remove(again);
                timer.start();
                controleur.right();
                droite = true;



            }

            // Lance le mode debug ou revient au mode normal en appuyant sur T
            if (event.getCode() == KeyCode.T) {
                controleur.debug();
                controleur.draw(context);  // permet d'utiliser le
                // mode debug en debut de partie

            }

            // Restart la partie en appuyant sur R
            if (event.getCode() == KeyCode.R) {
                restart();

            }


        });

        // Actions sur le clavier en relachant la touche
        scene.setOnKeyReleased((event) -> {


            // Si on relache Left:
            if (event.getCode() == KeyCode.LEFT) {
                gauche = false;

            }

            // Si on relache Right :
            if (event.getCode() == KeyCode.RIGHT) {
                droite = false;
            }

            //  Fait deplacer la meduse de maniere "smooth" en evitant
            //  que la meduse se bloque quand on appuie sur
            // gauche puis droite ou l'inverse trop rapidement
            if(!gauche && droite) {
                controleur.right();
            }
            if (gauche && !droite){
                controleur.left();
            }
            if (!gauche && !droite){
                controleur.stop();
            }

        });


        // titre de la fenetre
        primaryStage.setTitle("High Sea Tower");

        // ajouter la scene
        primaryStage.setScene(scene);

        // fenetre non resizable
        primaryStage.setResizable(false);

        // ajouter l'icone dans la barre des taches
        primaryStage.getIcons().add(icone);

        // afficher
        primaryStage.show();
    }

    /**
     * Methode qui renvoit si la partie est terminée
     * @return un boolean
     */
    public boolean getGameOver() {
        return controleur.getGameOver();
    }


    /**
     *  Reinitialise les valeurs du jeu au debut
     */
    public void startGame() {
        controleur = new Controleur();
        controleur.draw(context);
    }

    /**
     *  Cree un nouveau timer permettant de creer des animations
     */
    public void newTimer() {
        // Création de l'animation
        timer = new AnimationTimer() {


            // Initialiser dernier temps et premier temps
            private long lastTime = 0;
            private long firstTime = 0;

            // Initialiser la premiere et derniere fois que la
            // meduse touche la tortue
            private long firstInter = 0;
            private long lastInter = 0;

            // fonction appelée à chaque frame
            @Override
            public void handle(long now) {

                // Si dernier temps = 0, faire apparaitre le
                // groupe de bulles
                if (lastTime == 0) {
                    lastTime = now;
                    firstTime = now;
                    controleur.groupBulles();
                    return;
                }

                // Si une tortue est dans le jeu
                if(controleur.getTortue()) {

                    // si la meduse touche la tortue, sauvegarder ce temps
                    if (controleur.getFirstInterTortue()) {
                        firstInter = now;
                    }

                    // si la meduse ne touche plus la tortue, sauvegarder ce temps
                    if (controleur.getLastInterTortue()) {
                        lastInter = now;
                    }

                    // Si la meduse touche la tortue pendant un certain temps
                    // enlever vie à la meduse
                    if (lastInter - firstInter >= (long) 1e+6) {
                        controleur.setNbVies(controleur.getNbVies() - 1);
                        firstInter = 0;
                        lastInter = 0;
                    }

                }


                // Si 3 secondes se sont écoulés depuis le debut de l'animation,
                // faire apparaitre un groupe de bulles
                if ((now - firstTime) >= ((long)3e+9)) {
                    firstTime = now;
                    controleur.groupBulles();
                }


                // temps = (temps now - dernier temps) converti en seconde
                deltaTime = (now - lastTime) * 1e-9;


                // redemarre une partie si la partie est terminée
                if (getGameOver()) {

                    restart();
                    textOver();
                    textAgain();

                }



                // mettre a jour les nouvelles positions
                controleur.update(deltaTime);

                // dessiner le nouveau dessin
                controleur.draw(context);


                // mettre a jour le dernier temps
                lastTime = now;
            }
        };
    }

    /**
     *  Permet de restart la partie
     */
    public void restart(){

        timer.stop();
        deltaTime =0;
        startGame();
        newTimer();

    }

    /**
     *  Cree le texte du debut du jeu et l'ajoute à la racine
     */
    public void textDebut(){
        begin = new Text ("Veillez appuyer sur une touche\n" +
                " pour commencer la partie");
        begin.setFill(Color.WHITE);
        begin.setFont(Font.font(15));
        begin.setTextAlignment(TextAlignment.CENTER);
        begin.setX(80);
        begin.setY(200);
        root.getChildren().add(begin);
    }

    /**
     * Cree le texte du Game Over et l'ajoute à la racine
     */
    public void textOver(){
        over = new Text("GAME OVER");
        over.setFill(Color.WHITE);
        over.setFont(Font.font(50));
        over.setTextAlignment(TextAlignment.CENTER);
        over.setX(40);
        over.setY(210);
        root.getChildren().add(over);
    }

    /**
     * Cree le texte pour recommencer et l'ajoute à la racine
     */
    public void textAgain() {
        again = new Text ("Veillez appuyer sur une touche pour recommencer");
        again.setFill(Color.WHITE);
        again.setFont(Font.font(15));
        again.setTextAlignment(TextAlignment.LEFT);
        again.setX(10);
        again.setY(240);
        root.getChildren().add(again);
    }

}






