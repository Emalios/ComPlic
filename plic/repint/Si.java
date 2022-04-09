package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

public class Si extends Instruction {

    private final Expression condition;
    private final Bloc si;

    public Si(Expression condition, Bloc si) {
        super();
        this.condition = condition;
        this.si = si;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //TODO: on doit tester que condition est booléen
        condition.verifier();
        si.verifier();
    }

    @Override
    String toMips() {
        return null;
    }
}
