package com.uni.compiler.lexicAnalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LexicAnalizer {
	private FileManager source;
	private List<Token> symbolsTable = new ArrayList<Token>();
	private AnalizerFactory analizerFactory = AnalizerFactory.getInstance(this);
	private int line = 0;
	private Token nextToken = null;
	private Character oldChracter = null;

	private HashMap<String, String> reservedWords = analizerFactory
			.createReservedWords();

	public Token getTokenInConstruction() {
		return nextToken;
	}
	
	public void inicToken(){
		nextToken=new Token();
		nextToken.setLexema("");
	}

	public LexicAnalizer(String path) {
		source = new FileManager(path);
	}

	public void saveCharacter(Character c) {
		oldChracter = c;
	}

	public Token createToken() throws IOException {
		nextToken = null;
		Character charac;
		State currentState = analizerFactory.getBeginState();// initialState
		State nextState = null;
		if (oldChracter != null) {

			nextState = currentState.getNextState(charMapping(oldChracter));
			currentState.getAction(charMapping(oldChracter)).executeAction(
					oldChracter);
			currentState = nextState;
			oldChracter = null;

		}
		while (!(currentState.isFinal())
				&& ((charac = source.readChar()) != null)) {

			nextState = currentState.getNextState(charMapping(charac));
			currentState.getAction(charMapping(charac)).executeAction(charac);
			currentState = nextState;
			if (charac.equals('\n')) {
				line += 1;
			}
		}

		if (!(currentState.isFinal()) && ((charac = source.readChar()) == null)) {
			nextToken.setError("Error in line: " + line + " uncomplete");
		}

		return nextToken;

	}

	public Token getToken() throws IOException {
		Token currentToken = createToken();

		if (currentToken.getType() == "comentario") {
			currentToken = createToken();
		}

		if (currentToken.getType().equals("constante")) {
			double d = Math.pow(2, 32) - 1;
			double a = new Double(currentToken.getToken());
			if (a > d) {
				currentToken.setError("Error in line: " + line
						+ "constant out of range");
			}
		} else {
			if (currentToken.getType().equals("identificador")) {
				if (currentToken.getToken().length() > 15) {
					String tokenValue = String.copyValueOf(currentToken
							.getToken().toCharArray(), 0, 15);
					currentToken.setToken(tokenValue);
					currentToken.setWarning("Warning in line: " + line
							+ " identifier too long");
				}
			}
		}

		addToSymbolsTable(currentToken);

		return currentToken;
	}

	public void addToSymbolsTable(Token t) {
		if (!t.hasError()
				&& (t.getType().equals("identificador")
						|| t.getType().equals("constante") || (t.getType()
						.equals("cadena")))
				&& !reservedWords.containsKey(t.getToken())) {

			symbolsTable.add(t);
			System.out.println("Token added.... line: " + line + " TokenType: "
					+ t.getType() + " Token: " + t.getToken());
		} else {
			System.out.println("Token not added to the symbol table.... line: "
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

}
