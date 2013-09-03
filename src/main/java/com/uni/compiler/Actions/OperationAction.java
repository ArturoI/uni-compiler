package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class OperationAction extends Action {

	public OperationAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		lexicAnalizer.inicToken();
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.setLexema(String.valueOf(c));
		nextToken.setTokenType(String.valueOf(c));
	}

}
