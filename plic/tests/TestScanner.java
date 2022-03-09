package tests;

import analyse.AnalyseurLexical;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestScanner {

    public static void main(String[] args) throws FileNotFoundException {
        AnalyseurLexical analyseurLexical = new AnalyseurLexical(new File("plic/sources/test_lexical_normal.plic"));
        String result = "";
        while (!(result = analyseurLexical.next()).equals("\n")) System.out.println(result);
    }

    @Test
    public void testMoyai() {

    }

}
