package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;
import repint.expression.Idf;

public class Pour extends Instruction {

    private final Idf idf;
    private final Expression from;
    private final Expression to;
    private final Bloc bloc;

    public Pour(Idf idf, Expression from, Expression to, Bloc bloc) {
        super();
        this.idf = idf;
        this.from = from;
        this.to = to;
        this.bloc = bloc;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //TODO: s'assurer que from et to sont des entiers
        from.verifier();
        to.verifier();
        bloc.verifier();
    }

    @Override
    String toMips() {
        return null;
    }
}
