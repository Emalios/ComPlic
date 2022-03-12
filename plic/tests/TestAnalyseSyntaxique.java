package tests;

import analyse.AnalyseurLexical;
import analyse.AnalyseurSyntaxique;
import exceptions.ExceptionSyntaxique;
import exceptions.ProgrammeVide;
import exceptions.UniteLexicaleAttendu;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
        File fileName = new File("plic/sources/error/test_syntaxique_erreur_0.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test
    public void testMoyaiUniteAttendu() throws FileNotFoundException, ExceptionSyntaxique {
        boolean testOK = true;
        int[] files = new int[]{1, 2, 3, 4};
        for (Integer file : files) {
            File fileName = new File("plic/sources/error/test_syntaxique_erreur_" + file + ".plic");
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
            try {
                analyseurSyntaxique.analyse();
            } catch (UniteLexicaleAttendu e) {
                System.out.println(e.getMessage());
                System.out.println("Test OK.");
            } catch (Exception e) {
                testOK = false;
                e.printStackTrace();
            }
        }
        assert testOK;
    }

}
