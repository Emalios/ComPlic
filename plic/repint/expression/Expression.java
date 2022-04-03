package repint.expression;

import exceptions.ExceptionSemantique;

public abstract class Expression {

    public abstract void verifier() throws ExceptionSemantique;
    public String getType() throws ExceptionSemantique {
        return "entier";
    }
    public abstract String toMips();

}
