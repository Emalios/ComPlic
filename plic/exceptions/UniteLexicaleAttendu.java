package exceptions;

public class UniteLexicaleAttendu extends ExceptionSyntaxique {

    public UniteLexicaleAttendu(String nomUnite, String unite) {
        super(nomUnite + " attendu (" + unite + ")");
    }

    public UniteLexicaleAttendu(String unite) {
        super(unite + " attendu");
    }
}
