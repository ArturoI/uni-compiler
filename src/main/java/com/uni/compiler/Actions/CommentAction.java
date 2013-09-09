package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class CommentAction extends Action {

	public CommentAction(LexicAnalizer t) {
		lexicAnalizer = t;
	}

	@Override
	public void executeAction(Character c) {
		//lexicAnalizer.inicToken();
		nextToken = lexicAnalizer.getTokenInConstruction();
		nextToken.setTokenType("Comentario");
	}

}
