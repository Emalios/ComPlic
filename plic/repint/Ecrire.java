package repint;

import exceptions.ExceptionSemantique;
import repint.expression.Expression;
import repint.expression.Idf;
import repint.expression.operande.Nombre;

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
        StringBuilder builder = new StringBuilder();
        if(this.expression instanceof Nombre) {
            ajouterCommentaire(builder, "On récup la valeur de " + this.expression);
            ajouterLigne(builder, this.expression.toMips());
        } else {
            ajouterCommentaire(builder, "On calcule l'expression à afficher qui va se mettre dans $v0");
            ajouterLigne(builder, this.expression.toMips());
            ajouterCommentaire(builder, "Maintenant qu'on a dans $a0 l'emplacement mémoire de " + this.expression);
            ajouterCommentaire(builder, "On a tous ce qu'il faut là où il faut on récup $v0");
            ajouterLigne(builder, "lw $v0, 0($a0)");
        }
        ajouterCommentaire(builder, "On a l'expression dans $v0, maintenant on la met dans $a0");
        ajouterLigne(builder, "move $a0, $v0");
        ajouterCommentaire(builder, "On écrit");
        ajouterLigne(builder, "li $v0, 1");
        ajouterLigne(builder, "syscall");
        ajouterCommentaire(builder, "On affiche un retour à la ligne");
        ajouterLigne(builder, "li $v0, 4");
        ajouterLigne(builder, "la $a0, newLine");
        ajouterLigne(builder, "syscall");
        return builder.toString();
    }

    private void depiler(StringBuilder builder) {
        int deplacement = TDS.INSTANCE.getCptDepl();
        ajouterLigne(builder, "add $sp, $sp, 4\n lw $v0, 0($sp)");
    }

    private void empiler(StringBuilder builder) {
        int deplacement = TDS.INSTANCE.getCptDepl();
        ajouterLigne(builder, "add $sp, $sp, -4\nsw $v0, " + deplacement + "($s7)");
    }

    private void ajouterLigne(StringBuilder builder, String line) {
        builder.append(line).append("\n");
    }

    private void ajouterCommentaire(StringBuilder builder, String string) {
        ajouterLigne(builder.append("# "), string);
    }

    @Override
    public String toString() {
        return "Ecrire(" + expression + ")";
    }
}
