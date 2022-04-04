package repint.expression;

import exceptions.ExceptionSemantique;

public class OperationBinaire extends Expression {

    private final Expression left, right;
    private final String operateur;

    public OperationBinaire(Expression left, String operateur, Expression right) {
        this.left = left;
        this.operateur = operateur;
        this.right = right;
    }

    @Override
    public void verifier() throws ExceptionSemantique {
        left.verifier();
        right.verifier();
    }

    @Override
    public String toMips() {
        return null;
    }
}
