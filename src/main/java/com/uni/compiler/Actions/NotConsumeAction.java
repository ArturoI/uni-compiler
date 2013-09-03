package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;

public class NotConsumeAction extends Action{
	private LexicAnalizer lexicAnalizer;
	
	public NotConsumeAction(LexicAnalizer la){
		lexicAnalizer=la;
	}

	@Override
	public void executeAction(Character c) {
		lexicAnalizer.saveCharacter(c);		
	}
	

}
