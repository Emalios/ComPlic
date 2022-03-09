package exceptions;

public class UniteLexicaleAttendu extends ExceptionSyntaxique {

    public UniteLexicaleAttendu(String derniereUnite, String nomUnite, String unite) {
        super(nomUnite + " attendu (" + unite + ") après l'unité lexicale " + derniereUnite);
    }

    public UniteLexicaleAttendu(String derniereUnite, String unite) {
        super(unite + " attendu après l'unité lexicale " + derniereUnite);
    }

    public UniteLexicaleAttendu(String unite) {
        super(unite + " attendu");
    }
}
