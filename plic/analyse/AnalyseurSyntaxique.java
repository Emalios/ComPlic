package analyse;

import exceptions.ExceptionSyntaxique;
import exceptions.ProgrammeVide;
import exceptions.UniteLexicaleAttendu;

import java.io.File;
import java.io.FileNotFoundException;

public class AnalyseurSyntaxique {

    private final AnalyseurLexical analyseurLexical;
    private String courante;

    public AnalyseurSyntaxique(File file) throws FileNotFoundException {
        this.analyseurLexical = new AnalyseurLexical(file);
    }

    public void analyse() throws ExceptionSyntaxique {
        this.courante = this.analyseurLexical.next();
        analyseProg();
        //On test qu'on est bien à la fin du programme
        if(!this.courante.equals(Constante.EOF)) throw new ExceptionSyntaxique("Fin de programme attendu.");
    }

    private void analyseProg() throws ExceptionSyntaxique {
        //on test si le programme n'est pas vide
        analyseTerminal(Constante.PROGRAMME);
        //On teste si le courant est un identifieur
        if(!this.estIdf()) throw new UniteLexicaleAttendu("Identifieur", Constante.IDF);
        //On passe à l'unité lexicale suivante
        this.courante = this.analyseurLexical.next();
        //On est maintenant sur qu'on a un programme, on va maintenant analyser le bloc principal
        analyseBloc();
    }

    private void analyseTerminal(String terminal) throws UniteLexicaleAttendu {
        //on test si le programme n'est pas vide
        if(courante.equals(Constante.EOF)) throw new ProgrammeVide(terminal);
        //on test qu'on a bien un programme
        if(!courante.equals(Constante.PROGRAMME)) throw new UniteLexicaleAttendu(terminal);
        //on passe à la suivante
        this.courante = this.analyseurLexical.next();
    }

    private void analyseBloc() throws ExceptionSyntaxique {
        //on test que l'on commence bien avec une accolade ouvrante
        analyseTerminal(Constante.DEBUT_BLOC);
        //on boucle sur les itérations afin de les analyser  (tant que l'on arrive pas à '}')
        while (!this.courante.equals(Constante.FIN_BLOC)) {
            //on analyse l'instruction courante
            this.analyserInstruction();
        }
        //fin du bloc
    }

    private void analyserInstruction() {
        //on switch
        switch (this.courante) {
            case "ecrire": analyserEcrire(); break;
            case "entier": analyserDeclaration(); break;
            default: analyserAffectation();
        }
    }

    private void analyserEcrire() {

    }

    private void analyserDeclaration() {

    }

    private void analyserAffectation() {

    }

    private void analyserExpression() {

    }

    private boolean estIdf() {
        return this.courante.matches("[a-z|A-Z]+");
    }

}
