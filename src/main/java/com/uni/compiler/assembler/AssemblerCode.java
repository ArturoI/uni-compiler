package com.uni.compiler.assembler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import com.uni.compiler.parser.Terceto;

public class AssemblerCode {

	private ArrayList<Terceto> tercetoList;
	private ArrayList<Register> registerList;
	private Stack pila;
	private Queue<String> labelQueue;
        private int intMSG;

	public AssemblerCode(ArrayList<Terceto> TL) {
		tercetoList = TL;
		pila = new Stack();
		labelQueue = new ArrayDeque<String>();
		loadRegister();
                intMSG = 1;

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
		switch (operationMapping((String)t.getOperator())) {
		case 0://ADD
			addCode(t);
			break;
		case 1://sub
			subCode(t);
			break;
		case 2://div
			divCode(t);
			break;
		case 3://mul
			mulCode(t);
			break;
		case 4://MOV
			String currentRegister =getRegister("MOV");
			System.out.println("MOV " + currentRegister + " "+ t.getFirstOperand() );
			break;
		case 5://CMP
			CMPCode(t);
			break;
		case 6://PRINT
			break;
		case 7://JE
			System.out.println((String)t.getOperator() + " " + t.getFirstOperand() );
			break;
		case 8://
			System.out.println((String)t.getOperator() + " " + t.getFirstOperand() );
			break;
		case 9://label
			System.out.println((String)t.getOperator() + " " + t.getFirstOperand() );
			break;
		case 10://label
			System.out.println((String)t.getOperator() +  " " + t.getFirstOperand() );
			break;
		case 11://label
			System.out.println((String)t.getOperator() +  " " + t.getFirstOperand() );
			break;
		case 12://label
			System.out.println((String)t.getOperator() +  " " + t.getFirstOperand() );
			break;
		case 13://label
			System.out.println((String)t.getFirstOperand() + ": ");
			break;
		default:
			break;
		}
	}
	
	//Mapping Codigo Assembler
	
	private int operationMapping(String op){
		if (op.equals("ADD")){
			return 0;
		}
		if (op.equals("SUB")){
			return 1;
		}
		if (op.equals("DIV")){
			return 2;
		}
		if (op.equals("MUL")){
			return 3;
		}
		if (op.equals("MOV")){
			return 4;
		}
		if (op.equals("CMP")){
			return 5;
		}
		if (op.equals("PRINT")){
			return 6; //TODO
		}

		if (op.equals("JAE")){
			return 7;
		}
		if (op.equals("JA")){
			return 8;
		}
		if (op.equals("JB")){
			return 9;
		}
		if (op.equals("JBE")){
			return 10;
		}
		if (op.equals("JNE")){
			return 11;
		}
		if (op.equals("JE")){
			return 12;
		}
		if (op.equals("LABEl")){
			return 13; //TODO
		}
		
		
		
		return -1;
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
		}
		else{
			 if((t.getFirstOperand() instanceof Terceto)&&(t.getSecondOperand() instanceof Terceto)){
					System.out.println("ADD " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
							" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
					t.setAsseblerResult(registerMapping(currentRegister));
					registerList.get(registerMapping(currentRegister)).setBusy(true);

				}
			 else if (t.getFirstOperand() instanceof Terceto){
						System.out.println("ADD " +  ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + " " +  t.getSecondOperand());
						t.setAsseblerResult(registerMapping(currentRegister));
						registerList.get(registerMapping(currentRegister)).setBusy(true);

			 }
			 	else if (t.getSecondOperand() instanceof Terceto){
				System.out.println("MOV " + currentRegister +  " " + t.getFirstOperand());
				System.out.println("ADD " + currentRegister + " " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
				t.setAsseblerResult(registerMapping(currentRegister));
				registerList.get(registerMapping(currentRegister)).setBusy(true);

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
		}
		else{
			 if((t.getFirstOperand() instanceof Terceto)&&(t.getSecondOperand() instanceof Terceto)){
					System.out.println("MUL " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
							" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
					t.setAsseblerResult(registerMapping(currentRegister));
					registerList.get(registerMapping(currentRegister)).setBusy(true);

				}
			 else if (t.getFirstOperand() instanceof Terceto){
						System.out.println("MUL" +  ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + " " +  t.getSecondOperand());
						t.setAsseblerResult(registerMapping(currentRegister));
						registerList.get(registerMapping(currentRegister)).setBusy(true);

			 }
			 	else if (t.getSecondOperand() instanceof Terceto){
				System.out.println("MUL " + currentRegister +  " " + t.getFirstOperand());
				System.out.println("ADD " + currentRegister + " " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
				t.setAsseblerResult(registerMapping(currentRegister));
				registerList.get(registerMapping(currentRegister)).setBusy(true);

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
	
	public void CMPCode(Terceto t){
		String currentRegister = getRegister("CMP");
		if (!(t.getFirstOperand() instanceof Terceto) && !(t.getSecondOperand() instanceof Terceto)) {
			 System.out.println("MOV " + currentRegister + t.getFirstOperand());
			 System.out.println("CMP " + currentRegister + t.getSecondOperand());
			 t.setAsseblerResult(registerMapping(currentRegister));
			 registerList.get(registerMapping(currentRegister)).setBusy(true);
		} else {
			if ((t.getFirstOperand() instanceof Terceto) && (t.getSecondOperand() instanceof Terceto)) {
				//System.out.println("MOV " + currentRegister + t.getFirstOperand());
				System.out.println("CMP " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
								" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
			} else if (t.getFirstOperand() instanceof Terceto) {
				System.out.println("MOV " + currentRegister + t.getFirstOperand());
				System.out.println("CMP " + currentRegister + 
						" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
			} else if (t.getSecondOperand() instanceof Terceto) {
				System.out.println("MOV " + currentRegister + t.getSecondOperand());
				System.out.println("CMP " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
						" " + currentRegister);
			}
		}

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
        
        private void doPrint(Terceto t){
            //agrego el mensaje en el .data
            intMSG++;

            String s = (String) t.getFirstOperand();

            //meter en la estructura que tiene todo lo del .data, el bodoque de assembler para el print.

            /*
            this.printMSG.add("msgSTR_"+intMSG+"   db '" + s + "',0Ah,0Dh,'$'");
            
            //llamo al mensaje con la variable q acabo de crear
            this.archivo.add("    MOV ah, 09h");
            this.archivo.add("    LEA dx, msgSTR_"+intMSG);
            this.archivo.add("    INT 21h");
           
           crear una logica que construya lo del .data por una parte, el codigo assembler posta por otra,
           y luego mergear todo a gusto y piacere en un archivito .asm
           
        */
        }
}
