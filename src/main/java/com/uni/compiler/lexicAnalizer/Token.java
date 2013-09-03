package com.uni.compiler.lexicAnalizer;

public class Token {

	private String type;
	private String lexema;
	// private int line;
	private String errorMessage = "";
	private String warningMessage = "";

	public Token() {
	}

	public void addChar(char c) {
		lexema += c;
	}
	
	public void deleteLastChar(){
		lexema=lexema.substring(0, lexema.length()-1);
	}

	public void setError(String message) {
		errorMessage = message;
	}
	
	public void setLexema(String l){
		lexema=l;
	}

	public void setWarning(String message) {
		warningMessage = message;
	}

	public boolean hasError() {
		return !"".equals(errorMessage);
	}

	public boolean hasWarning() {
		return "".equals(warningMessage);
	}

	public String getType() {
		return type;
	}

	public String getToken() {
		return lexema;
	}

	public void setTokenType(String t) {
		type = t;
	}

	// public void setLine(int l){
	// line=l;
	// }

	public void setToken(String token) {
		lexema = token;
	}

}
