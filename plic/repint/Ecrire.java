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
    String toMips() {
        String comm = "Ecriture de " + this.expression;
        String mips = "li $v0, 1\n" +
                      "lw $a0, " + this.expression.toMips() + "\n" +
                      "syscall";
        return comm + "\n" + mips;
    }

    @Override
    public String toString() {
        return "Ecrire(" + expression + ")";
    }
}