package com.uni.compiler.lexicAnalizer;

import java.io.IOException;
import java.util.HashMap;

public class LexicAnalizer {
	FileManager source;
	
	public LexicAnalizer(){
		
	}
	
	public Token getToken() throws IOException{
		Token nextToken= new Token();
		Character charac;
		State currentState = new State();//initialState
		State nextState=null;
		while ((charac = source.readChar())!=null){
			nextState = currentState.getNextState(charac);
			nextState.getAction().executeAction();
			currentState=nextState;
		}
		
		return nextToken;
		
	}
	
	
}
