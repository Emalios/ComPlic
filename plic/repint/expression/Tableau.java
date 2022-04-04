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
        StringBuilder builder = new StringBuilder();
        ajouterCommentaire(builder, "On calcule l'emplacement mémoire de " + this.idf + " a l'emplacement " + this.val);
        ajouterCommentaire(builder, "Donc on multiple " + this.val + " et - 4 et on ajoute ensuite l'emplacement mémoire de " + this.idf + " ici " + baseDeplacement);
        ajouterLigne(builder, this.val.toMips());
        ajouterCommentaire(builder, "Avant d'aller plus loin, on test le débordement");
        ajouterCommentaire(builder, "On ajoute la taille du tableau");
        ajouterLigne(builder, "li $t0, " + TDS.INSTANCE.get(new Entree(this.idf.toString())).getTaille());
        ajouterCommentaire(builder, "On compare, si c'est >= alors on va dans erreur");
        ajouterLigne(builder, "bge $v0, $t0, erreur");
        ajouterLigne(builder, "li $t1, -4");
        ajouterCommentaire(builder, "On fait la multiplication");
        ajouterLigne(builder, "mult $v0, $t1");
        ajouterCommentaire(builder, "On met le résultat dans $v0");
        ajouterLigne(builder, "mflo $v0");
        ajouterCommentaire(builder, "On fait l'addition avec l'emplacement mémoire de " + this.idf + ", ici " + baseDeplacement);
        ajouterLigne(builder, "add $a0, $v0, " + baseDeplacement);
        ajouterLigne(builder, "add $a0, $a0, $s7");
        return builder.toString();
    }

    private void ajouterLigne(StringBuilder builder, String line) {
        builder.append(line).append("\n");
    }

    private void ajouterCommentaire(StringBuilder builder, String string) {
        ajouterLigne(builder.append("# "), string);
    }

    private int getTaille() {
        return TDS.INSTANCE.get(new Entree(this.idf.toString())).getTaille();
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
