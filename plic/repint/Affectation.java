package repint;

import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import repint.expression.Acces;
import repint.expression.Expression;
import repint.expression.Idf;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Affectation extends Instruction {

    private final Acces acces;
    private final Expression valeur;

    public Affectation(Acces acces, Expression expression) {
        this.acces = acces;
        this.valeur = expression;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //vérifier expressions
        this.valeur.verifier();
        //on vérifie que idf existe
        this.acces.verifier();
        Symbole symbole = TDS.INSTANCE.get(new Entree(this.acces.getIdf().toString()));
        if(!symbole.getType().equals(this.valeur.getType())) throw new MauvaisType(this.acces, symbole.getType(), this.valeur, this.valeur.getType());
    }

    @Override
    String toMips() {
        StringBuilder builder = new StringBuilder();
        builder.append(" #Affectation de ").append(this.acces).append(" a ").append(this.valeur).append("\n");
        builder.append(this.valeur instanceof Idf ? "lw" : "li");
        builder.append(" $v0,").append(this.valeur.toMips()).append("\n");
        builder.append("sw $v0,").append(TDS.INSTANCE.get(new Entree(this.acces.getIdf().toString())).getDeplacement()).append("($s7)");
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Affectation(" + "," + valeur + ")";
    }
}
