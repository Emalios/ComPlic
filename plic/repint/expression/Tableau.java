package repint.expression;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import exceptions.UniteLexicaleAttendu;
import repint.Entree;
import repint.Symbole;
import repint.TDS;

public class Tableau extends Acces {

    private Expression val;
    private Idf idf;

    public Tableau(Idf idf, Expression acces) {
        this.val = acces;
        this.idf = idf;
    }

    @Override
    public void verifier() throws ExceptionSemantique {
        //on verifie idf
        this.idf.verifier();
        //on verifie expression
        val.verifier();
        //on test que idf est bien de type tableau
        Symbole symbole = TDS.INSTANCE.get(new Entree(this.idf.toString()));
        if(!"entier".equals(this.val.getType())) throw new MauvaisType(this.idf, symbole.getType(), this.val, this.val.getType());
    }

    @Override
    public String getType() throws ExceptionSemantique {
        return Constante.ENTIER;
    }

    @Override
    public String toMips() {
        return null;
    }

    @Override
    public Idf getIdf() {
        return this.idf;
    }
}
