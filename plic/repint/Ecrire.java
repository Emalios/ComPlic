package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;

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
    String toMips() {
        String builder = "# Calcule de l'expression à afficher\n" +
                this.expression.toMips() + "\n" +
                "# On écrit le résultat\n" +
                "move $a0, $v0\n" +
                "li $v0, 1\n" +
                "syscall\n" +
                "# Retour à la ligne\n" +
                "li $v0, 4\n" +
                "la $a0, newLine\n" +
                "syscall\n";
        return builder;
    }

    @Override
    public String toString() {
        return "Ecrire(" + expression + ")";
    }
}
