package exceptions;

public class ExceptionFichier extends Exception {

    public ExceptionFichier(String msg) {
        super("ERREUR: (fichier) " + msg);
    }

}
