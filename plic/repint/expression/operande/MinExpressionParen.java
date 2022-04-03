package repint.expression.operande;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

public class MinExpressionParen extends Expression {

    private final Expression expression;

    public MinExpressionParen(Expression expression) {
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
