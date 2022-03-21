package repint;

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
    void verifier() throws ExceptionSemantique {
        //on ne fait rien c'est correct
    }

    @Override
    String getType() throws ExceptionSemantique {
        return "entier";
    }

    @Override
    String toMips() {
        return String.valueOf(this.valeur);
    }

    @Override
    public String toString() {
        return "Nombre(" + this.valeur + ")";
    }
}
