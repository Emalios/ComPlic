package repint;

public class Symbole {

    private final String type;
    private int deplacement;
    private int taille;

    public Symbole(String type, int deplacement, int taille) {
        this.type = type;
        this.deplacement = deplacement;
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void addDeplacement(int delta) {
        this.deplacement += delta;
    }

    public String getType() {
        return type;
    }
}
