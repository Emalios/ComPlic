package repint;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import repint.expression.Expression;

import java.util.concurrent.atomic.AtomicInteger;

public class Si extends Instruction {

    private final Expression condition;
    private final Bloc si;

    public Si(Expression condition, Bloc si) {
        super();
        this.condition = condition;
        this.si = si;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //on doit tester que condition est booléen
        if(!condition.getType().equals(Constante.BOOLEAN)) throw new MauvaisType(condition, condition.getType(), Constante.BOOLEAN);
        condition.verifier();
        si.verifier();
    }

    @Override
    String toMips() {
        StringBuilder builder = new StringBuilder();
        int nbLabel = NB.getAndIncrement();
        ajouterCommentaire(builder, "On calcule le résultat de l'expression booléene '" + condition + "' (soit 1 si vrai, soit 0 si faux)");
        ajouterLigne(builder, condition.toMips());
        ajouterCommentaire(builder, "Si le résultat est 0 alors on va à la fin du si pour continuer l programme");
        ajouterLigne(builder, "beq $v0, $zero, finSi" + nbLabel);
        ajouterCommentaire(builder, "Si c'est 0, c'est faux donc on continue le programme, sinon on continue ici dans le bloc du si");
        ajouterCommentaire(builder, "Bloc du si");
        ajouterLigne(builder, "alors" + nbLabel + ":");
        ajouterLigne(builder, this.si.toMips());
        ajouterCommentaire(builder, "Fin bloc du si");
        ajouterCommentaire(builder, "Ensuite un fois le bloc du si exécuté on continue le programme");
        ajouterLigne(builder, "b finSi" + nbLabel);
        ajouterCommentaire(builder, "Fin de la condition");
        ajouterLigne(builder, "finSi" + nbLabel + ":");
        return builder.toString();
    }
}
