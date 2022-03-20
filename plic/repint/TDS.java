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

    public void ajouter(Entree entree, Symbole symbole) throws DoubleDeclaration {
        if(this.tds.containsKey(entree)) throw new DoubleDeclaration(entree.getIdf());
        //condition pour la première variable pour la définir à 0
        if(this.tds.isEmpty()) cptDepl = 0;
        else cptDepl += symbole.getDeplacement();
        this.tds.put(entree, symbole);
    }

    public boolean contient(String idf) {
        return this.tds.containsKey(new Entree(idf));
    }

    public Symbole get(Entree idf) {
        return this.tds.get(idf);
    }

}
