import analyse.AnalyseurSyntaxique;
import exceptions.*;
import repint.Bloc;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {

    public Plic(String arg) throws FileNotFoundException, ExceptionSyntaxique, ExceptionSemantique {
        File file = new File(arg);
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
        Bloc bloc = analyseurSyntaxique.analyse();
        bloc.verifier();
        System.out.println(bloc);
    }

    public static void main(String[] args) throws ExceptionSyntaxique, ExceptionFichier {
        if(args.length == 0) throw new ExceptionFichier("Veuillez préciser un chemin vers un fichier .plic");
        if(!(args[0].endsWith(".plic"))) throw new MauvaisSuffixe(args[0]);
        try {
            new Plic(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println(new FichierInconnu(args[0]).getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
