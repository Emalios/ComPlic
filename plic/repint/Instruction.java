package repint;

import exceptions.ExceptionSemantique;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Instruction {

    abstract void verifier() throws ExceptionSemantique;
    abstract String toMips();

    /**
     * utilisé pour générer les labels afin de ne pas avoir deux labels avec le même nom
     */
    protected static final AtomicInteger NB = new AtomicInteger();

    protected void depiler(StringBuilder builder) {
        int deplacement = TDS.INSTANCE.getCptDepl();
        ajouterLigne(builder, "add $sp, $sp, 4\n lw $v0, 0($sp)");
    }

    protected void empiler(StringBuilder builder) {
        int deplacement = TDS.INSTANCE.getCptDepl();
        ajouterLigne(builder, "add $sp, $sp, -4\nsw $v0, " + deplacement + "($s7)");
    }

    protected void ajouterLigne(StringBuilder builder, String line) {
        builder.append(line).append("\n");
    }

    protected void ajouterCommentaire(StringBuilder builder, String string) {
        ajouterLigne(builder.append("# "), string);
    }

}
