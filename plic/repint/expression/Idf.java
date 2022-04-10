package repint.expression;

import exceptions.ExceptionSemantique;
import exceptions.VariableInconnue;
import repint.Entree;
import repint.TDS;
import repint.expression.Acces;

public class Idf extends Acces {

    private String idf;

    public Idf(String idf) {
        this.idf = idf;
    }

    @Override
    public void verifier() throws ExceptionSemantique {
        //on test que la variable est dans la table des symboles
        if(!TDS.INSTANCE.contient(this.idf)) throw new VariableInconnue(this.idf);
    }

    @Override
    public String getType() throws ExceptionSemantique {
        //on vérifie que ça existe
        verifier();
        //on récupère le type contenu dans la TDS
        return TDS.INSTANCE.get(new Entree(this.idf)).getType();
    }

    @Override
    public String toMips() {
        int deplacement = TDS.INSTANCE.get(new Entree(this.idf)).getDeplacement();
        String com = "# On met l'adresse de " + this.idf + " dans $a0\n";
        String t = "subi $a0, $s7, " + (-deplacement) + "\n";
        //--------------------------------- return com + t
        return com + t + "lw $v0, 0($a0)";
    }

    @Override
    public String toString() {
        return this.idf;
    }

    @Override
    public Idf getIdf() {
        return new Idf(this.idf);
    }
}
