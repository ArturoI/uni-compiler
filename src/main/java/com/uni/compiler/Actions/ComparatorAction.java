package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class ComparatorAction extends Action {

	public ComparatorAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.addChar(c);
                nextToken.setTokenType("Comparador");
	}

}
