package repint;

import exceptions.ExceptionSemantique;
import exceptions.MauvaisType;
import repint.expression.Acces;
import repint.expression.Expression;
import repint.expression.Idf;
import repint.expression.OperationBinaire;
import repint.expression.operande.Nombre;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Affectation extends Instruction {

    private final Acces acces;
    private final Expression valeur;

    public Affectation(Acces acces, Expression expression) {
        this.acces = acces;
        this.valeur = expression;
    }

    @Override
    void verifier() throws ExceptionSemantique {
        //vérifier expressions
        this.valeur.verifier();
        //on vérifie que idf existe
        this.acces.verifier();
        Symbole symbole = TDS.INSTANCE.get(new Entree(this.acces.getIdf().toString()));
        if(!(this.acces instanceof Idf)) return;
        if(!symbole.getType().equals(this.valeur.getType())){
            throw new MauvaisType(this.acces, symbole.getType(), this.valeur, this.valeur.getType());
        }
    }

    @Override
    String toMips() {
        StringBuilder builder = new StringBuilder();
        ajouterCommentaire(builder, toString());
        //obligé de savoir si c'est un identifieur, on n'a aucun moyen de savoir quand récupérer soit la valeur soit l'adresse
        //supposons k := k ;
        //c'est impossible avec une seule méthode toMips et sans connaître le type des opérandes
        if(this.valeur instanceof Nombre || this.valeur instanceof OperationBinaire) {
            //si c'est un nombre on récup juste la valeur dans $v0
            ajouterCommentaire(builder, "On calcule la valeur de " + this.valeur + " et on le met dans $v0");
            ajouterLigne(builder, this.valeur.toMips());
        } else {
            //sinon on récup l'adresse dans $a0 et ensuite on récup la valeur dans $v0
            ajouterCommentaire(builder, "On calcule l'emplacement mémoire de " + this.valeur + " et on le met dans $a0");
            ajouterLigne(builder, this.valeur.toMips());
            ajouterCommentaire(builder, "On met la valeur de l'expression " + this.valeur + " dans $v0");
            ajouterLigne(builder, "lw $v0, 0($a0)");
        }
        ajouterCommentaire(builder, "On empile ce qu'il y a dans $v0 pour calculer ensuite l'emplacement mémoire de " + this.acces);
        empiler(builder);
        ajouterCommentaire(builder, "On calcule l'emplacement mémoire de " + this.acces + " et on le met dans $a0");
        ajouterLigne(builder, this.acces.toMips());
        ajouterCommentaire(builder, "Maintenant on a l'adresse mémoire de " + this.acces + " dans $a0");
        //ajouterLigne(builder, "add $a0, $v0, $s7");
        ajouterCommentaire(builder, "Maintenant qu'on a l'emplacement mémoire de " + this.acces + " on peut dépiler pour mettre " + this.valeur + " dans $v0");
        depiler(builder);
        ajouterCommentaire(builder, "On a tous ce qu'il faut là où il faut donc on met ce qu'il y a dans $v0 dans $a0");
        ajouterLigne(builder, "sw $v0, 0($a0)");
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
        return "Affectation(" + this.acces + "," + valeur + ")";
    }
}
