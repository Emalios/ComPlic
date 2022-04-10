package repint.expression;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import repint.TDS;
import repint.expression.operande.Nombre;

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
    public String getType() throws ExceptionSemantique {
        switch (this.operateur) {
            case "+":
            case "-":
            case "*": return Constante.ENTIER;
            case "=":
            case ">=":
            case ">":
            case "<=":
            case "<":
            case "#":
            case "et":
            case "ou": return Constante.BOOLEAN;
        }
        throw new ExceptionSemantique("Opérateur '" + this.operateur + "' inconnu.");
    }

    @Override
    public String toString() {
        return left + " " + operateur + " " + right;
    }

    @Override
    public String toMips() {
        StringBuilder builder = new StringBuilder();
        ajouterCommentaire(builder, "On calcule la première opérande dans $v0");
        calculerOperande(this.left, builder);
        ajouterCommentaire(builder, "On empile le résultat de $v0");
        empiler(builder);
        ajouterCommentaire(builder, "On calcule la deuxième opérande dans $v0");
        calculerOperande(this.right, builder);
        ajouterCommentaire(builder, "On met la deuxième opérande dans $v1");
        ajouterLigne(builder, "move $v1, $v0");
        ajouterCommentaire(builder, "On peut maintenant dépiler la première opérande dans $v0");
        depiler(builder);
        switch (this.operateur) {
            case "+": ajouterLigne(builder, "add $v0, $v0, $v1"); break;
            case "-": ajouterLigne(builder, "sub $v0, $v0, $v1"); break;
            case "*": ajouterLigne(builder, "mul $v0, $v0, $v1"); break;
            case "=": ajouterLigne(builder, "seq $v0, $v0, $v1"); break;
            case ">=": ajouterLigne(builder, "sge $v0, $v0, $v1"); break;
            case ">": ajouterLigne(builder, "sgt $v0, $v0, $v1"); break;
            case "<=": ajouterLigne(builder, "sle $v0, $v0, $v1"); break;
            case "<": ajouterLigne(builder, "slt $v0, $v0, $v1"); break;
            case "#": ajouterLigne(builder, "sne $v0, $v0, $v1"); break;
            case "et": ajouterLigne(builder, "and $v0, $v0, $v1"); break;
            case "ou": ajouterLigne(builder, "or $v0, $v0, $v1"); break;
        }
        return builder.toString();
    }

    public void calculerOperande(Expression expression, StringBuilder builder) {
        if(expression instanceof Nombre || expression instanceof OperationBinaire) {
            ajouterCommentaire(builder, "On met " + expression + " dans $v0");
            ajouterLigne(builder, expression.toMips());
        } else {
            ajouterCommentaire(builder, "On récupère l'adresse de " + expression + " dans $a0");
            ajouterLigne(builder, expression.toMips());
        }
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
}
