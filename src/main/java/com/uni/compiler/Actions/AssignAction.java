/*
 * To change this template, choose Tools | Templates
*/

package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class AssignAction extends Action {

	public AssignAction(LexicAnalizer la) {
		lexicAnalizer = la;
	}

	@Override
	public void executeAction(Character c) {
		nextToken = lexicAnalizer.getTokenInConstruction();
		//nextToken.addChar(c);
                nextToken.setTokenType("Asignacion");
                lexicAnalizer.saveCharacter(c);
	}
}
