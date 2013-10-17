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

programa:   declaraciones sentencias
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

conjVariables: conjVariables ',' ID { addFunctionNameToToken(this.functionName, ((Token)$3.obj)); this.tmpId.add((Token)$3.obj);  }	
             | ID                   { addFunctionNameToToken(this.functionName, ((Token)$1.obj)); this.tmpId.add((Token)$1.obj);  }
	     ;

funcion: FUNCTION ID bloqueFuncion { }
       ;

bloqueFuncion:	BEGIN declaracionFuncion sentenciasF END { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Cuerpo de la funcion."); this.functionName = ""; }
		
                | BEGIN END                              { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera el cuerpo de la funcion");  }
                | declaracionFuncion sentenciasF END     { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera BEGIN");  }
                | BEGIN declaracionFuncion sentenciasF error { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera END");  }
                ;

declaracionFuncion: variables			{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Variables de la funcion."); }//this.functionName = ((Token)$0.obj).getToken(); }
		  | IMPORT conjVariables ';'    { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }//this.functionName = ((Token)$0.obj).getToken();}
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

//RETURN

returnF: RETURN '(' exprAritmetica ')'
       | RETURN error                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
       | RETURN '(' error                   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
       | RETURN '(' exprAritmetica error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
       ;

//SELECCION

seleccionF: IF condicion THEN sentenciaSeleccionF                            { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF"); }
          | IF condicion THEN sentenciaSeleccionF ELSE sentenciaSeleccionF   { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF-ELSE");  }
          | IF condicion sentenciaSeleccionF                                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
          ;

sentenciaSeleccionF: BEGIN sentenciasF END
                   | sentenciaF
                   ;

// ITERACION

iteracionF: LOOP sentenciasF UNTIL condicion { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
          | LOOP UNTIL condicion             { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
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
         | error { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": Error Sintactico : Sentencia Invalida"); }
         ;

//ASIGNACION

asignacion: ID '=' exprAritmetica   { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Asignacion.");
                                      this.pilaTerceto.push((Token)$1.obj);
                                      crearTerceto("=", 1);
                                    }
         
          | '=' exprAritmetica      { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
          | ID '=' error            { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
          | ID exprAritmetica       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
          ;

exprAritmetica:	exprAritmetica '+' termino { crearTerceto("+", 2); }
              | exprAritmetica '-' termino { crearTerceto("-", 2); }
              | termino
             
              | exprAritmetica '+' error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              | '+' termino                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }  	
              | exprAritmetica '-' error    { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              | '-' termino                 { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
              ;

termino: termino '*' factor { crearTerceto("*", 2); }
       | termino '/' factor { crearTerceto("/", 2); }
       | factor
       
       | termino '*' error   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
       | '*' factor          { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 	
       | termino '/' error   { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
       | '/' factor          { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 	
       ;

factor: ID { this.pilaTerceto.push((Token)$1.obj); }
      | CTEINT { this.pilaTerceto.push((Token)$1.obj); }
      ;

//IMPRESION

impresion: PRINT '(' STRING ')' ';'	{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Impresion");
                                          this.pilaTerceto.push(new Token());
                                          this.pilaTerceto.push((Token)$3.obj);
                                          crearTerceto("@PRINT", 1);
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

seleccion: IF condicion THEN sentenciaSeleccion                          { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF");
                                                                           //this.pi.set((Integer)this.pila.pop(),new DPI(this.pi.size()+2));
                                                                           //Agregar metodo para setear en el terceto X la direccion de salto Y.
                                                                           setDireccionDeSaltoEnTerceto((Integer) this.pilaBranches.pop(), this.tercetoId + 2);
                                                                           this.pilaBranches.push(this.tercetoId);
                                                                           this.tercetoList.add(new Terceto("BI", new Token(), new Token(), null, ++this.tercetoId ));
                                                                         }

         | IF condicion THEN sentenciaSeleccion ELSE sentenciaSeleccion  { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF-ELSE");  }         
         | IF condicion error                                            { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN");  }
         ;

condicion: '(' exprAritmetica opLogico exprAritmetica ')' { crearTerceto(((Token)$3.obj).getToken(), 2); 
                                                            this.pilaBranches.push(this.tercetoId);
                                                            this.tercetoList.add(new Terceto("BF", new Token(), new Token(), null, ++this.tercetoId));
                                                          }
         
         | exprAritmetica opLogico exprAritmetica ')'            { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('."); }
         | '(' exprAritmetica opLogico exprAritmetica error      { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'."); }
         | '(' opLogico exprAritmetica ')'                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
         | '(' exprAritmetica opLogico ')'                       { showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
         ;

opLogico: '<' 
        | MENORIGUAL 
        | '>' 
        | MAYORIGUAL  
        | IGUAL 
        | DISTINTO
        ;

sentenciaSeleccion: BEGIN sentencias END
                  | sentencia
                  ;

// ITERACION

iteracion: LOOP sentencias UNTIL condicion { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
         | LOOP UNTIL condicion            { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }      
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

public Stack pilaLoop;
public Stack pilaBegin;

private List<Token> tmpId; //vector de ids de delaraciones y asignaciones multiples

private List<Token> symbolsTable;

public List<Terceto> tercetoList;
private int tercetoId;
private Stack pilaBranches;	  //para el seguimiento de terceto.

private Stack pilaTerceto;

private String functionName;
private boolean functionNameNext;

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
      this.tmpId = new ArrayList<Token>();

      this.tercetoList = new ArrayList<Terceto>();
      this.pilaTerceto = new Stack();
      this.pilaBranches = new Stack();

      this.symbolsTable = st;
      this.functionNameNext = false;
      this.tercetoId = 1;
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
        errorPanel.setNegrita(s);
        errorPanel.newLine();	
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
      if (presedencia == 1){
        t = new Terceto(operador, operand1, operand2, null, this.tercetoId);
      } else {
        t = new Terceto(operador, operand2, operand1, null, this.tercetoId);
      }
      this.tercetoList.add(t);
      this.pilaTerceto.push(this.tercetoId);
      this.tercetoId++;
    }

    public List<Terceto> getTercetoList(){ return this.tercetoList; }

    private void setDireccionDeSaltoEnTerceto(Integer terceto, int dirDeSalto){
        for (Terceto t: this.tercetoList){
            if (t.getId() == terceto){
                t.setFirstOperand(dirDeSalto);
            }
        }
    }