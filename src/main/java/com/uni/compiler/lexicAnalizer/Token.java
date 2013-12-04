package com.uni.compiler.lexicAnalizer;

public class Token {

    private String type;
    private String variableType;
    private String lexema;
    private int line;
    private String errorMessage = "";
    private String warningMessage = "";
    public String functionName;
    private int value;
    private boolean isVariable;

    public Token() {
        this.functionName = "";
        this.variableType = "";
        this.isVariable=false;
    }

    public Token(String lexema) {
        this.lexema = lexema;
        this.functionName = "";
        this.variableType = "";
        this.isVariable=false;
    }
    
    public boolean isVariable(){
    	return this.isVariable;
    }
    
    public void setIsVariable(boolean b){
    	this.isVariable=b;
    }

    public void addChar(char c) {
        lexema += c;
    }

    public void deleteLastChar() {
        lexema = lexema.substring(0, lexema.length() - 1);
    }

    public void setError(String message) {
        errorMessage = message;
    }

    public void setLexema(String l) {
        lexema = l;
    }

    public void setWarning(String message) {
        warningMessage = message;
    }

    public boolean hasError() {
        return !"".equals(errorMessage);
    }

    public boolean hasWarning() {
        return !"".equals(warningMessage);
    }

    public String getWarning() {
        return this.warningMessage;
    }

    public String getError() {
        return this.errorMessage;
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

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return this.line;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public String getVariableType() {
        return variableType;
    }

    @Override
    public String toString() {
        return this.lexema;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    public Token(String lexema, String t) {
        this.lexema = lexema;
        this.functionName = "";
        this.variableType = "";
        this.isVariable=false;
        this.type = t;
    }
}
