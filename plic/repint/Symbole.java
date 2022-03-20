package repint;

public class Symbole {

    private final String type;
    private final int deplacement;

    public Symbole(String type, int deplacement) {
        this.type = type;
        this.deplacement = deplacement;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public String getType() {
        return type;
    }
}
