package exceptions;

public class UniteLexicaleAttendu extends ExceptionSyntaxique {

    public UniteLexicaleAttendu(String ancienneUnite, String courante, String uniteAttendu, String regexUniteAttendu) {
        super("'" + uniteAttendu + "' (" + regexUniteAttendu + ") attendu après l'unité lexicale '" + ancienneUnite + "' à la place de '" + courante + "'");
    }

    public UniteLexicaleAttendu(String derniereUnite, String unite) {
        super("'" + unite + "' attendu après l'unité lexicale '" + derniereUnite + "'");
    }

    public UniteLexicaleAttendu(String unite) {
        super("'" + unite + "' attendu");
    }
}
