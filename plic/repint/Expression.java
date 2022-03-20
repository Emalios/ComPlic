package repint;

import exceptions.ExceptionSemantique;

public abstract class Expression {

    abstract void verifier() throws ExceptionSemantique;
    abstract String getType() throws ExceptionSemantique;
    abstract String toMips();

}
