package com.uni.compiler.lexicAnalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LexicAnalizer {
	private FileManager source;
	private List<Token> symbolsTable = new ArrayList<Token>();
	private AnalizerFactory analizerFactory = AnalizerFactory.getInstance(this);
	public int line = 1;
	private Token nextToken = null;
	private Character oldChracter = null;

	private HashMap<String, String> reservedWords = analizerFactory
			.createReservedWords();

	public Token getTokenInConstruction() {
		return nextToken;
	}
	
	public void inicToken(){
		nextToken = new Token();
		nextToken.setLexema("");
                nextToken.setTokenType("");
	}

	public LexicAnalizer(String path) {
		source = new FileManager(path);
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
		
                while (!currentState.isFinal()){
                    charac = source.readChar();
                    if (charac != null) {
                        Integer c = charMapping(charac);
			nextState = currentState.getNextState(c);
			currentState.getAction(c).executeAction(charac);
			currentState = nextState;
                
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
		Token currentToken = createToken();
		// We don't need the comments.
                if (currentToken.getType() == "Comentario") {
			currentToken = createToken();
		}

		if (currentToken.getType().equals("Constante")) {
			double d = Math.pow(2, 32) - 1;
			double a = new Double(currentToken.getToken());
			if (a > d) {
				currentToken.setError("Error in line: " + line
						+ "constant out of range");
			}
		} else {
			if (currentToken.getType().equals("Identificador")) {
				if (currentToken.getToken().length() > 15) {
					String tokenValue = String.copyValueOf(currentToken
							.getToken().toCharArray(), 0, 15);
					currentToken.setToken(tokenValue);
					currentToken.setWarning("Warning in line: " + line
							+ " Identifier too long");
				}
			}
		}

		addToSymbolsTable(currentToken);
		return currentToken;
	}

	public void addToSymbolsTable(Token t) {
		if (!t.hasError()
				&& (t.getType().equals("Identificador")
						|| t.getType().equals("Constante") || (t.getType()
						.equals("Cadena")))
				&& !reservedWords.containsKey(t.getToken())) {

			symbolsTable.add(t);
			System.out.println("[V] Token added line: " + line + " TokenType: "
					+ t.getType() + " Token: " + t.getToken());
		} else {
			System.out.println("[X] Token NOT added to the symbol table. line: "
					+ line + " TokenType: " + t.getType() + " Token: "
					+ t.getToken());
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

}
