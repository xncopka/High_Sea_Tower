import javafx.scene.canvas.GraphicsContext;

/**
 *  classe qui relie la vue au modele, HighSeaTower à Jeu
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
     * Met à jour les positions, vitesses des entites du jeu
     * @param deltaTime Temps écoulé depuis le dernier update() en secondes
     */
    void update(double deltaTime) {
        jeu.update(deltaTime);
    }

    /**
     * Le contrôleur demande au modele de mettre à jour l'état de la meduse
     * Methode pour sauter
     */
    void jump() {
        jeu.jump();
    }

    /**
     * Le contrôleur demande au modele de mettre à jour l'état de la meduse
     * Methode pour aller a droite
     */
    void right() { jeu.right();}
    /**
     * Le contrôleur demande au modele de mettre à jour l'état de la meduse
     * Methode pour aller a gauche
     */
    void left() { jeu.left();}
    /**
     * Le contrôleur demande au modele de mettre à jour l'état de la meduse
     * Methode La meduse arrete de se deplacer
     */
    void stop() { jeu.stop();}
    /**
     * Le contrôleur demande au modele de mettre à jour l'état des bulles
     * Methode pour grouper les bulles a l'arriere plan
     */
    void groupBulles() { jeu.groupBulles();}

    /**
     * Methode pour activer ou desactiver le mode debug du jeu
     */
    void debug() { jeu.debug();}
    /**
     * Le contrôleur demande au modele de mettre à jour l'état du plancher de depart
     *  Méthode pour supprimer le plancher
     */
    void removePlancher() {
        jeu.removePlancher();
    }
    /**
     * Le contrôleur demande au modele de mettre à jour l'état du jeu
     * Methode qui indique si la partie est terminee ou pas
     */
    boolean getGameOver() {
        return jeu.getGameOver();
    }

    /**
     *
     * Methode pour mettre à jour le jeu avec ses valeurs initiales
     */
    public void updateInit() {jeu.updateInit();}


    /**
     *  Methode pour savoir si la meduse a touche la tortue
     * @return un boolean
     */
    public boolean getFirstInterTortue() {
        return jeu.getFirstInterTortue();
    }


    /**
     * Methode pour savoir si la meduse n'a plus touche la tortue
     * @return un boolean
     */
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


    /**
     * Methode pour savoir si une tortue est dans le jeu
     * @return  un boolean
     */
    public boolean getTortue() {
        return jeu.getTortue();
    }





}







