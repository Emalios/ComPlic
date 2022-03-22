package repint.expression;

import exceptions.ExceptionSemantique;

public class Nombre extends Expression {

    private final int valeur;

    public Nombre(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

    @Override
    public void verifier() throws ExceptionSemantique {
        //on ne fait rien c'est correct
    }

    @Override
    public String getType() throws ExceptionSemantique {
        return "entier";
    }

    @Override
    public String toMips() {
        return String.valueOf(this.valeur);
    }

    @Override
    public String toString() {
        return "Nombre(" + this.valeur + ")";
    }
}
