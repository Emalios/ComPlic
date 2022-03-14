package exceptions;

public class DeclarationMauvaisEndroit extends ExceptionSyntaxique {

    public DeclarationMauvaisEndroit() {
        super("Vous ne pouvez plus faire de déclaration après la première instruction.");
    }
}
