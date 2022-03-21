package repint;

public class Symbole {

    private final String type;
    private int deplacement;

    public Symbole(String type, int deplacement) {
        this.type = type;
        this.deplacement = deplacement;
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
