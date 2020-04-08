import javafx.scene.canvas.GraphicsContext;

import java.util.Queue;


public class Controleur {

        Jeu jeu;

        public Controleur() {
            jeu = new Jeu();
        }

        void draw(GraphicsContext context) {
            jeu.draw(context);
        }

        void removePlateformes(Queue p){
        jeu.removePlateformes(p);
    }

        void update(double deltaTime) {
            jeu.update(deltaTime);
        }

        void jump() {
            jeu.jump();
        }

        void right() { jeu.right();}

        void left() { jeu.left();}

        void stop() { jeu.stop();}

        void groupBulles() { jeu.groupBulles();}

    }









