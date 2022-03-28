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
        if(!(this.acces instanceof Idf)) return;
        if(!symbole.getType().equals(this.valeur.getType())){
            throw new MauvaisType(this.acces, symbole.getType(), this.valeur, this.valeur.getType());
        }
    }

    public String mips() {
        StringBuilder builder = new StringBuilder();
        builder.append(" #Affectation de ").append(this.acces).append(" a ").append(this.valeur).append("\n");
        builder.append(this.valeur instanceof Idf ? "lw" : "li");
        builder.append(" $v0,").append(this.valeur.toMips()).append("\n");
        builder.append("sw $v0,").append(TDS.INSTANCE.get(new Entree(this.acces.getIdf().toString())).getDeplacement()).append("($sp)");
        return builder.toString();
    }

    @Override
    String toMips() {
                /*
        Code qui calcule la valeur de l’expression dans $v0
        Empiler $v0
        Code qui calcule l’adresse de l’accès dans $a0
        Dépiler dans $v0
        Ranger $v0 à l’adresse contenue dans $a0
         */
        /*
        Code qui calcule la valeur de l’expression dans $v0
         */
        String com = "# Affectation de " + this.acces.toString() + " à " + this.valeur.toString() + "\n";
        String codePourMettreDansV0 = this.valeur.toMips();
        //Code pour empiler $v0
        String onEmpileV0 = "# on empile\nsw $v0, " + TDS.INSTANCE.getCptDepl() + "($sp)\nadd $sp,$sp,-4\n";
        //Code qui calcule l’adresse de l’accès dans $a0
        String adresseAcces = "# Code pour mettre valeur '" + this.acces + "' accès dans $a0\n" + this.acces.toMips() + "move $a0, $v0\n\n";
        //Dépiler dans $v0
        String depiler = "# on depile \n" +
                "add $sp,$sp,4\n" +
                "lw $v0, " + TDS.INSTANCE.getCptDepl() + "($sp)\n";
        //Ranger $v0 à l’adresse contenue dans $a0
        String ranger = "#on met $v0 à l'adresse contenu dans $a0\nmove $v0, $a0\n";
        return com + codePourMettreDansV0 + onEmpileV0 + adresseAcces + depiler + ranger;
    }

    @Override
    public String toString() {
        return "Affectation(" + "," + valeur + ")";
    }
}
