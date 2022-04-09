package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;
import repint.expression.Idf;

public class Lire extends Instruction {

    private Idf idf;

    public Lire(Idf idf) {
        super();
        this.idf = idf;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        this.idf.verifier();
    }

    @Override
    String toMips() {
        return null;
    }
}
