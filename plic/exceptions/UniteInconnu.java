package exceptions;

public class UniteInconnu extends ExceptionSyntaxique {

    public UniteInconnu(String unite) {
        super("Unité: '" + unite + "' inconnu.");
    }

}
