import javafx.scene.canvas.GraphicsContext;

/**
 *  Classe qui relie la vue au modèle, HighSeaTower à Jeu
 */
public class Controleur {

    Jeu jeu;

    /**
     * Constructeur de Controleur
     */
    public Controleur() {
        jeu = new Jeu();
    }


    /**
     * Demande au jeu de dessiner les formes du dessin
     * @param context contexte graphique du canvas
     */
    void draw(GraphicsContext context) {
        jeu.draw(context);
    }


    /**
     * Demande au jeu de mettre à jour les données du jeu
     * @param deltaTime Temps écoulé depuis le dernier update() en secondes
     */
    void update(double deltaTime) {
        jeu.update(deltaTime);
    }


    /**
     * Demande au modele de mettre à jour l'état de la meduse
     * Methode pour sauter
     */
    void jump() {
        jeu.jump();
    }


    /**
     * Demande au modele de mettre à jour l'état de la meduse
     * Methode pour aller a droite
     */
    void right() { jeu.right();}


    /**
     * Demande au modele de mettre à jour l'état de la meduse
     * Methode pour aller a gauche
     */
    void left() { jeu.left();}


    /**
     * Demande au modele de mettre à jour l'état de la meduse
     * Methode La meduse arrete de se deplacer
     */
    void stop() { jeu.stop();}


    /**
     * Demande au modele de mettre à jour l'état des bulles
     * Methode pour grouper les bulles a l'arriere plan
     */
    void groupBulles() { jeu.groupBulles();}


    /**
     * Demande au modele de mettre à jour l'état du mode debug
     * Methode pour activer ou desactiver le mode debug du jeu
     */
    void debug() { jeu.debug();}


    /**
     * Demande au modele de mettre à jour l'état du plancher de depart
     * Méthode pour supprimer le plancher
     */
    void removePlancher() {
        jeu.removePlancher();
    }


    /**
     * Demande au modele de mettre à jour l'état du jeu
     * Methode qui indique si la partie est terminee ou pas
     */
    boolean getGameOver() {
        return jeu.getGameOver();
    }


    /**
     *  Demande au modele si la meduse a touche la tortue
     * @return un boolean
     */
    public boolean getFirstInterTortue() {
        return jeu.getFirstInterTortue();
    }


    /**
     * Demande au modele si la meduse n'a plus touche la tortue
     * @return un boolean
     */
    public boolean getLastInterTortue() {
        return jeu.getLastInterTortue();
    }


    /**
     * Demande au jeu le nombre de vies restantes de la meduse
     * @return le nombre de vies restantes de la meduse
     */
    public int getNbVies() {
        return jeu.getNbVies();
    }


    /**
     * Demande au jeu de mettre à jour le nombre de vies de la meduse
     * Mutateur du nombre de vies restantes
     */
    public void setNbVies(int life) {
        jeu.setNbVies(life);
    }


    /**
     * Demande au modele si une tortue est dans le jeu
     * @return  un boolean
     */
    public boolean getTortue() {
        return jeu.getTortue();
    }

}







