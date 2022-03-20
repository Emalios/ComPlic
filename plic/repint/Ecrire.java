package repint;

import exceptions.ExceptionSemantique;

public class Ecrire extends Instruction {

    private final Expression expression;

    public Ecrire(Expression expression) {
        this.expression = expression;
    }

    public Expression getIdf() {
        return expression;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //on vérifie que l'expression est valide
        this.expression.verifier();
    }

    @Override
    public String toString() {
        return "Ecrire(" + expression + ")";
    }
}
