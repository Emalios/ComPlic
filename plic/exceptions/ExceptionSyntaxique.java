package exceptions;

public class ExceptionSyntaxique extends Exception {

    public ExceptionSyntaxique(String msg) {
        super("ERREUR: (syntaxique) " + msg);
    }

}
