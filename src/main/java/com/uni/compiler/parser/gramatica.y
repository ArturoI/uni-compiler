%{
	package com.uni.compiler.parser;
	
	import java.util.Enumeration;


	import com.uni.compiler.UI.UIMain;
	import com.uni.compiler.UI.Style;
	import com.uni.compiler.lexicAnalizer.*;

%}

%token ID INT CTEINT STRING IF THEN ELSE BEGIN END IMPORT MENORIGUAL MAYORIGUAL IGUAL DISTINTO LOOP UNTIL PRINT RETURN FUNCTION



%start programa

%%

programa:	declaraciones sentencias { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": Programa."); }

//- - - - - - - - - - - - - - - - - - - - D E C L A R A C I O N - - - - - - - - - - - - - - - - - - - - 
				
declaraciones: 	declaraciones declaracion ';'
				| declaracion ';'
				;

declaracion:	variables
				| funcion
				;

variables: 		INT conjVariables 	{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": Declaracion"); }
				;

conjVariables: 	conjVariables ',' ID 	
				| ID
				;

funcion: 		FUNCTION ID bloqueFuncion
				| FUNCTION bloqueFuncion	{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba Nombre de la funcion"); }
				;

bloqueFuncion:	BEGIN  declaracionFuncion sentenciasFuncion END 	{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Cuerpo de la funcion."); }
				;

declaracionFuncion:		variables ';'				{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Variables de la funcion."); }
				| IMPORT conjVariables ';'      	{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "sentencia IMPORT de la funcion."); }
						;

sentenciasFuncion:		sentenciasFuncion sentencia
						| sentenciasFuncion return
						| sentencia
						| return
						;

return:					RETURN '(' exprAritmetica ')'
						| RETURN exprAritmetica ')'  		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
						| RETURN '(' ')'			 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para retornar"); }
						| RETURN '(' exprAritmetica 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
						;

//- - - - - - - - - - - - - - - - - F I N   D E C L A R A C I O N - - - - - - - - - - - - - - - - -

//- - - - - - - - - - - - - - - - - - - - S E N T E N C I A - - - - - - - - - - - - - - - - - - - -

sentencias: 	sentencias sentencia ';'
				| sentencia
				| ';'
				;

sentencia: 		asignacion
				| impresion 
				| seleccion
				| iteracion
				| error 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Sentencia no valida ERROR"); }
				;

//ASIGNACION

asignacion: 	ID '=' exprAritmetica
				| '=' exprAritmetica 			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera Identificador antes de ="); }
				| ID '=' error				{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se espera un valor para asignar al Identificador."); }
				| ID exprAritmetica			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Asignacion no valida."); }
				;

exprAritmetica:	exprAritmetica '+' termino
				| exprAritmetica '-' termino
				| termino
				| exprAritmetica '+' error		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
				| '+' termino					{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); }  	
				| exprAritmetica '-' error		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
				| '-' termino					{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
				;

termino: 		termino '*' factor 
				| termino '/' factor 
				| factor
				| termino '*' error				{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
				| '*' factor					{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 	
				| termino '/' error				{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 
				| '/' factor					{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Expresion invalida"); } 	
				;

factor:			ID 
				| CTEINT
				;

//IMPRESION

impresion: 		PRINT '(' STRING ')'	{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Impresion");  }
				| PRINT '(' STRING 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
				| PRINT STRING ')' 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
				| PRINT '(' ')' 		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba una cadena para imprimir"); }
				| PRINT STRING 			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba () son requeridos para imprimir"); }
				| PRINT '(' 			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba la cadena a imprimir y ')'"); }
				| PRINT ')' 			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' y la cadena a imprimir"); }
				| PRINT					{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '(' la cadena a imprimir y ')'"); }
				;

//SELECCION

seleccion:  	IF '(' condicion ')' THEN sentenciaSeleccion   							 { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF-ELSE"); }
				| IF '(' condicion ')' THEN sentenciaSeleccion ELSE sentenciaSeleccion   { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia IF-ELSE");  }
				
				| IF condicion ')' THEN sentenciaSeleccion		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
				| IF '(' condicion THEN sentenciaSeleccion		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
				| IF '(' ')' THEN sentenciaSeleccion			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba condicion "); }
				| IF '(' condicion ')' sentenciaSeleccion		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
				
				| IF condicion ')' THEN sentenciaSeleccion ELSE sentenciaSeleccion		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba '('"); }
				| IF '(' condicion THEN sentenciaSeleccion ELSE sentenciaSeleccion		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba ')'"); }
				| IF '(' ')' THEN sentenciaSeleccion ELSE sentenciaSeleccion			{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba condicion "); }
				| IF '(' condicion ')' sentenciaSeleccion ELSE sentenciaSeleccion		{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba THEN"); }
				;

condicion: 		exprAritmetica opLogico exprAritmetica  
				| opLogico exprAritmetica				{ showErrorParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Error Sintactico : Se esperaba un valor para comparar."); }
				;

opLogico: 		'<' 
				| MENORIGUAL 
				| '>' 
				| MAYORIGUAL  
				| IGUAL 
				| DISTINTO
				;

sentenciaSeleccion: BEGIN sentencias END
					| sentencia ';'
					;

// ITERACION

iteracion:		LOOP sentencias UNTIL condicion { showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL"); }
				| LOOP UNTIL condicion			{ showInfoParser("Linea " + ((Token)$1.obj).getLine() + ": " + "Sentencia LOOP-UNTIL sin cuerpo"); }
				;
%%

/*_________________________________________________________________________________________________________*/

/*_________________________________________________________________________________________________________*/

private LexicAnalizer la;
private Enumeration elements;
//GUI
private UIMain uiMain;
//paneles para UI
private Style errorPanel;//Panel de Errores
private Style lexPanel;  //Panel de Lexema
private Style linePanel; //Panel para marcar errores

 public Parser(LexicAnalizer la, UIMain v, boolean debugMe)
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

	}

    private int yylex() {

       Token tk = new Token();

       if(!la.hasMoreElements()){
           return 0;
       }else{

           tk = (Token)la.nextElement();
           
           yylval = new ParserVal(tk);

           //System.out.println(tk.getType());
           
           if(tk.getType().equals("EOF"))
               return 0;

           if(this.la.getReservedWordsTable().containsKey(tk.getToken())){
               if(tk.getToken().equalsIgnoreCase("IF"))
                   return IF;
               if(tk.getToken().equalsIgnoreCase("ELSE"))
                   return ELSE;
               if(tk.getToken().equalsIgnoreCase("PRINT"))
                   return PRINT;
               if(tk.getToken().equalsIgnoreCase("INT"))
                   return INT;
               if(tk.getToken().equalsIgnoreCase("LOOP"))
                   return LOOP;
               if(tk.getToken().equalsIgnoreCase("UNTIL"))
                   return UNTIL;
               if(tk.getToken().equalsIgnoreCase("BEGIN"))
                   return BEGIN;
               if(tk.getToken().equalsIgnoreCase("END"))
                   return END;
               if(tk.getToken().equalsIgnoreCase("IMPORT"))
                   return IMPORT;
               if(tk.getToken().equalsIgnoreCase("RETURN"))
                   return RETURN;
               if(tk.getToken().equalsIgnoreCase("FUNCTION"))
               	   return FUNCTION;
           }else{
               if(tk.getType().equalsIgnoreCase("Identificador")){
                   return ID;
               }else{
                   if(tk.getType().equalsIgnoreCase("Constante")){
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

    private void yyerror(String string) {}
	
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