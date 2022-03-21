package repint;

import exceptions.ExceptionSemantique;
import exceptions.VariableInconnue;

public class Idf extends Expression {

    private String idf;

    public Idf(String idf) {
        this.idf = idf;
    }

    public String getIdf() {
        return idf;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //on test que la variable est dans la table des symboles
        if(!TDS.INSTANCE.contient(this.idf)) throw new VariableInconnue(this.idf);
    }

    @Override
    String getType() throws ExceptionSemantique {
        //on vérifie que ça existe
        verifier();
        //on récupère le type contenu dans la TDS
        return TDS.INSTANCE.get(new Entree(this.idf)).getType();
    }

    @Override
    String toMips() {
        return "-" + TDS.INSTANCE.get(new Entree(this.idf)).getDeplacement() + "($sp)";
    }

    @Override
    public String toString() {
        return this.idf;
    }
}
