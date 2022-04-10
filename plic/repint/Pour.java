package repint;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import repint.expression.Expression;
import repint.expression.Idf;
import repint.expression.OperationBinaire;
import repint.expression.operande.Nombre;

public class Pour extends Instruction {

    private final Idf idf;
    private final Expression from;
    private final Expression to;
    private final Bloc bloc;

    public Pour(Idf idf, Expression from, Expression to, Bloc bloc) {
        super();
        this.idf = idf;
        this.from = from;
        this.to = to;
        this.bloc = bloc;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //s'assurer que from et to sont des entiers
        if(!from.getType().equals(Constante.ENTIER)) throw new MauvaisType(from, from.getType(), Constante.ENTIER);
        if(!to.getType().equals(Constante.ENTIER)) throw new MauvaisType(to, to.getType(), Constante.ENTIER);
        from.verifier();
        to.verifier();
        bloc.verifier();
    }

    @Override
    String toMips() {
        Affectation affectation = new Affectation(idf, from);
        Expression condition = new OperationBinaire(this.idf, "<=", this.to);
        //on ajoute l'incrémentation de idf
        bloc.ajouterInstr(new Affectation(idf, new OperationBinaire(idf, "+", new Nombre(1))));
        Tantque tantque = new Tantque(condition, bloc);
        return affectation.toMips() + "\n" + tantque.toMips();
    }
}
