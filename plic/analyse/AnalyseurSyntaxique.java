package analyse;

import exceptions.*;
import repint.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class AnalyseurSyntaxique {

    private static final List<String> KEYWORDS = List.of(
            Constante.ENTIER,
            Constante.PROGRAMME,
            Constante.ECRIRE
    );

    private final AnalyseurLexical analyseurLexical;
    private String courante;
    private String ancienne;

    public AnalyseurSyntaxique(File file) throws FileNotFoundException {
        this.analyseurLexical = new AnalyseurLexical(file);
    }

    public Bloc analyse() throws ExceptionSyntaxique, ExceptionSemantique {
        this.courante = this.analyseurLexical.next();
        Bloc bloc = analyseProg();
        //On test qu'on est bien à la fin du programme
        if(!this.courante.equals(Constante.EOF)) throw new ExceptionSyntaxique("Fin de programme attendu après l'unité: " + this.courante);
        return bloc;
    }

    private Bloc analyseProg() throws ExceptionSyntaxique, ExceptionSemantique {
        //on test qu'on a bien 'programme'
        analyseTerminal(Constante.PROGRAMME);
        //On teste si le courant est un identifieur
        if(!this.estIdf()) throw new UniteLexicaleAttendu(Constante.PROGRAMME, this.courante, "Identifieur", Constante.IDF_REGEX);
        //On passe à l'unité lexicale suivante
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        //On est maintenant sur qu'on a un programme, on va maintenant analyser le bloc principal
        return analyseBloc();
    }

    private void analyseTerminal(String terminalAttendu) throws UniteLexicaleAttendu {
        //on test si le programme n'est pas vide
        if(courante.equals(Constante.EOF)) throw new ProgrammeVide(terminalAttendu);
        //on test qu'on a bien un terminal
        if(!courante.equals(terminalAttendu)) {
            throw new UniteLexicaleAttendu(this.ancienne, this.courante, terminalAttendu, "");
        }
        //on passe à la suivante
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
    }

    private Bloc analyseBloc() throws ExceptionSyntaxique, ExceptionSemantique {
        Bloc bloc = new Bloc();
        //on test que l'on commence bien avec une accolade ouvrante
        analyseTerminal(Constante.DEBUT_BLOC);
        //on boucle sur toutes les déclarations
        while (this.courante.equals(Constante.ENTIER)) {
            this.analyserDeclaration();
        }
        //on boucle sur les itérations afin de les analyser  (tant que l'on arrive pas à '}')
        //condition pour s'assurer qu'on a au moins une instruction avant la FIN_BLOC
        if(this.courante.equals(Constante.FIN_BLOC)) throw new AucuneInstruction();
        while (!this.courante.equals(Constante.FIN_BLOC)) {
            //on analyse l'instruction courante et on l'ajoute au bloc
            bloc.ajouterInstr(this.analyserInstruction());
        }
        //fin du bloc
        //on passe à la prochaine unité lexicale
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        return bloc;
    }

    private Instruction analyserInstruction() throws ExceptionSyntaxique {
        Instruction instruction;
        //on test toutes les instructions que l'on peut avoir
        if (this.courante.equals(Constante.ECRIRE)) {
            instruction = analyserEcrire();
        } else if (this.courante.equals(Constante.ENTIER)) {
            //pas censé avoir de déclaration ici
            throw new DeclarationMauvaisEndroit();
        } else if (this.estIdf()){
            instruction = analyserAffectation();
        } else {
            //throw une erreur, unité inconnue
            throw new UniteInconnu(this.courante);
        }
        //on s'assure que l'expression se termine bien par le séparateur
        analyseTerminal(Constante.INSTR_SEPARATOR);
        return instruction;
    }

    private Ecrire analyserEcrire() throws UniteLexicaleAttendu {
        //on passe à la prochaine unité lexicale
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        //on test si on a bien une expression
        return new Ecrire(this.analyserExpression());
    }

    private void analyserDeclaration() throws UniteLexicaleAttendu, ExceptionSemantique {
        //on passe à la prochaine unité lexicale
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        //On vérifie que l'on a bien un identifieur
        String idf = "";
        if(this.estIdf()) {
            //on passe à la prochaine unité lexicale
            idf = this.courante;
            this.ancienne = this.courante;
            this.courante = this.analyseurLexical.next();
        } else {
            //sinon on throw une erreur
            throw new UniteLexicaleAttendu(this.ancienne, this.courante, "identifieur", Constante.IDF_REGEX);
        }
        //on ajoute la déclaration dans la table des symboles
        TDS.INSTANCE.ajouter(new Entree(idf), new Symbole(Constante.ENTIER, Constante.ENTIER_DEPLACEMENT));
        //on s'assure que la déclaration se termine bien par le séparateur
        analyseTerminal(Constante.INSTR_SEPARATOR);
    }

    private Instruction analyserAffectation() throws UniteLexicaleAttendu {
        String idf = this.courante;
        //on passe à la prochaine unité lexicale
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        //On vérifie que l'on a bien un token d'affectation
        this.analyseTerminal(Constante.AFFECTATION);
        //On vérifie qu'on a bien une expression
        return new Affectation(new Idf(idf), this.analyserExpression());
    }

    private Expression analyserExpression() throws UniteLexicaleAttendu {
        Expression expression = null;
        if(this.estIdf()) expression = new Idf(this.courante);
        if(this.estValeurEntiere()) expression = new Nombre(Integer.parseInt(this.courante));
        //sinon on throw une erreur
        if(expression == null) throw new UniteLexicaleAttendu("expression", Constante.EXPRESSION);
        //on passe au suivant
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        return expression;
    }

    private boolean estValeurEntiere() {
        try {
            Integer.parseInt(this.courante);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean estIdf() throws UniteLexicaleAttendu {
        //TODO: améliorer cette erreur
        if(KEYWORDS.contains(this.courante)) throw new UniteLexicaleAttendu(this.courante, "Identifieur");
        return this.courante.matches("[a-z|A-Z]+");
    }

}
