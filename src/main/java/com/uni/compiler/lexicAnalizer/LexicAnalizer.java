package com.uni.compiler.lexicAnalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LexicAnalizer implements Enumeration {
	private FileManager source;
	private List<Token> symbolsTable;
	private AnalizerFactory analizerFactory;
	public int line = 1;
	private Token nextToken = null;
	private Character oldChracter = null;
        

	private HashMap<String, String> reservedWords;

	public Token getTokenInConstruction() {
		return nextToken;
	}
	
	public void inicToken(){
		nextToken = new Token();
		nextToken.setLexema("");
                nextToken.setTokenType("");
	}

	public LexicAnalizer(String path, List<Token> st) {
		source = new FileManager(path);
                this.analizerFactory = new AnalizerFactory(this);
                this.reservedWords = this.analizerFactory.createReservedWords();
                this.symbolsTable = st;
	}

	public void saveCharacter(Character c) {
		oldChracter = c;
	}

	public Token createToken() throws IOException {
		inicToken();
		Character charac = null;
		State currentState = analizerFactory.getBeginState();// initialState
		State nextState = null;
		
                if (oldChracter != null) {
                    Integer c = charMapping(oldChracter);
                    nextState = currentState.getNextState(c);
                    currentState.getAction(c).executeAction(
                                    oldChracter);
                    currentState = nextState;
                    oldChracter = null;   
		}
		
                while (!currentState.isFinal() && source.hasMoreElement()){
                    charac = source.readChar();
                    if (charac != null) {
                        Integer c = charMapping(charac);
			nextState = currentState.getNextState(c);
			currentState.getAction(c).executeAction(charac);
			currentState = nextState;
                    } else {
                        nextToken.setTokenType("EOF");
                    }
                }
                
                line = source.getLineNumber();
                nextToken.setLine(line);
                if (charac == null) {
                    if (!currentState.isFinal()) {
                            nextToken.setError("Error in line: " + line + " incomplete Token");
                    }
                } else { 
                    if (charac.equals('\n')) { nextToken.setLine(line-1); }
                }
                
                if (nextToken.getType().equals("Identificador")){
                    String value = reservedWords.get(nextToken.getToken());
                    if (value != null){ nextToken.setTokenType("Palabra reservada"); }
                }
		
                return nextToken;

	}

	public Token getToken() throws IOException {
            if (source.hasMoreElement()){
                Token currentToken = createToken();
                
                if (currentToken.getType() == "Comentario") {
                    currentToken = createToken();
		} else {
                    if (currentToken.getType().equals("Constante Positiva")) {
			double d = Math.pow(2, 15) - 1;
			double a = new Double(currentToken.getToken());
			if (a > d) {
				currentToken.setError("Constante fuera de rango, maximo valor = 32767");
			}
                    } else {
                        if (currentToken.getType().equals("Constante Negativa")) {
                            double nd = (Math.pow(2, 15)) * -1;
                            double a = new Double(currentToken.getToken());
                            if (nd > a) {
                                    currentToken.setError("Constante fuera de rango, minimo valor = -32768");
                            }
                        } else {
                            if (currentToken.getType().equals("Identificador")) {
                                if (currentToken.getToken().length() > 15) {
                                    String tokenValue = String.copyValueOf(currentToken
                                                    .getToken().toCharArray(), 0, 15);
                                    currentToken.setToken(tokenValue);
                                    currentToken.setWarning("Identifier too long");
                                }
                            }
                        }
                    }
                }

		addToSymbolsTable(currentToken);
		return currentToken;
            } else {
                Token last = new Token();
                last.setLexema("FIN DEL ARCHIVO");
                last.setTokenType("EOF");
                this.source.close();
                return last;
            }
	}

	public void addToSymbolsTable(Token t) {
            //Si el tipo es identificador se agrega en el parser - por el nombre de la funcion appendeado.
		if (!t.hasError()
                    && (t.getType().equals("Constante") || (t.getType().equals("Cadena")))
                    && !reservedWords.containsKey(t.getToken())
                    && !symbolsTable.contains(t.getToken())
                   ) {

			symbolsTable.add(t);
			//System.out.println("[V] Token added line: " + line + " TokenType: "
			//		+ t.getType() + " Token: " + t.getToken());
		} else {
			//System.out.println("[X] Token NOT added to the symbol table. line: "
			//		+ line + " TokenType: " + t.getType() + " Token: "
			//		+ t.getToken());
		}

	}

	public Integer charMapping(Character c) {
		if (Character.isLetter(c)) {
			return new Integer(1);
		}
		if (Character.isDigit(c)) {
			return new Integer(2);
		}
		if (c.equals('+')) {
			return new Integer(3);
		}
		if (c.equals('-')) {
			return new Integer(4);
		}
		if (c.equals('*')) {
			return new Integer(5);
		}
		if (c.equals('=')) {
			return new Integer(6);
		}
		if (c.equals('<')) {
			return new Integer(7);
		}
		if (c.equals('!')) {
			return new Integer(8);
		}
		if (c.equals('>')) {
			return new Integer(9);
		}
		if (c.equals('/')) {
			return new Integer(10);
		}
		if (c.equals('\\')) {
			return new Integer(11);
		}
		if (c.equals('(')) {
			return new Integer(12);
		}
		if (c.equals(')')) {
			return new Integer(13);
		}
		if (c.equals(';')) {
			return new Integer(14);
		}
		if (c.equals(',')) {
			return new Integer(15);
		}
		if (c.equals('"')) {
			return new Integer(16);
		}
		if (c.equals('\n')) {
			return new Integer(17);
		}
		if (Character.isWhitespace(c)) {
			return new Integer(18);
		}
		if (c.equals('\t')) {
			return new Integer(19);
		}

		return new Integer(-1);
	}
        
        public HashMap getReservedWordsTable (){
            return this.reservedWords;
        }
        
        public List<Token> getSymbolsTable (){
            return this.symbolsTable;
        }
        
        @Override
        public boolean hasMoreElements() {
            return source.hasMoreElement();
        }
        
        @Override
        public Token nextElement(){
            try {
                return getToken();
            } catch (IOException ex) {
                Logger.getLogger(LexicAnalizer.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
}
