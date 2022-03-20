package exceptions;

public class ExceptionSemantique extends Exception {

    public ExceptionSemantique(String msg) {
        super("ERREUR: (sémantique) " + msg);
    }

}
