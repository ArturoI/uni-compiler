package com.uni.compiler.assembler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import com.uni.compiler.parser.Terceto;

public class AssemblerCode {

	/*private ArrayList<Terceto> tercetoList;
	private ArrayList<Register> registerList;
	private Stack pila;
	private Queue<String> labelQueue;

	public AssemblerCode(ArrayList<Terceto> TL) {
		tercetoList = TL;
		pila = new Stack();
		labelQueue = new ArrayDeque<String>();
		loadRegister();

	}

	private void loadRegister() {
		registerList = new ArrayList<Register>();
		registerList.add(new Register("EAX"));
		registerList.add(new Register("EBX"));
		registerList.add(new Register("ECX"));
		registerList.add(new Register("EDX"));
	}

	public void getAssemblerCode(ArrayList<Terceto> tercetoList) {
		for (Terceto t : tercetoList) {

			if (!labelQueue.isEmpty()) {
				// Agregar label en el archivo.
			}

			generateCode(t);

		}
	}

	public void generateBeginCode() {

	}

	public void generateCode(Terceto t) {
		switch (t.getAssemblerCode()) {
		case 0://ADD
			addCode(t);
			break;
		// addCode(t);
		case 1://Mul
			break;
		// commuteOperation
		case 2: 
			break;
		// mulCode();
		case 3:
			break;
		// divCode();
		case 4:
			break;
		//Print
		case 5:
			break;
		//salto incondiconal
		case 6:
			break;
		//comparacion
		case 7:
			break;
		//generar label??
		case 8:
			break;
		//salto por falso
		default:
			break;
		}
	}
	
	public int registerMapping(String register){
		if (register.equals("EAX")){
			return 0;
		}
		else if (register.equals("EBX")){
			return 1;
		}
		else if  (register.equals("ECX")){
			return 2;
			
		}
		else if (register.equals("EDX")){
			return 3;
		}
		return -1;
	}

	public void addCode(Terceto t) {
		String currentRegister = getRegister("ADD");
		if(!(t.getFirstOperand() instanceof Terceto)&&!(t.getSecondOperand() instanceof Terceto)){
			System.out.println("MOV " + currentRegister + " " + t.getFirstOperand());
			System.out.println("ADD " + currentRegister + " " + t.getSecondOperand());
			t.setAsseblerResult(registerMapping(currentRegister));
			registerList.get(registerMapping(currentRegister)).setBusy(true);
			t.setAsseblerResult(registerMapping(currentRegister));
		}
		else{
			 if((t.getFirstOperand() instanceof Terceto)&&(t.getSecondOperand() instanceof Terceto)){
					System.out.println("ADD " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
							" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
					t.setAsseblerResult(registerMapping(currentRegister));
					registerList.get(registerMapping(currentRegister)).setBusy(true);
					t.setAsseblerResult(registerMapping(currentRegister));

				}
			 else if (t.getFirstOperand() instanceof Terceto){
						System.out.println("ADD " +  ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + " " +  t.getSecondOperand());
						t.setAsseblerResult(registerMapping(currentRegister));
						registerList.get(registerMapping(currentRegister)).setBusy(true);
						t.setAsseblerResult(registerMapping(currentRegister));

			 }
			 	else if (t.getSecondOperand() instanceof Terceto){
				System.out.println("MOV " + currentRegister +  " " + t.getFirstOperand());
				System.out.println("ADD " + currentRegister + " " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
				t.setAsseblerResult(registerMapping(currentRegister));
				registerList.get(registerMapping(currentRegister)).setBusy(true);
				t.setAsseblerResult(registerMapping(currentRegister));

			}
		}
	}
	
	
	
	public void mulCode(Terceto t){
		String currentRegister = getRegister("MUL");
		if(!(t.getFirstOperand() instanceof Terceto)&&!(t.getSecondOperand() instanceof Terceto)){
			System.out.println("MOV " + currentRegister + " " + t.getFirstOperand());
			System.out.println("MUL " + currentRegister + " " + t.getSecondOperand());
			t.setAsseblerResult(registerMapping(currentRegister));
			registerList.get(registerMapping(currentRegister)).setBusy(true);
			t.setAsseblerResult(registerMapping(currentRegister));
		}
		else{
			 if((t.getFirstOperand() instanceof Terceto)&&(t.getSecondOperand() instanceof Terceto)){
					System.out.println("MUL " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
							" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
					t.setAsseblerResult(registerMapping(currentRegister));
					registerList.get(registerMapping(currentRegister)).setBusy(true);
					t.setAsseblerResult(registerMapping(currentRegister));

				}
			 else if (t.getFirstOperand() instanceof Terceto){
						System.out.println("MUL" +  ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + " " +  t.getSecondOperand());
						t.setAsseblerResult(registerMapping(currentRegister));
						registerList.get(registerMapping(currentRegister)).setBusy(true);
						t.setAsseblerResult(registerMapping(currentRegister));

			 }
			 	else if (t.getSecondOperand() instanceof Terceto){
				System.out.println("MUL " + currentRegister +  " " + t.getFirstOperand());
				System.out.println("ADD " + currentRegister + " " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
				t.setAsseblerResult(registerMapping(currentRegister));
				registerList.get(registerMapping(currentRegister)).setBusy(true);
				t.setAsseblerResult(registerMapping(currentRegister));

			}
		}
	}
	
	public void divCode(Terceto t){
		String currentRegister = getRegister("DIV");
		System.out.println("MOV" + currentRegister + t.getFirstOperand());
		System.out.println("DIV" + currentRegister + t.getSecondOperand());	
		t.setAsseblerResult(Integer.parseInt(currentRegister));
	}
	
	public void subCode(Terceto t){
		String currentRegister = getRegister("SUB");
		System.out.println("MOV" + currentRegister + t.getFirstOperand());
		System.out.println("SUB" + currentRegister + t.getSecondOperand());	
		t.setAsseblerResult(Integer.parseInt(currentRegister));
	}
	
	public void print(Terceto t){
		System.out.println("PRINT " + t.getFirstOperand());
	}
	
	public void doCMP(Terceto t){
		//if(t.getFirstOperand().isTerceto){
			//Register r  = getFreeRegister();
			//System.out.println("MOV " + r + t.getFirstOperand());
			//System.out.println("CMP " + r + t.getSecondOperand());
		//}
		//else{
		
		//}
			
		
	}


	public String getRegister(String operation) {	
		for (Register r:registerList){
			if (!r.isBusy()){
				return r.getName();
			}
		}
		return freeReg(1).getName();
	}

	public Register freeReg(int i){
		return new Register(null);
	}
        * 
        * */
    
}
