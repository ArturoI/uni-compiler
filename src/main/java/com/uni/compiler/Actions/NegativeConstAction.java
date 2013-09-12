package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class NegativeConstAction extends Action {

	public NegativeConstAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		//lexicAnalizer.inicToken();
                nextToken = lexicAnalizer.getTokenInConstruction();
                nextToken.addChar(c);
		nextToken.setTokenType("Constante Negativa");
	}

}
