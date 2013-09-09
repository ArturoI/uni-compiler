package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class NCComparatorAction extends Action{
	private LexicAnalizer lexicAnalizer;
	
	public NCComparatorAction(LexicAnalizer la){
		lexicAnalizer=la;
	}

	@Override
	public void executeAction(Character c) {
		lexicAnalizer.saveCharacter(c);
                nextToken = lexicAnalizer.getTokenInConstruction();
                nextToken.setTokenType("Comparador");
	}
	

}
