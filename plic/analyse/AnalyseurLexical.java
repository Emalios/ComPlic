package analyse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyseurLexical {

    private final Scanner scanner;

    public AnalyseurLexical(File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    public String next() {
        boolean trouve = false;
        String lexicalUnit = "";
        while (scanner.hasNext() && !trouve) {
            lexicalUnit = scanner.next();
            switch (lexicalUnit) {
                //on fait un nextLine pour supprimer le commentaire
                case "//": scanner.nextLine(); break;
                //on affiche
                default: trouve = true;
            }
        }
        //Si on ne trouve plus rien, alors c'est la fin du fichier
        if(!trouve) lexicalUnit = "\n";
        //fin de fichier
        return lexicalUnit;
    }

}
