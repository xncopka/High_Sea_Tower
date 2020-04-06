import javafx.scene.canvas.GraphicsContext;



    public class Controleur {

        Jeu jeu;

        public Controleur() {
            jeu = new Jeu();
        }

        void draw(GraphicsContext context) {
            jeu.draw(context);
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

        void debug() { jeu.debug();}

        void plancher() {
            jeu.plancher();
        }

        boolean getGameOver() {
            return jeu.getGameOver();
        }

        void getInfos() {
            jeu.getInfos();
        }

    }









