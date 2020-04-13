import javafx.scene.canvas.GraphicsContext;

/**
 * La classe contrôleur gère convertit les événements du jeu
 * en une action appropriée
 */


    public class Controleur {

        Jeu jeu;

    /**
     * Constructeur de contrôleur
     */
        public Controleur() {
            jeu = new Jeu();
        }

    /**
     * Dessine le jeu selon le contexte graphique du canvas
     * @param context contexte graphique du canvas
     */
        void draw(GraphicsContext context) {
            jeu.draw(context);
        }

    /**
     * Met à jour la position des modeles
     * @param deltaTime Temps écoulé depuis le dernier update() en secondes
     */
        void update(double deltaTime) {
            jeu.update(deltaTime);
        }

    /**
     * Le contrôleur met à jour l'état de la meduse
     * Methode pour sauter
     */
        void jump() {
            jeu.jump();
        }

    /**
     * Le contrôleur met à jour l'état de la meduse
     * Methode pour aller a droite
     */
        void right() { jeu.right();}
    /**
     * Le contrôleur met à jour l'état de la meduse
     * Methode pour aller a gauche
     */
        void left() { jeu.left();}
    /**
     * Le contrôleur met à jour l'état de la meduse
     * Methode La meduse arrete de se deplacer
     */
        void stop() { jeu.stop();}
    /**
     * Le contrôleur met à jour l'état des bulles
     * Methode pour grouper les bulles a l'arriere plan
     */
        void groupBulles() { jeu.groupBulles();}

    /**
     * Methode pour debug le jeu
     */
        void debug() { jeu.debug();}
    /**
     * Le contrôleur met à jour l'état du plancher de depart
     */
        void removePlancher() {
            jeu.removePlancher();
        }
    /**
     * Le contrôleur met à jour l'état du jeu
     * Methode qui indique que la partie est terminee
     */
        boolean getGameOver() {
            return jeu.getGameOver();
        }

        public void updateInit() {jeu.updateInit();}

        public boolean getFirstInterTortue() {
            return jeu.getFirstInterTortue();
        }


        public boolean getLastInterTortue() {
            return jeu.getLastInterTortue();
        }
    /**
     * Le contrôleur met à jour l'état du jeu
     * Methode pour indiquer le nombre de vies restantes
     */
        public int getNbVies() {
            return jeu.getNbVies();
        }
    /**
     * Le contrôleur met à jour l'état du jeu
     * Mutateur du nombre de vies restantes
     */
        public void setNbVies(int life) {
             jeu.setNbVies(life);
        }

        public boolean getTortue() {
            return jeu.getTortue();
        }





    }









