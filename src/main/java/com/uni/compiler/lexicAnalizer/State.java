package com.uni.compiler.lexicAnalizer;

import java.util.HashMap;

import com.uni.compiler.Actions.Action;

public class State {
	private  Action actionToExecute;
	HashMap<Character, State> nextStates = new HashMap<Character, State>();
	
	public State (){
		
	}
	
	public State getNextState(Character c){
		return nextStates.get(c);
	}
	
	public Action getAction(){
		return actionToExecute;
	}
	
	public void addNextState(Character c,State state){
		nextStates.put(c, state);
	}
}
