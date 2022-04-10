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
        StringBuilder builder = new StringBuilder();
        //debut
        builder.append(".data\n" +
                "newLine : .asciiz \"\\n\"\n" +
                "erreur_str: .asciiz \"Erreur: Débordement\"\n" +
                "\n" +
                ".text\n" +
                "main : \n");
        builder.append(bloc.toMips());
        //end
        builder.append("# fin\n");
        builder.append("end: \n" +
                "   li $v0,10\n" +
                "   syscall ");
        builder.append("#Erreur\n" +
                "erreur:\n" +
                "    #On affiche l'erreur\n" +
                "    li $v0, 4\n" +
                "    la $a0, erreur_str\n" +
                "    syscall\n" +
                "\n" +
                "    b end");
        System.out.println(builder);
    }

    public static void main(String[] args) throws ExceptionSyntaxique, ExceptionFichier {
        try {
            if(args.length == 0) throw new ExceptionFichier("Veuillez préciser un chemin vers un fichier .plic");
            if(!(args[0].endsWith(".plic"))) throw new MauvaisSuffixe(args[0]);
            new Plic(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println(new FichierInconnu(args[0]).getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
