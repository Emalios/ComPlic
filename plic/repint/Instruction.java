package repint;

import exceptions.ExceptionSemantique;

public abstract class Instruction {

    abstract void verifier() throws ExceptionSemantique;
    abstract String toMips();

}
