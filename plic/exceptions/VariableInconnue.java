package exceptions;

public class VariableInconnue extends ExceptionSemantique {
    public VariableInconnue(String idf) {
        super("Variable '" + idf + "' inconnue.");
    }
}
