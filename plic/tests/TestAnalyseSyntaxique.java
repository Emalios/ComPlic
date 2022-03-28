package tests;

import analyse.AnalyseurSyntaxique;
import exceptions.*;
import org.junit.After;
import org.junit.Test;
import repint.Bloc;
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
            Bloc bloc = analyseurSyntaxique.analyse();
            bloc.verifier();
            bloc.toMips();
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
        String files[] = new String[]{"P0test_syntaxique_erreur_deux_idf.plic", "P0test_syntaxique_erreur_idf_incorrect.plic",
        "P0test_syntaxique_erreur_manque_espace.plic", "P1test_syntaxique_manque_nombre_erreur_tableau_declaration_1.plic", "P1test_syntaxique_var_a_la_place_nombre_erreur_tableau_declaration_2.plic",
                "P1test_syntaxique_manque_crochet_fermant_erreur_tableau_declaration_1.plic", "P1test_syntaxique_manque_crochet_ouvrant_erreur_tableau_declaration_1.plic",
                "P1test_syntaxique_manque_idf_erreur_tableau_declaration_1.plic"
        };
        String path = "plic/sources/error/";
        for (String pathF : files) {
            File fileName = new File(path + pathF);
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
            try {
                analyseurSyntaxique.analyse();
                testOK = false;
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

    @Test
    public void testMoyaiSemantique() throws FileNotFoundException {
        boolean testOK = true;
        String files[] = new String[]{"P1test_semantique_-1_erreur_tableau_affectation_1.plic", "P1test_semantique_debordement_erreur_tableau_affectation_2.plic"

        };
        String path = "plic/sources/error/";
        for (String pathF : files) {
            File fileName = new File(path + pathF);
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
            try {
                analyseurSyntaxique.analyse().verifier();
                testOK = false;
            } catch (ExceptionSemantique e) {
                TDS.INSTANCE.clear();
                System.out.println("Test OK.");
            } catch (Exception e) {
                testOK = false;
                e.printStackTrace();
            }
        }
        assert testOK;
    }

    @Test
    public void testMoyaiVariableInconnue() throws FileNotFoundException {
        boolean testOK = true;
        String files[] = new String[]{"P1test_semantique_var_inconnue_erreur_tableau_affectation_3.plic"
        };
        String path = "plic/sources/error/";
        for (String pathF : files) {
            File fileName = new File(path + pathF);
            System.out.println("Test de " + fileName + "...");
            AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(fileName);
            try {
                analyseurSyntaxique.analyse().verifier();
                testOK = false;
            } catch (VariableInconnue e) {
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
