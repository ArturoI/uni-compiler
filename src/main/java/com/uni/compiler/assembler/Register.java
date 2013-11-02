package com.uni.compiler.assembler;

public class Register {
	
	private String name;
	private boolean isBusy;
	
	public Register(String n){
		name=n;
		isBusy=false;
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
