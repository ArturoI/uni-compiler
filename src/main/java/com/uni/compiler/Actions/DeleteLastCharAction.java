package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class DeleteLastCharAction extends Action {

	public DeleteLastCharAction(LexicAnalizer la) {
		lexicAnalizer = la;

	}

	@Override
	public void executeAction(Character c) {
		//lexicAnalizer.inicToken();
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.deleteLastChar();
	}

}

