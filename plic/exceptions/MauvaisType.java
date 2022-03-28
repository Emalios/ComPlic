package exceptions;

import repint.expression.Expression;

public class MauvaisType extends ExceptionSemantique {

    public MauvaisType(Expression exp1, String type1, Expression exp2, String type2) {
        super("Mauvais type, l'expression '" + exp1 + "' (" + type1 + ") n'a pas le même type que '" + exp2 + "' (" + type2 + ")");
    }

}
