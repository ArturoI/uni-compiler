package com.uni.compiler.lexicAnalizer;

import java.util.HashMap;

import com.uni.compiler.Actions.Action;

public class State {
	private String name;
	HashMap<Integer, Cell> nextStates = new HashMap<Integer, Cell>();

	public State(String stateName) {
		name = stateName;
	}

	public State getNextState(Integer c) {
		return nextStates.get(c).getState();
	}

	public Action getAction(Integer c) {
		return nextStates.get(c).getActionToExecute();
	}

	public void addNextState(Integer c, Cell nState) {
		nextStates.put(c, nState);
	}

	public boolean isFinal() {
		return this.name.equals("EndState");
	}
}
