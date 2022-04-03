package analyse;

import exceptions.*;
import repint.*;
import repint.expression.*;
import repint.expression.operande.ExpressionParen;
import repint.expression.operande.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class AnalyseurSyntaxique {

    private static final List<String> KEYWORDS = List.of(
            Constante.ENTIER,
            Constante.PROGRAMME,
            Constante.ECRIRE,
            Constante.TABLEAU
    );

    public static final List<String> OPERATEURS = List.of(
            Constante.PLUS,
            Constante.MIN,
            Constante.MUL,
            ">",
            "<",
            ">=",
            "<=",
            "=",
            "#",
            "et",
            "ou",
            "non"
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
        while (estDeclaration(this.courante)) {
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

    private boolean estDeclaration(String courante) {
        return courante.equals(Constante.ENTIER) || courante.equals(Constante.TABLEAU);
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
        //on stocke le type, on est sur que c'est soit Constante.ENTIER ou Constante.TABLEAU
        String type = this.courante;
        int deplacement = Constante.ENTIER_DEPLACEMENT;
        //on passe à la prochaine unité lexicale
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        //si on a un tableau on s'assure qu'on a bien les paramètres nécessaires
        if(type.equals(Constante.TABLEAU)) {
            analyseTerminal(Constante.TABLEAU_OUVRE);
            //on s'assure qu'on a bien un entier
            if(!estValeurEntiere()) throw new UniteLexicaleAttendu(this.ancienne, this.courante, Constante.ENTIER, Constante.ENTIER);
            int val = Integer.parseInt(this.courante);
            //on s'assure qu'il est sup à 0
            if(val <= 0) throw new UniteLexicaleAttendu(this.ancienne, this.courante, Constante.ENTIER + " > 0", Constante.ENTIER);
            deplacement = deplacement * val;
            this.ancienne = this.courante;
            this.courante = this.analyseurLexical.next();
            analyseTerminal(Constante.TABLEAU_FERME);
        }
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
        //on s'assure que la déclaration se termine bien par le séparateur
        analyseTerminal(Constante.INSTR_SEPARATOR);
        //on ajoute la déclaration dans la table des symboles
        TDS.INSTANCE.ajouter(new Entree(idf), type, deplacement);
    }

    private Instruction analyserAffectation() throws UniteLexicaleAttendu {
        Acces acces = analyserAcces();
        //On vérifie que l'on a bien un token d'affectation
        this.analyseTerminal(Constante.AFFECTATION);
        //On vérifie qu'on a bien une expression
        return new Affectation(acces, this.analyserExpression());
    }

    private Expression analyserExpression() throws UniteLexicaleAttendu {
        Expression left = analyserOperande();
        //on test si on a un opérateur, si non on renvoie uniquement l'opérande left
        if(!estOperateur(this.courante)) {
            return left;
        }
        //si on a un opérateur on le récupère et on construit une expression binaire
        String operateur = this.courante;
        //on passe à la suite
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        Expression right = analyserOperande();
        return new OperationBinaire(left, operateur, right);
    }

    private boolean estOperateur(String courante) {
        return OPERATEURS.contains(courante);
    }

    private Expression analyserOperande() throws UniteLexicaleAttendu {
        Expression operande = null;
        //si c'est un entier
        if(this.estValeurEntiere()) {
            operande = new Nombre(Integer.parseInt(this.courante));
            //on passe à la suite
            this.ancienne = this.courante;
            this.courante = this.analyseurLexical.next();
        }
        //si c'est un acces
        else if(this.estIdf()) {
            operande = analyserAcces();
        }
        //si c'est - ( EXPRESSION )
        else if(this.courante.equals(Constante.MIN)) {
            operande = analyserMinExpressionParen();
        }
        //si c'est non EXPRESSION
        else if(this.courante.equals(Constante.NON)) {
            operande = analyserNonExpression();
        }
        //sinon ça doit être ( EXPRESSION )
        else {
            operande = analyserExpressionParen();
        }
        return operande;
    }

    //parse - EXPRESSION
    private Expression analyserExpressionParen() throws UniteLexicaleAttendu {
        analyseTerminal(Constante.PAREN_OPEN);
        Expression expression = analyserExpression();
        analyseTerminal(Constante.PAREN_CLOSE);
        return new ExpressionParen(expression);
    }

    //parse non EXPRESSION
    private Expression analyserNonExpression() throws UniteLexicaleAttendu {
        analyseTerminal(Constante.NON);
        Expression expression = analyserExpression();
        return new NonExpression(expression);
    }

    //parse - ( EXPRESSION )
    private Expression analyserMinExpressionParen() throws UniteLexicaleAttendu {
        analyseTerminal(Constante.MIN);
        analyseTerminal(Constante.PAREN_OPEN);
        Expression expression = analyserExpression();
        analyseTerminal(Constante.PAREN_CLOSE);
        return new MinExpressionParen(expression);
    }

    private Acces analyserAcces() throws UniteLexicaleAttendu {
        //on vérifie qu'on a un identifieur
        if(!this.estIdf()) throw new UniteLexicaleAttendu(this.ancienne, this.courante, "identifieur", Constante.IDF_REGEX);
        Idf idf = new Idf(this.courante);
        //on passe à la suite
        this.ancienne = this.courante;
        this.courante = this.analyseurLexical.next();
        //si on a pas '[' on retourne idf
        if(!this.courante.equals(Constante.TABLEAU_OUVRE)) return idf;
        //si on a un ';' on s'arrête, mais il peut aussi avoir un '[' pour un accès tableau
        if(this.courante.equals(Constante.INSTR_SEPARATOR)) return idf;
        //on doit avoir [ <expr> ]
        analyseTerminal(Constante.TABLEAU_OUVRE);
        Expression expression = analyserExpression();
        //on doit avoir ']'
        analyseTerminal(Constante.TABLEAU_FERME);
        return new Tableau(idf, expression);
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
        //if(KEYWORDS.contains(this.courante) || OPERATEURS.contains(this.courante)) throw new UniteLexicaleAttendu(this.ancienne, this.courante, "identifieur", Constante.IDF_REGEX);
        if(KEYWORDS.contains(this.courante)) return false;
        if(OPERATEURS.contains(this.courante)) return false;
        return this.courante.matches("[a-z|A-Z]+");
    }

}
