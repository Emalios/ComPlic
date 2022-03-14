package exceptions;

public class FichierInconnu extends ExceptionFichier {

    public FichierInconnu(String fileName) {
        super("Fichier '" + fileName + "' inconnue.");
    }

}
