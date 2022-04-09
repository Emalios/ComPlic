package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

public class SiSinon extends Instruction {

    private final Expression condition;
    private final Bloc si;
    private final Bloc sinon;

    public SiSinon(Expression condition, Bloc si, Bloc sinon) {
        super();
        this.condition = condition;
        this.si = si;
        this.sinon = sinon;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //TODO: on doit tester que condition est booléen
        condition.verifier();
        si.verifier();
        sinon.verifier();
    }

    @Override
    String toMips() {
        return null;
    }
}
