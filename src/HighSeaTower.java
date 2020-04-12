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


public class HighSeaTower extends Application {



    // Largeur et hauteur de la fenêtre
    public static final int WIDTH = 350, HEIGHT = 480;

    // Contrôleur de l'application
    private Controleur controleur;
    private GraphicsContext context;
    private AnimationTimer timer;
    private Pane root;
    private Text over;
    private Text again;
    private boolean gauche;
    private boolean droite;
    private double deltaTime;
    private Text begin;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


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

            // Lancer le timer et faire saute jellyfish si on appuie sur Espace
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP) {
                root.getChildren().remove(over);
                root.getChildren().remove(again);
                root.getChildren().remove(begin);
                timer.start();
                controleur.jump();
                controleur.removePlancher();

            }


            // Lancer le timer et faire bouger lateralement vers la gauche jellyfish si on appuie sur Left
            if (event.getCode() == KeyCode.LEFT) {
                root.getChildren().remove(begin);
                root.getChildren().remove(over);
                root.getChildren().remove(again);
                timer.start();
                controleur.left();
                controleur.imageLeft();
                gauche = true;



            }

            // Lancer le timer et faire bouger lateralement vers la droite jellyfish si on appuie sur Right
            if (event.getCode() == KeyCode.RIGHT) {
                root.getChildren().remove(begin);
                root.getChildren().remove(over);
                root.getChildren().remove(again);
                timer.start();
                controleur.right();
                controleur.imageRight();
                droite = true;
                


            }

            // Lance le mode debug ou revient au mode normal
            if (event.getCode() == KeyCode.T) {
                controleur.debug();
                controleur.draw(context);  // permet d'utiliser le mode debug en debut de partie

            }

            if (event.getCode() == KeyCode.R) {
                restart();
              


            }


        });

        // Actions sur le clavier en relachant la touche
        scene.setOnKeyReleased((event) -> {


            // arreter jellyfish de continuer d'aller a gauche si on relache Left
            if (event.getCode() == KeyCode.LEFT) {
                gauche = false;



            }

            // arreter jellyfish de continuer d'aller a droite si on relache Right
            if (event.getCode() == KeyCode.RIGHT) {
                droite = false;
            }

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
     * savoir si la partie est terminée
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
        controleur.updateInit();
        controleur.draw(context);
    }

    public void newTimer() {
        // Création de l'animation
        timer = new AnimationTimer() {


            // Initialiser dernier temps et premier temps
            private long lastTime = 0;
            private long firstTime = 0;

            private long firstInter = 0;
            private long lastInter = 0;

            // fonction appelée à chaque frame
            @Override
            public void handle(long now) {

                // Si dernier temps = 0
                if (lastTime == 0) {
                    lastTime = now;
                    firstTime = now;
                    controleur.groupBulles();
                    return;
                }

                if(controleur.getTortue()) {

                    if (controleur.getFirstInterTortue()) {
                        firstInter = now;
                    }

                    if (controleur.getLastInterTortue()) {
                        lastInter = now;
                    }

                    if (lastInter - firstInter >= (long) 1e+6) {
                        controleur.setNbVies(controleur.getNbVies() - 1);
                        firstInter = 0;
                        lastInter = 0;
                    }

                }





                // Si 3 secondes se sont écoulés depuis le debut de l'animation
                if ((now - firstTime) >= ((long)3e+9)) {
                    firstTime = now;
                    controleur.groupBulles();
                }


                // temps = (temps now - dernier temps) converti en seconde
                 deltaTime = (now - lastTime) * 1e-9;


                // redemarre une partie si la partie est terminée
                if (getGameOver()) {

                     restart();
                    over = new Text("GAME OVER");
                    over.setFill(Color.WHITE);
                    over.setFont(Font.font(50));
                    over.setTextAlignment(TextAlignment.CENTER);
                    over.setX(40);
                    over.setY(210);
                    root.getChildren().add(over);
                    again = new Text ("Veillez appuyer sur une touche pour recommencer");
                    again.setFill(Color.WHITE);
                    again.setFont(Font.font(15));
                    again.setTextAlignment(TextAlignment.LEFT);
                    again.setX(10);
                    again.setY(240);
                    root.getChildren().add(again);

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

    public void restart(){

        timer.stop();
        deltaTime =0;
        startGame();
        newTimer();


    }

    public void textDebut(){
        begin = new Text ("Veillez appuyer sur une touche\n pour commencer la partie");
        begin.setFill(Color.WHITE);
        begin.setFont(Font.font(15));
        begin.setTextAlignment(TextAlignment.CENTER);
        begin.setX(80);
        begin.setY(200);
        root.getChildren().add(begin);
    }






}







