package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class ConstAction extends Action {

	public ConstAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		lexicAnalizer.inicToken();
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.addChar(c);
		nextToken.setTokenType("constante");
	}

}
