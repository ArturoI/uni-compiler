%{
package com.uni.compiler.parser;

import java.util.*;

import com.uni.compiler.UI.UIMain;
import com.uni.compiler.UI.Style;
import com.uni.compiler.lexicAnalizer.*;

%}

%token ID INT CTEINT STRING IF THEN ELSE BEGIN END IMPORT MENORIGUAL MAYORIGUAL IGUAL DISTINTO LOOP UNTIL PRINT RETURN FUNCTION

%right THEN ELSE
%right ';'
%left '+' '-'
%left '*' '/'

%start programa

%%

programa:   declaraciones {this.tercetoList.add(new Terceto ("LABELF","inicio",new Token(),null));} sentencias
            ;

//- - - - - - - - - - - - - - - - - - - - D E C L A R A C I O N - - - - - - - - - - - - - - - - - - - - 
				
declaraciones: declaraciones declaracion
	     | declaracion
             //| error { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Declaracion invalida"); }
	     ;

declaracion: variables
	   | funcion
           ;

variables: INT conjVariables ';'    { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": Declaracion de variables."); 
                                      saveVariableType("INT");
                                      addTmpIdToSymbolsTable();
                                      this.tmpId = new ArrayList<Token>();
                                    }
         
         | INT conjVariables error  { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": Error Sintactico : Se esperaba ';'");  }
         ;

conjVariables: conjVariables ',' ID { ((Token)$3.obj).setIsVariable(true); addFunctionNameToToken(this.functionName, ((Token)$3.obj)); this.tmpId.add((Token)$3.obj);  }	
             | ID                   { ((Token)$1.obj).setIsVariable(true); addFunctionNameToToken(this.functionName, ((Token)$1.obj)); this.tmpId.add((Token)$1.obj);  }
	     ;

funcion: FUNCTION ID { this.tercetoList.add(new Terceto("LABELF", "F" + ((Token)$2.obj).getToken(), new Token(), null)); } bloqueFuncion { executingFunctionCode = false; }
       ;

bloqueFuncion:	BEGIN declaracionFuncion sentenciasF END { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Cuerpo de la funcion.");
                                                           this.functionName = "";
                                                           this.tercetoList.add(new Terceto("RET", new Token("0"), new Token(), null)); 
                                                         }
		
                | BEGIN END                              { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera el cuerpo de la funcion");  }
                | declaracionFuncion sentenciasF END     { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera BEGIN");  }
                | BEGIN declaracionFuncion sentenciasF error { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
                ;

declaracionFuncion: variables			{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Variables de la funcion."); executingFunctionCode = true; }
		  | IMPORT conjVariables ';'    { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
		  ;

sentenciasF: sentenciasF sentenciaF
           | sentenciaF
           | ';'
           ;

sentenciaF: asignacion ';'
          | impresion
          | returnF ';'
          | seleccionF
          | iteracionF
          ;

//RETURN En EAX se guarda el resultado de retorno de la funcion.

returnF: RETURN '(' exprAritmetica ')'      { this.tercetoList.add(new Terceto("RET", new Token("EXP"), new Token(), null)); }
       | RETURN '('')'                      { this.tercetoList.add(new Terceto("RET", new Token("0"), new Token(), null)); }
       | RETURN error                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
       | RETURN '(' error                   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
       | RETURN '(' exprAritmetica error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
       ;

condicionF: '(' exprAritmeticaF opLogico exprAritmeticaF ')' { crearTerceto("CMP", 1);
                                                            this.tercetoList.add(new Terceto((String) this.pilaOperators.pop(), new Token(), new Token(), null));
                                                            //System.out.println("agregue un BF en la posicion " + this.tercetoList.size());
                                                            this.pilaBranches.push(this.tercetoList.size());
                                                            //this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null));
                                                            //System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BF");
                                                          }

         
         | exprAritmeticaF opLogico exprAritmeticaF ')'            { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
         | '(' exprAritmeticaF opLogico exprAritmeticaF error      { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
         | '(' opLogico exprAritmeticaF ')'                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
         | '(' exprAritmeticaF opLogico ')'                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
         ;

exprAritmeticaF:exprAritmeticaF '+' termino { crearTerceto("ADD", 2); }
              | exprAritmeticaF '-' termino { crearTerceto("SUB", 2); }
              | termino


              | exprAritmeticaF '+' error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              | '+' termino                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }  	
              | exprAritmeticaF '-' error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              | '-' termino                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              ;

//SELECCION

seleccionF: IF condicionF THEN sentenciaSeleccionF   { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF");
                                                      setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                      createLabel();
                                                    }
          | IF condicionF THEN sentenciaSeleccionF ELSE { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("JMP", new Token(), new Token(), null));
                                                  //System.out.println("agregue un BI en la posicion " + this.tercetoList.size());
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  //System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");
                                                  createLabel();
                                                } sentenciaSeleccionF  { setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel();}         
          | IF condicionF sentenciaSeleccionF    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
          ;
         
sentenciaSeleccionF: BEGIN sentenciasF END
                   | sentenciaF
                   ;

// ITERACION

iteracionF: LOOP { createLabelForLoop(); } sentenciasF UNTIL condicionF { 
                    showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                    setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
          | LOOP UNTIL condicionF             { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
          ;




//- - - - - - - - - - - - - - - - - F I N   D E C L A R A C I O N - - - - - - - - - - - - - - - - -

//- - - - - - - - - - - - - - - - - - - - S E N T E N C I A - - - - - - - - - - - - - - - - - - - -

sentencias: sentencias sentencia
          | sentencia
          | ';'
          ;

sentencia: asignacion ';'
         | impresion
         | seleccion
         | iteracion
         | llamadaAFuncion
         | error { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": Error Sintactico : Sentencia Invalida"); }
         ;

//ASIGNACION

asignacion: ID '=' exprAritmetica   { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Asignacion.");
                                      checkContain((Token)$1.obj);
                                      checkNameMangling((Token)$1.obj);
                                      this.pilaTerceto.push((Token)$1.obj);
                                      crearTerceto("MOV", 1);
                                    }
         
          | '=' exprAritmetica      { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
          | ID '=' error            { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
          | ID exprAritmetica       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
          ;

exprAritmetica:	exprAritmetica '+' termino { crearTerceto("ADD", 2); }
              | exprAritmetica '-' termino { crearTerceto("SUB", 2); }
              | termino
              | llamadaAFuncion

              | exprAritmetica '+' error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              | '+' termino                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }  	
              | exprAritmetica '-' error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              | '-' termino                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              ;

termino: termino '*' factor { crearTerceto("MUL", 2); }
       | termino '/' factor { crearTerceto("DIV", 2); }
       | factor

       | termino '*' error   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
       | '*' factor          { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 	
       | termino '/' error   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
       | '/' factor          { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 	
       ;

factor: ID { checkContain((Token)$1.obj); checkNameMangling((Token)$1.obj); this.pilaTerceto.push((Token)$1.obj); }
      | CTEINT { this.pilaTerceto.push((Token)$1.obj); }
      ;

//IMPRESION

impresion: PRINT '(' STRING ')' ';'	{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Impresion");
                                          this.pilaTerceto.push(new Token());
                                          this.pilaTerceto.push((Token)$3.obj);
                                          crearTerceto("PRINT", 1);
                                        }
         
         | PRINT '(' STRING ')' error   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ';'");  }
         | PRINT '(' STRING error       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
         | PRINT STRING ')'             { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
         | PRINT '(' ')'                { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
         | PRINT STRING 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
         | PRINT '('                    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
         | PRINT ')'                    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
         | PRINT			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
         ;

//SELECCION

seleccion: IF condicion THEN sentenciaSeleccion { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1);
                                                  createLabel();
                                                 }

         | IF condicion THEN sentenciaSeleccion ELSE { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF-ELSE");
                                                  setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 2);
                                                  this.tercetoList.add(new Terceto("JMP", new Token(), new Token(), null));
                                                  //System.out.println("agregue un BI en la posicion " + this.tercetoList.size());
                                                  this.pilaBranches.push(this.tercetoList.size());
                                                  //System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BI");
                                                  createLabel();
                                                } sentenciaSeleccion { setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoList.size() + 1); createLabel();}         
         
         | IF condicion error                   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
         ;

condicion: '(' exprAritmetica opLogico exprAritmetica ')' { crearTerceto("CMP", 1);
                                                            this.tercetoList.add(new Terceto((String) this.pilaOperators.pop(), new Token(), new Token(), null));
                                                            //System.out.println("agregue un BF en la posicion " + this.tercetoList.size());
                                                            this.pilaBranches.push(this.tercetoList.size());
                                                            //this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null));
                                                            //System.out.println("agregue un " + this.tercetoList.size() + " en el tope de la pila para el BF");
                                                          }

         
         | exprAritmetica opLogico exprAritmetica ')'            { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
         | '(' exprAritmetica opLogico exprAritmetica error      { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
         | '(' opLogico exprAritmetica ')'                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
         | '(' exprAritmetica opLogico ')'                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
         ;

opLogico: '<' { pilaOperators.push("JAE"); }
        | MENORIGUAL { pilaOperators.push("JA"); }
        | '>' { pilaOperators.push("JBE"); }
        | MAYORIGUAL  { pilaOperators.push("JB"); }
        | IGUAL { pilaOperators.push("JNE"); }
        | DISTINTO { pilaOperators.push("JE"); }
        ;

sentenciaSeleccion: BEGIN sentencias END
                  | sentencia
                  ;

// ITERACION 
                
iteracion: LOOP { createLabelForLoop(); } sentencias UNTIL condicion { 
                  showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL");
                  setDireccionDeSaltoEnTercetoLabelLoop(this.tercetoList.size(), (Integer) this.pilaLoopLabel.pop());
                }
         | LOOP UNTIL condicion { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }      
         ;

//Llamada a funcion

llamadaAFuncion: ID '(' ')' { nombreFuncion = ((Token)$1.obj).getToken();
                              showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Llamada a Funcion " + nombreFuncion);
                              //((Token)$1.obj).getLine();
                              this.tercetoList.add(new Terceto("CALL", new Token("F" + ((Token)$1.obj).getToken()), new Token(), null));
                              //Donde vuelve la funcion luego de ejecutarse.
                            }
                            ;

%%

/*_________________________________________________________________________________________________________*/

private LexicAnalizer la;
private Enumeration elements;
//GUI
private UIMain uiMain;
//paneles para UI
private Style errorPanel;//Panel de Errores
private Style lexPanel;  //Panel de Lexema
private Style linePanel; //Panel para marcar errores
private String lastError = "";

private int cantidadErrores=0;

public Stack pilaLoop;
public Stack pilaBegin;

private List<Token> tmpId; //vector de ids de delaraciones y asignaciones multiples

private List<Token> symbolsTable;

public List<Terceto> tercetoList;
private Stack pilaBranches;	  //para el seguimiento de terceto.
private Stack pilaTerceto;
private Stack pilaLoopLabel;

private Stack pilaBILoop;

private Stack pilaReturn;
private Stack pilaOperators;

private String functionName;
private String nombreFuncion;

private boolean functionNameNext;
private boolean executingFunctionCode;
private boolean errorSemantico;

public ArrayList<String> errorList = new ArrayList<String>();

private int loopLabelNumber;


 public Parser(LexicAnalizer la, UIMain v, boolean debugMe, List<Token> st)
	{
	    this.la = la;

	    yydebug=debugMe;
		
		//UIMain
	    this.uiMain = v;

	        
	    //Panel de Errores
	    errorPanel = new Style(uiMain.getjTextPane1());
	    //Panel de Lexema
	    lexPanel = new Style(uiMain.getjTextPane3());
	    //Panel para marcar errores
	    linePanel = new Style(uiMain.getjTextPane2());

      this.pilaLoop = new Stack();
      this.pilaBegin = new Stack();

      this.functionName = "";
      this.nombreFuncion = "";
      this.tmpId = new ArrayList<Token>();

      this.tercetoList = new ArrayList<Terceto>();
      this.pilaTerceto = new Stack();
      this.pilaBranches = new Stack();
      this.pilaLoopLabel = new Stack();
      this.pilaBILoop = new Stack();
      this.pilaReturn = new Stack();
      this.pilaOperators = new Stack();

      this.symbolsTable = st;
      this.functionNameNext = false;
      this.errorSemantico = false; //Para indicarle al generador de Assembler si generar o hubo un error.

      loopLabelNumber = 1;
      executingFunctionCode = false;
    }

    private int yylex() {

       Token tk = new Token();


       if(!la.hasMoreElements()){
           return 0;
       }else{

           tk = (Token)la.nextElement();

           yylval = new ParserVal(tk);

           while (la.hasMoreElements() && tk.hasError()){
                showErrorParser("Linea " + tk.getLine() + ": Error Lexico: " + tk.getError());
                tk = (Token)la.nextElement();
           }

           if (this.functionNameNext){
              this.functionName = tk.getToken();
              addFunctionToSymbolsTable(tk);
              this.functionNameNext = false;
          }

           //System.out.println(tk.getType());
           
           if(tk.getType().equals("EOF"))
               return ';';
               
           if (tk.hasWarning()){
                showErrorParser("Linea " + tk.getLine() + ": Warning Lexico: " + tk.getWarning());
           } 

           if(this.la.getReservedWordsTable().containsKey(tk.getToken())){
               if(tk.getToken().equalsIgnoreCase("IF"))
                   return IF;
               if(tk.getToken().equalsIgnoreCase("THEN"))
                   return THEN;
               if(tk.getToken().equalsIgnoreCase("ELSE"))
                   return ELSE;
               if(tk.getToken().equalsIgnoreCase("PRINT"))
                   return PRINT;
               if(tk.getToken().equalsIgnoreCase("INT"))
                   return INT;
               if(tk.getToken().equalsIgnoreCase("LOOP")){
                    this.pilaLoop.push(tk);
                    return LOOP;
               }
               if(tk.getToken().equalsIgnoreCase("UNTIL")){
                    if (!this.pilaLoop.empty()){
                        this.pilaLoop.pop();
                    } else {
                        showErrorParser("Linea " + tk.getLine() + ": UNTIL sin su correspondiente LOOP");
                        this.errorSemantico = true;
                    }
                    return UNTIL;
               }
               if(tk.getToken().equalsIgnoreCase("BEGIN")){
                    this.pilaBegin.push(tk);
                    return BEGIN;
               }
               if(tk.getToken().equalsIgnoreCase("END")){
                    if (!this.pilaBegin.empty()){
                        this.pilaBegin.pop();
                    } else {
                        showErrorParser("Linea " + tk.getLine() + ": END sin su correspondiente BEGIN");
                        this.errorSemantico = true;
                    }
                    return END;
               }
               if(tk.getToken().equalsIgnoreCase("IMPORT"))
                   return IMPORT;
               if(tk.getToken().equalsIgnoreCase("RETURN"))
                   return RETURN;
               if(tk.getToken().equalsIgnoreCase("FUNCTION")){
                   this.functionNameNext = true;
               	   return FUNCTION;
               }
           }else{
               if(tk.getType().equalsIgnoreCase("Identificador")){
                   return ID;
               }else{
                   if(tk.getType().equalsIgnoreCase("Constante Negativa") || tk.getType().equalsIgnoreCase("Constante Positiva")){
                        return CTEINT;
                   }else{
                        if(tk.getType().equalsIgnoreCase("Cadena")){
                                return STRING;
                        }else{
                        	if(tk.getToken().equalsIgnoreCase("+"))
                                    return '+';
                            if(tk.getToken().equalsIgnoreCase("-"))
                                    return '-';
                            if(tk.getToken().equalsIgnoreCase("*"))
                                    return '*';
                            if(tk.getToken().equalsIgnoreCase("/"))
                                    return '/';
                            if(tk.getToken().equalsIgnoreCase("="))
                                    return '=';
                            if(tk.getToken().equalsIgnoreCase(">"))
                                    return '>';
                            if(tk.getToken().equalsIgnoreCase("<"))
                                    return '<';
                            if(tk.getToken().equalsIgnoreCase(">="))
                                    return MAYORIGUAL;
                            if(tk.getToken().equalsIgnoreCase("!="))
                                    return DISTINTO;
                            if(tk.getToken().equalsIgnoreCase("<="))
                                    return MENORIGUAL;
                            if(tk.getToken().equalsIgnoreCase("=="))
                                    return IGUAL;
                            if(tk.getToken().equalsIgnoreCase(")"))
                                    return ')';
                            if(tk.getToken().equalsIgnoreCase("("))
                                    return '(';
                            if(tk.getToken().equalsIgnoreCase(";"))
                                    return ';';
                            if(tk.getToken().equalsIgnoreCase(","))
                                    return ',';
                        }
                   }
               }
           }
       }
       return 0;
    }

    private void yyerror(String string) {
        //showErrorParser("ERROR FROM PARSER : " + string);
    }
	
    private void showInfoParser(String s){
        //lexPanel.setCursiva("ShowInfoParser : ");
        lexPanel.setNegrita(s);
        lexPanel.newLine();
    }
	
    private void showErrorParser(String s){
        //errorPanel.setCursiva("ShowErrorParser : ");
	    //    errorPanel.setNegrita(s);
	    //    errorPanel.newLine();	
	    errorList.add(s);
	        cantidadErrores++;
    }

    private Token getTokenFromSymbolTable(String tokenId){
        for (Token t : this.symbolsTable){
            if (t.getToken().equals(tokenId)){
                return t;
            }
        }
        return null;
    }
        
    private void saveVariableType(String tipo){
        for (Token t: this.tmpId){
            t.setVariableType(tipo);
        }   
    }

    private void addFunctionNameToToken(String functionName, Token t){
      if (!functionName.equals("")){
        t.setLexema(functionName + "&" + t.getToken());
        t.functionName = functionName;
      }
    }

    private void addFunctionToSymbolsTable(Token t){
      if (t.getType().equals("Identificador")){
          Token tokenInSymbolTable = getTokenFromSymbolTable(t.getToken());
          if (tokenInSymbolTable == null){
            this.symbolsTable.add(t);
          }else{
            showErrorParser("Linea "+ t.getLine() +": Error Semantico: La funcion " + t.getToken() + " ya fue declarada");
            this.errorSemantico = true;
          }
        }
    }

    private void addTmpIdToSymbolsTable(){
      for (Token t: this.tmpId){
        if (t.getType().equals("Identificador")){
          Token tokenInSymbolTable = getTokenFromSymbolTable(t.getToken());
          if (tokenInSymbolTable == null){
            this.symbolsTable.add(t);
          }else{
            if (!t.getToken().contains("&")){
              showErrorParser("Linea "+ t.getLine() +": Error Semantico: " + t.getToken() + " ya fue declarado");
              this.errorSemantico = true;
            }
          }
        }
      }
    }

    // si presedencia 1 => operacion, primer Operando, segundo Operando
    // si presedencia 2 => operacion, segundo Operando, primer Operando
    private void crearTerceto(String operador, int presedencia){
      Object operand1 = this.pilaTerceto.pop();
      Object operand2 = this.pilaTerceto.pop();
      Terceto t;
      /*if (operand1 instanceof Integer){
        Object aux = operand1;
        operand1 = this.tercetoList.get(((Integer)aux).intValue() - 1);
      }
      if (operand2 instanceof Integer){
        Object aux2 = operand2;
        operand2 = this.tercetoList.get(((Integer)aux2).intValue() - 1);
      }*/
      if (presedencia == 1){
        t = new Terceto(operador, operand1, operand2, null);
      } else {
        t = new Terceto(operador, operand2, operand1, null);
      }
      this.tercetoList.add(t);
      this.pilaTerceto.push(this.tercetoList.size());
    }

    public List<Terceto> getTercetoList(){ return this.tercetoList; }

    private void setDireccionDeSaltoEnTerceto(Integer terceto, int dirDeSalto){
        ((Terceto) this.tercetoList.get(terceto - 1)).setFirstOperand("LABEL" + dirDeSalto);
        //System.out.println("Agregar Dir Salto en Terceto #" + terceto.toString() + " el valor " + dirDeSalto);
        //System.out.println("Terceto: " + ((Terceto) this.tercetoList.get(terceto - 1)).toString());
    }

    private void setDireccionDeSaltoEnTercetoLabelLoop(Integer terceto, int dirDeSalto){
        ((Terceto) this.tercetoList.get(terceto - 1)).setFirstOperand("LABLOOP" + dirDeSalto);
        //System.out.println("Agregar Dir Salto en Terceto #" + terceto.toString() + " el valor " + dirDeSalto);
        //System.out.println("Terceto: " + ((Terceto) this.tercetoList.get(terceto - 1)).toString());
    }

    private void createLabel(){
      Token label = new Token(); label.setLexema("LABEL" + Integer.toString(this.tercetoList.size() + 1));
      this.tercetoList.add(new Terceto("LABEL", label, new Token(), null));
    }

    private void createLabelForLoop(){
      this.pilaLoopLabel.push(this.loopLabelNumber);
      Token label = new Token(); label.setLexema("LABLOOP" + Integer.toString(this.loopLabelNumber));
      this.tercetoList.add(new Terceto("LABEL", label, new Token(), null));
      this.loopLabelNumber++;
    }

    private void checkContain(Token t) {
      String token = t.getToken();
      if (executingFunctionCode){
        token = functionName + "&" + t.getToken();
      }
      if (getTokenFromSymbolTable(token) == null){
        showErrorParser("Linea " + t.getLine() + ": Error Semantico: Variable "+t.getToken()+" no declarada.");
        this.errorSemantico = true;
      }
    }

    private void checkNameMangling(Token t){
      if (executingFunctionCode){
        String s = functionName + "&" + t.getToken();
        t.setLexema(s);
      }
    }
    
    public boolean compilar(){
    return (this.cantidadErrores > 1);
    }
