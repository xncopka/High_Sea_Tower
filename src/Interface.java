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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;



public class Interface extends Application {

    // Largeur et hauteur de la fenêtre
    public static final int WIDTH = 350, HEIGHT = 480;

    @Override
    public void start(Stage primaryStage) {

        // nombre de points initialisé à 0
        int points = 0;

        // m designe le nombre de metres montés
        String score = points + "m";


        // icone de la barre de tache
        Image icone = new Image("/jellyfish1.png");


        // racine 
        Pane root = new Pane();

        //  contenu graphique à afficher dans la fenêtre
        Scene scene = new Scene(root, WIDTH, HEIGHT);


        // Node qui contient notre background bleu
        StackPane background = new StackPane();
        root.getChildren().add(background);
        background.setStyle("-fx-background-color: #00008b");

        // Fenêtre de jeu
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        background.getChildren().add(canvas);

        // Contexte graphique du canvas
        GraphicsContext context = canvas.getGraphicsContext2D();

        // Ajouter le score au contexte graphique
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(25));
        context.setFill(Color.WHITE);
        context.fillText(score, 175, 60);

        // Couleur pour les bulles
        context.setFill(Color.rgb(0, 0, 255, 0.4));


        // initialiser les bulles
        initBulles();

        // Création de l'animation
        AnimationTimer timer = new AnimationTimer() {
            // Classe anonyme

            // Initialiser dernier temps et premier temps
            private long lastTime = 0;
            private long firstTime = 0;

            // fonction appelée à chaque frame
            @Override
            public void handle(long now) {


                // Si dernier temps = 0
                if (lastTime == 0) {
                    lastTime = now;
                    firstTime = now;
                    return;
                }


                  // Si 3 secondes se sont écoulés depuis le debut de l'animation
                  if ((now - firstTime) >= ((long)3e+9)) {
                      firstTime = now;
                      initBulles();
                  }




                // temps = (temps now - dernier temps) converti en seconde
                double deltaTime = (now - lastTime) * 1e-9;

                // Efface tout le canvas et redessine le canvas
                context.clearRect(0, 0, WIDTH, HEIGHT);

                // Mettre la couleur du texte a blanc
                context.setFill(Color.WHITE);

                // remettre le score
                context.fillText(score, 175, 60);

                // remettre la couleur des bulles à bleu
                context.setFill(Color.rgb(0, 0, 255, 0.4));


                // Pour chaque groupe de bulle
                for (int i = 0; i < bulles.length ; i++) {

                    // Pour chaque bulles dans un groupe
                    for (int j = 0; j < bulles[0].length; j++) {

                        // mettre a jour la vitesse de la bulle
                        Bulle bulle = bulles[i][j];
                        bulle.update(deltaTime);

                        // dessiner la bulle
                        context.fillOval(bulle.getX(), bulle.getY(), bulle.getRayon()*2, bulle.getRayon()*2);
                    }

                }
                // mettre a jour le dernier temps
                lastTime = now;
            }
        };
        timer.start();



        // Quitter l'application si on appuie sur Echap
        scene.setOnKeyPressed((event) -> {

            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
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



    // Creation d'un tableau 2d pour contenir 3 groupes de 5 bulles
    Bulle[][] bulles = new Bulle[3][5];
    Random random= new Random();
    double maxRayon = 40;
    double minRayon = 10;


    // Creer un tableau contenant les coordonnées suivant le nombre de groupe
    double[] baseX = new double[bulles.length];

    // Borne pour baseX
    double borne = 20;


    // Initialiser les bulles
    public void initBulles() {
        // Pour chaque groupe de bulle
        for (int i = 0; i < bulles.length; i++) {

            // generer aleatoirement une coordonnee baseX
            baseX[i] = random.nextDouble() * (WIDTH + 1);

            for (int j = 0; j < bulles[0].length; j++) {

                // calculer rayon pour chaque bulle
                double rayon = random.nextDouble() * (maxRayon + 1 - minRayon) + minRayon;

                // calculer vitesse pour chaque bulle
                double vitesseY = random.nextDouble() * (Bulle.vitesseMax + 1 - Bulle.vitesseMin) + Bulle.vitesseMin;

                // calculer la position initiale X pour chaque bulle
                double base = baseX[i];
                double initX = random.nextDouble() * (base + borne + 1 - base + borne) + base - borne;

                // mettre a jour les valeurs dans la bulle
                bulles[i][j] = new Bulle(initX, HEIGHT, rayon, 0, vitesseY);
            }
        }
    }















}







