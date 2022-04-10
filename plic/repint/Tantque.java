package repint;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import repint.expression.Expression;

public class Tantque extends Instruction {

    private final Expression condition;
    private final Bloc bloc;

    public Tantque(Expression condition, Bloc bloc) {
        super();
        this.condition = condition;
        this.bloc = bloc;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //on doit tester que condition est booléen
        if(!condition.getType().equals(Constante.BOOLEAN)) throw new MauvaisType(condition, condition.getType(), Constante.BOOLEAN);
        bloc.verifier();
    }

    @Override
    String toMips() {
        StringBuilder builder = new StringBuilder();
        int nbLabel = NB.getAndIncrement();
        ajouterCommentaire(builder, "On déclare le label de la boucle");
        ajouterLigne(builder, "tantque" + nbLabel + ":");
        ajouterCommentaire(builder, "On calcule le résultat de l'expression booléene '" + condition + "' (soit 1 si vrai, soit 0 si faux)");
        ajouterLigne(builder, condition.toMips());
        ajouterCommentaire(builder, "Si le résultat est 0 alors on va passe à la suite du programme");
        ajouterLigne(builder, "beq $v0, $zero, finTantQue" + nbLabel);
        ajouterCommentaire(builder, "Si c'est 0, c'est faux donc on va dans le bloc du sinon, sinon on continue ici dans le bloc du si");
        ajouterCommentaire(builder, "Bloc du si");
        ajouterLigne(builder, "alors" + nbLabel + ":");
        ajouterLigne(builder, this.bloc.toMips());
        ajouterCommentaire(builder, "Fin bloc du si");
        ajouterCommentaire(builder, "Ensuite un fois le bloc du si exécuté on essaie de reboucler");
        ajouterLigne(builder, "b tantque" + nbLabel);
        ajouterCommentaire(builder, "Fin de la boucle");
        ajouterLigne(builder, "finTantQue" + nbLabel + ":");
        return builder.toString();
    }
}
