package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class DeleteCharacterAction extends Action {

	public DeleteCharacterAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.deleteLastChar();
	}

}
