package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class ConsumeAction extends Action {

	public ConsumeAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.addChar(c);
	}
}
