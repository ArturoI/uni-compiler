package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class NoConsumeAction extends Action {

	public NoConsumeAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		nextToken = lexicAnalizer.getTokenInConstruction();
		lexicAnalizer.saveCharacter(c);
	}
}