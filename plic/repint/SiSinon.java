package repint;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import repint.expression.Expression;

import java.util.concurrent.atomic.AtomicInteger;

public class SiSinon extends Instruction {

    private final Expression condition;
    private final Bloc si;
    private final Bloc sinon;

    public SiSinon(Expression condition, Bloc si, Bloc sinon) {
        super();
        this.condition = condition;
        this.si = si;
        this.sinon = sinon;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //on doit tester que condition est booléen
        if(!condition.getType().equals(Constante.BOOLEAN)) throw new MauvaisType(condition, condition.getType(), Constante.BOOLEAN);
        condition.verifier();
        si.verifier();
        sinon.verifier();
    }

    @Override
    String toMips() {
        StringBuilder builder = new StringBuilder();
        int nbLabel = NB.getAndIncrement();
        ajouterCommentaire(builder, "On calcule le résultat de l'expression booléene '" + condition + "' (soit 1 si vrai, soit 0 si faux)");
        ajouterLigne(builder, condition.toMips());
        ajouterCommentaire(builder, "Si le résultat est 0 alors on va dans le bloc du sinon");
        ajouterLigne(builder, "beq $v0, $zero, sinon" + nbLabel);
        ajouterCommentaire(builder, "Si c'est 0, c'est faux donc on va dans le bloc du sinon, sinon on continue ici dans le bloc du si");
        ajouterCommentaire(builder, "Bloc du si");
        ajouterLigne(builder, "alors" + nbLabel + ":");
        ajouterLigne(builder, this.si.toMips());
        ajouterCommentaire(builder, "Fin bloc du si");
        ajouterCommentaire(builder, "Ensuite un fois le bloc du si exécuté on continue le programme");
        ajouterLigne(builder, "b finSi" + nbLabel);
        ajouterCommentaire(builder, "Bloc du sinon");
        ajouterLigne(builder, "sinon" + nbLabel + ":");
        ajouterLigne(builder, this.sinon.toMips());
        ajouterCommentaire(builder, "Fin bloc du sinon");
        ajouterCommentaire(builder, "Ensuite un fois le bloc du sinon exécuté on continue le programme");
        ajouterLigne(builder, "b finSi" + nbLabel);
        ajouterCommentaire(builder, "Fin de la condition");
        ajouterLigne(builder, "finSi" + nbLabel + ":");
        return builder.toString();
    }
}
