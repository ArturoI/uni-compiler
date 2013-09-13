package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class ErrorAction extends Action{

	private LexicAnalizer lexicAnalizer;
	
	public ErrorAction(LexicAnalizer la){
		lexicAnalizer=la;
	}
	
	@Override
	public void executeAction(Character c) {
		lexicAnalizer.saveCharacter(c);
		nextToken=lexicAnalizer.getTokenInConstruction();
		nextToken.setError("Token Invalido");
	}

}
