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
        StringBuilder builder = new StringBuilder(this.expression.toMips());
        builder.append("# On inverse\n");
        builder.append("not $v0 $v0\n");
        return builder.toString();
    }
}
