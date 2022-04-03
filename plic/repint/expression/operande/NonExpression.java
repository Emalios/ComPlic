package repint.expression.operande;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

public class NonExpression extends Expression {

    private Expression expression;

    public NonExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void verifier() throws ExceptionSemantique {

    }

    @Override
    public String toMips() {
        return null;
    }
}
