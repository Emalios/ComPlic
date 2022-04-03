package repint.expression.operande;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

public class ExpressionParen extends Expression {

    private final Expression expression;

    public ExpressionParen(Expression expression) {
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
