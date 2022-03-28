package repint;

import exceptions.DoubleDeclaration;

import java.util.HashMap;
import java.util.Map;

public class TDS {

    public static final TDS INSTANCE = new TDS();

    private int cptDepl;
    private final Map<Entree, Symbole> tds;

    private TDS() {
        this.cptDepl = 0;
        this.tds = new HashMap<>();
    }

    public void clear() {
        tds.clear();
    }

    public void ajouter(Entree entree, String type, int deplacement) throws DoubleDeclaration {
        if(this.tds.containsKey(entree)) throw new DoubleDeclaration(entree.getIdf());
        //condition pour la première variable pour la définir à 0
        Symbole symbole = new Symbole(type, cptDepl);
        this.tds.put(entree, symbole);
        cptDepl -= deplacement;
    }

    public boolean contient(String idf) {
        return this.tds.containsKey(new Entree(idf));
    }

    public Symbole get(Entree idf) {
        return this.tds.get(idf);
    }

    public String toMips() {
        StringBuilder builder = new StringBuilder("#Allouement de l'espace mémoire nécessaire (déclaration)").append("\n");
        builder.append("move $s7, $sp").append("\n");
        builder.append("add $sp,$sp,").append(this.cptDepl).append("\n");
        //on affiche les commentaires
        for (Entree entree : this.tds.keySet()) {
            builder.append("#").append(entree).append(" stocké à ").append(this.tds.get(entree).getDeplacement()).append("\n");
        }
        return builder.toString();
    }

}
