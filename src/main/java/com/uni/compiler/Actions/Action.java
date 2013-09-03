package com.uni.compiler.Actions;

import com.uni.compiler.lexicAnalizer.LexicAnalizer;
import com.uni.compiler.lexicAnalizer.Token;

public abstract class Action {
	
	protected Token nextToken;
	protected LexicAnalizer lexicAnalizer;
	
	public abstract void executeAction(Character c);
}
