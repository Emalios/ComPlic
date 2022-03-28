package repint.expression;

import analyse.Constante;
import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import exceptions.UniteLexicaleAttendu;
import repint.Entree;
import repint.Symbole;
import repint.TDS;

public class Tableau extends Acces {

    private final Expression val;
    private final Idf idf;

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
        int baseDeplacement = TDS.INSTANCE.get(new Entree(this.idf.toString())).getDeplacement();
        //t[1] avec cptl = -8
        //int = 4
        String exprMips = this.val.toMips();
        String calculMemoire ="" +
                "# code pour mettre l'expression '" + this.toString() + "' dans $v0\n" +
                "li $a0, 4\n" +
                 exprMips + "\n" +
                "mult $a0, $v0\n" +
                "mfhi $a0 # 32 most significant bits of multiplication to $a0\n"+
                "addi $a0, $a0, " + -baseDeplacement + "\n"
                ;
        return calculMemoire;
    }

    @Override
    public String toString() {
        return this.idf + "[" + this.val + "]";
    }

    @Override
    public Idf getIdf() {
        return this.idf;
    }
}
