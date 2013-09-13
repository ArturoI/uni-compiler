package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class NCOperationAction extends Action {

	public NCOperationAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		//lexicAnalizer.inicToken();
		nextToken = lexicAnalizer.getTokenInConstruction();
                lexicAnalizer.saveCharacter(c);
		nextToken.setTokenType("Operacion");
	}

}
