package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class IdAction extends Action {

	public IdAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		//lexicAnalizer.inicToken();
            	lexicAnalizer.saveCharacter(c);
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.setTokenType("Identificador");
	}

}
