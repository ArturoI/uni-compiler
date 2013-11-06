package com.uni.compiler.assembler;

import com.uni.compiler.parser.Terceto;

public class Register {
	
	private String name;
	private boolean isBusy;
	private Terceto t;
	
	public Register(String n){
		name=n;
		isBusy=false;
	}
	
	public void setTerceto(Terceto t1){
		t=t1;
	}
	
	public Terceto getTerceto(){
		return t;
	}

	public void setName (String n){
		name=n;
	}
	
	public void setBusy (boolean b){
		isBusy=b;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isBusy (){
		return isBusy;
	}
}
