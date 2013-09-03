package com.uni.compiler.lexicAnalizer;

import com.uni.compiler.Actions.Action;

public class Cell {
	private State nextState;
	private Action actionToExecute;
	
	public Cell (State nxS, Action aTE){
		nextState =nxS;
		actionToExecute=aTE;
	}
	
	public State getState(){
		return nextState;
	}
	
	public Action getActionToExecute(){
		return actionToExecute;
	}
	
	public void setState(State s){
		nextState=s;
	}
	
	public void setActionToExecute(Action a){
		actionToExecute=a;
	}
	
}
