package exceptions;

public class MauvaisSuffixe extends ExceptionFichier {

    public MauvaisSuffixe(String fileName) {
        super("Mauvaise extension pour le fichier '" + fileName + "', extension '.plic' attendu");
    }
}
