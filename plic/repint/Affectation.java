package repint;

import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;

public class Affectation extends Instruction {

    private final Idf idf;
    private final Expression valeur;

    public Affectation(Idf idf, Expression expression) {
        this.idf = idf;
        this.valeur = expression;
    }

    public Idf getIdf() {
        return idf;
    }

    public Expression getValeur() {
        return valeur;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //vérifier expressions
        this.valeur.verifier();
        //on vérifie que idf existe
        this.idf.verifier();
        //vérifier les types
        Symbole symbole = TDS.INSTANCE.get(new Entree(this.idf.getIdf()));
        if(!symbole.getType().equals(this.valeur.getType())) throw new MauvaisType(this.idf, symbole.getType(), this.valeur, this.valeur.getType());
    }

    @Override
    public String toString() {
        return "Affectation(" + idf + "," + valeur + ")";
    }
}
