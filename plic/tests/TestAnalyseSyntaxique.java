package tests;

import analyse.AnalyseurSyntaxique;
import exceptions.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class TestAnalyseSyntaxique {

    @Test
    public void testMoyaiNormal() throws FileNotFoundException, ExceptionSyntaxique {
        File folderNormal = new File("plic/sources/normal/");
        for (File file : folderNormal.listFiles()) {
            String fileName = file.getName();
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
            analyseurSyntaxique.analyse();
            System.out.println("Test OK.");
        }
    }

    @Test(expected = ProgrammeVide.class)
    public void testMoyaiVide() throws FileNotFoundException, ExceptionSyntaxique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_pas_programme.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test(expected = DeclarationMauvaisEndroit.class)
    public void testMoyaiDeclaration() throws FileNotFoundException, ExceptionSyntaxique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_delcaration_instruction.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test(expected = AucuneInstruction.class)
    public void testMoyaiAucuneInstruction() throws FileNotFoundException, ExceptionSyntaxique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_aucune_instruction.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test
    public void testMoyaiUniteAttendu() throws FileNotFoundException {
        boolean testOK = true;
        int[] filesNum = new int[]{3, 4, 5};
        File[] files = new File("plic/sources/error/").listFiles();
        for (Integer file : filesNum) {
            File fileName = files[file];
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
            try {
                analyseurSyntaxique.analyse();
            } catch (UniteLexicaleAttendu e) {
                System.out.println("Test OK.");
            } catch (Exception e) {
                testOK = false;
                e.printStackTrace();
            }
        }
        assert testOK;
    }

}
