package repint.expression;

import exceptions.ExceptionSemantique;

public abstract class Expression {

    public abstract void verifier() throws ExceptionSemantique;
    public abstract String getType() throws ExceptionSemantique;
    public abstract String toMips();

}
