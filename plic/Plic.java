import analyse.AnalyseurSyntaxique;
import exceptions.ExceptionSyntaxique;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {

    public Plic(String arg) throws FileNotFoundException {
        File file = new File(arg);
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
        try {
            analyseurSyntaxique.analyse();
            System.out.println("Tout s'est bien passé");
        } catch (ExceptionSyntaxique exceptionSyntaxique) {
            exceptionSyntaxique.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Plic(args[0]);
    }

}
