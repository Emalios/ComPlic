package exceptions;

public class DoubleDeclaration extends ExceptionSemantique {

    public DoubleDeclaration(String idfVariable) {
        super("Variable '" + idfVariable + "' déclaré deux fois.");
    }
}
