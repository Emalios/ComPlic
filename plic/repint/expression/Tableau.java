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
                "# code pour mettre l'expression '" + this + "' dans $v0\n" +
                "li $t1, -4\n" +
                 exprMips + "\n" +
                "#On verifie que ça ne sort pas du tableau\n" +
                "li $a0, 5\n" +
                "bltz $v0 erreur\n" +
                "bge $v0,$a0, erreur\n" +
                "mult $t1, $v0\n" +
                "mflo $v0 # On met le résultat dans $v0\n"+
                "add $v0, $v0, " + -baseDeplacement + " # On calcule le déplacement\n" +
                "add $a0, $v0, $s7\n" +
                "lw $v0, 0($a0)\n"
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
