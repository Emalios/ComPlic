package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

public class Tantque extends Instruction {

    private final Expression condition;
    private final Bloc bloc;

    public Tantque(Expression condition, Bloc bloc) {
        super();
        this.condition = condition;
        this.bloc = bloc;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //TODO: s'assurer que condition est de type booléen
        bloc.verifier();
    }

    @Override
    String toMips() {
        return null;
    }
}
