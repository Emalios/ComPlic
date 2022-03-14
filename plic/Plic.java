import analyse.AnalyseurSyntaxique;
import exceptions.ExceptionFichier;
import exceptions.ExceptionSyntaxique;
import exceptions.FichierInconnu;
import exceptions.MauvaisSuffixe;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {

    public Plic(String arg) throws FileNotFoundException, ExceptionSyntaxique {
        File file = new File(arg);
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
        analyseurSyntaxique.analyse();
    }

    public static void main(String[] args) throws ExceptionSyntaxique, ExceptionFichier {
        if(!(args[0].endsWith(".plic"))) throw new MauvaisSuffixe(args[0]);
        try {
            new Plic(args[0]);
        } catch (FileNotFoundException e) {
            throw new FichierInconnu(args[0]);
        }
    }

}
