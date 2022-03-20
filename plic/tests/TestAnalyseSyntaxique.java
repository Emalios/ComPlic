package tests;

import analyse.AnalyseurSyntaxique;
import exceptions.*;
import org.junit.After;
import org.junit.Test;
import repint.TDS;

import java.io.File;
import java.io.FileNotFoundException;

public class TestAnalyseSyntaxique {

    @After
    public void after() {
        TDS.INSTANCE.clear();
    }

    @Test
    public void testMoyaiNormal() throws FileNotFoundException, ExceptionSyntaxique, ExceptionSemantique {
        File folderNormal = new File("plic/sources/normal/");
        for (File file : folderNormal.listFiles()) {
            String fileName = file.getName();
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
            analyseurSyntaxique.analyse();
            System.out.println("Test OK.");
            TDS.INSTANCE.clear();
        }
    }

    @Test(expected = ProgrammeVide.class)
    public void testMoyaiVide() throws FileNotFoundException, ExceptionSyntaxique, ExceptionSemantique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_pas_programme.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test(expected = DoubleDeclaration.class)
    public void testMoyaiDoubleDeclaration() throws FileNotFoundException, ExceptionSyntaxique, ExceptionSemantique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_double_declaration.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test(expected = DeclarationMauvaisEndroit.class)
    public void testMoyaiDeclaration() throws FileNotFoundException, ExceptionSyntaxique, ExceptionSemantique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_delcaration_instruction.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test(expected = AucuneInstruction.class)
    public void testMoyaiAucuneInstruction() throws FileNotFoundException, ExceptionSyntaxique, ExceptionSemantique {
        File fileName = new File("plic/sources/error/P0test_syntaxique_erreur_aucune_instruction.plic");
        System.out.println("Test de " + fileName + "...");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
        analyseurSyntaxique.analyse();
        System.out.println("Test OK.");
    }

    @Test
    public void testMoyaiUniteAttendu() throws FileNotFoundException {
        boolean testOK = true;
        int[] filesNum = new int[]{3, 5, 6};
        File[] files = new File("plic/sources/error/").listFiles();
        for (Integer file : filesNum) {
            File fileName = files[file];
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
            try {
                analyseurSyntaxique.analyse();
            } catch (UniteLexicaleAttendu e) {
                TDS.INSTANCE.clear();
                System.out.println("Test OK.");
            } catch (Exception e) {
                testOK = false;
                e.printStackTrace();
            }
        }
        assert testOK;
    }

}
