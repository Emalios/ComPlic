package tests;

import analyse.AnalyseurSyntaxique;
import exceptions.ExceptionFichier;
import exceptions.ExceptionSyntaxique;
import exceptions.FichierInconnu;
import exceptions.MauvaisSuffixe;

import java.io.File;
import java.io.FileNotFoundException;

public class TestGeneraux {

    public static void main(String[] args) throws Exception {
        File file = new File("plic/sources/");
        runDirectory(file);
    }

    private static void runDirectory(File directory) {
        System.out.println("Exploration du dossier '" + directory + "'");
        for (File file : directory.listFiles()) {
            if(file.isDirectory()) {
                runDirectory(file);
            } else {
                try {
                    runFile(file);
                    System.out.println("Exécution OK");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void runFile(File file) throws Exception {
        System.out.println("run du fichier '" + file + "'");
        if(!(file.getName().endsWith(".plic"))) throw new MauvaisSuffixe(file.getName());
        try {
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
            analyseurSyntaxique.analyse();
        } catch (FileNotFoundException e) {
            throw new FichierInconnu(file.getName());
        }
    }

}
