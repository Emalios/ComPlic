package analyse;

import exceptions.ExceptionSyntaxique;
import exceptions.ProgrammeVide;
import exceptions.UniteInconnu;
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
        if(!this.courante.equals(Constante.EOF)) throw new ExceptionSyntaxique("Fin de programme attendu après l'unité: " + this.courante);
    }

    private void analyseProg() throws ExceptionSyntaxique {
        //on test si le programme n'est pas vide
        analyseTerminal(Constante.PROGRAMME);
        //On teste si le courant est un identifieur
        if(!this.estIdf()) throw new UniteLexicaleAttendu(Constante.PROGRAMME + "Identifieur", Constante.IDF);
        //On passe à l'unité lexicale suivante
        this.courante = this.analyseurLexical.next();
        //On est maintenant sur qu'on a un programme, on va maintenant analyser le bloc principal
        analyseBloc();
    }

    private void analyseTerminal(String terminal) throws UniteLexicaleAttendu {
        //on test si le programme n'est pas vide
        if(courante.equals(Constante.EOF)) throw new ProgrammeVide(terminal);
        //on test qu'on a bien un programme
        if(!courante.equals(terminal)) {
            System.out.println(terminal);
            throw new UniteLexicaleAttendu(terminal, this.courante);
        }
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
        //on passe à la prochaine unité lexicale
        this.courante = this.analyseurLexical.next();
    }

    private void analyserInstruction() throws ExceptionSyntaxique {
        //on test toutes les instructions que l'on peut avoir
        if (this.courante.equals(Constante.ECRIRE)) {
            analyserEcrire();
        } else if (this.courante.equals(Constante.ENTIER)) {
            analyserDeclaration();
        } else if (this.estIdf()){
            analyserAffectation();
        } else {
            //throw une erreur, unité inconnue
            throw new UniteInconnu(this.courante);
        }
        //on s'assure que l'expression se termine bien par le séparateur
        analyseTerminal(Constante.INSTR_SEPARATOR);
    }

    private void analyserEcrire() throws UniteLexicaleAttendu {
        //on passe à la prochaine unité lexicale
        this.courante = this.analyseurLexical.next();
        //on test si on a bien une expression
        this.analyserExpression();
    }

    private void analyserDeclaration() throws UniteLexicaleAttendu {
        //on passe à la prochaine unité lexicale
        this.courante = this.analyseurLexical.next();
        //On vérifie que l'on a bien un identifieur
        if(this.estIdf()) {
            //on passe à la prochaine unité lexicale
            this.courante = this.analyseurLexical.next();
        } else {
            //sinon on throw une erreur
            throw new UniteLexicaleAttendu(Constante.ENTIER, "identifieur", Constante.IDF);
        }
    }

    private void analyserAffectation() throws UniteLexicaleAttendu {
        //on passe à la prochaine unité lexicale
        this.courante = this.analyseurLexical.next();
        //On vérifie que l'on a bien un token d'affectation
        this.analyseTerminal(Constante.AFFECTATION);
        //On vérifie qu'on a bien une expression
        this.analyserExpression();
    }

    private void analyserExpression() throws UniteLexicaleAttendu {
        boolean estValide = this.estIdf() || this.estValeurEntiere();
        if(estValide) {
            //on passe à la prochaine unité lexicale
            this.courante = this.analyseurLexical.next();
        } else {
            //sinon on throw une erreur
            throw new UniteLexicaleAttendu("expression", Constante.EXPRESSION);
        }
    }

    private boolean estValeurEntiere() {
        try {
            Integer.parseInt(this.courante);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean estIdf() {
        return this.courante.matches("[a-z|A-Z]+");
    }

}
