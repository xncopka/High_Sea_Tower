public class Bulle {


    private double posX, posY;

    private double rayon;

    private double vitesseX, vitesseY;

    public static double vitesseMax = 450;
    public  static double vitesseMin = 350;



    // Constructeur de Bulle
    public Bulle(double posX, double posY, double rayon, double vitesseX, double vitesseY) {
        this.posX = posX;
        this.posY = posY;
        this.rayon = rayon;
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
    }

    /**
     * Met à jour la position Y de la balle
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {


        // mettre a jour la nouvelle position Y
        posY -=  dt * vitesseY;


    }

    // Getters

    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }

    public double getRayon() {
        return rayon;
    }













}
