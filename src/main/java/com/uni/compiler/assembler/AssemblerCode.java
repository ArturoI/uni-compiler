package com.uni.compiler.assembler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.uni.compiler.lexicAnalizer.Token;
import com.uni.compiler.parser.Terceto;

public class AssemblerCode {

	private ArrayList<Terceto> tercetoList;
	private ArrayList<Register> registerList;
	private Stack pila;
	private Queue<String> labelQueue;

	private ArrayList<Token> symbolTable;
	private int  contVariables =4;
	private int intMSG=0;
	


	private ArrayList<String> dataStructure=new ArrayList<String>();
	private ArrayList<String> codeStructure=new ArrayList<String>();
	
	public AssemblerCode(List<Terceto> TL, List<Token> ST) {
		tercetoList = (ArrayList<Terceto>) TL;
		pila = new Stack();
		labelQueue = new ArrayDeque<String>();
		loadRegister();

		symbolTable=(ArrayList<Token>) ST;



	}

	private void loadRegister() {
		registerList = new ArrayList<Register>();
		registerList.add(new Register("EAX"));
		registerList.add(new Register("EBX"));
		registerList.add(new Register("ECX"));
		registerList.add(new Register("EDX"));
	}

	public ArrayList<String> getAssemblerCode() {
		initCode();
		for (Terceto t : tercetoList) {
			
			generateCode(t);

		}
		for (String s:dataStructure){
			System.out.println(s);
		}
		
		codeStructure.add("fin:");
		for (String s:codeStructure){
			dataStructure.add(s);
			System.out.println(s);
		}
		
		return dataStructure;
		
		
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
		case 4://MOV EDX frula
			//String currentRegister = getRegister("MOV");
                        doMov(t);
			break;
		case 5://CMP
			CMPCode(t);
			break;
		case 6://PRINT
			doPrint(t);
			break;
		case 7://JE
			addToCodeStructure((String)t.getOperator() + " " + t.getFirstOperand() );
			break;
		case 8://
			addToCodeStructure((String)t.getOperator() + " " + t.getFirstOperand() );
			break;
		case 9://label
			addToCodeStructure((String)t.getOperator() + " " + t.getFirstOperand() );
			break;
		case 10://label
			addToCodeStructure((String)t.getOperator() +  " " + t.getFirstOperand() );
			break;
		case 11://label
			addToCodeStructure((String)t.getOperator() +  " " + t.getFirstOperand() );
			break;
		case 12://label
			addToCodeStructure((String)t.getOperator() +  " " + t.getFirstOperand() );
			break;
		case 13://label
			addToCodeStructure((String)t.getFirstOperand() + ": ");
			break;
		default:
			break;
		}
	}
	
	private void addToDataStructure(String t){
		dataStructure.add(t);
	}
	
	private void addToCodeStructure(String t){
		codeStructure.add(t);
	
	}
	
	   private void doPrint(Terceto t){
           //agrego el mensaje en el .data
           intMSG++;

           String s = (String) t.getFirstOperand();

           //meter en la estructura que tiene todo lo del .data, el bodoque de assembler para el print.

           addToDataStructure("msgSTR_"+intMSG+"   db '" + s + "',0Ah,0Dh,'$'");
           addToDataStructure("    MOV ah, 09h");
           addToDataStructure("    LEA dx, msgSTR_"+intMSG);
           addToDataStructure("    INT 21h");
           
       }
           
       private void doMov(Terceto t){
           String secOperand = "";
           if (t.getSecondOperand() instanceof Integer){
               Terceto aux = this.tercetoList.get(((Integer)t.getSecondOperand()).intValue() - 1);
               secOperand = ((Register)registerList.get(aux.getAssemblerResult())).getName();
               //secOperand = ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName();
               addToCodeStructure("MOV " + t.getFirstOperand() + " " + secOperand );
           } else {
               addToCodeStructure("MOV " + t.getFirstOperand() + " " + t.getSecondOperand());
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
			return 6; 
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
		if (op.equals("LABEL")){
			return 13; 
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
	
		if(!(t.getFirstOperand() instanceof Integer)&&!(t.getSecondOperand() instanceof Integer)){
			String currentRegister = getRegister("ADD");
			addToCodeStructure("MOV " + currentRegister + " " + t.getFirstOperand());
			addToCodeStructure("ADD " + currentRegister + " " + t.getSecondOperand());
			t.setAsseblerResult(registerMapping(currentRegister));
			registerList.get(registerMapping(currentRegister)).setBusy(true);
			
		}
		else if (t.getFirstOperand() instanceof Integer){
			Terceto aux1 = this.tercetoList.get(((Integer)t.getFirstOperand()).intValue() - 1);
			addToCodeStructure("ADD " + registerList.get(aux1.getAssemblerResult()).getName() + " " + t.getSecondOperand() );
			t.setAsseblerResult(registerMapping(registerList.get(aux1.getAssemblerResult()).getName()));
			registerList.get(registerMapping(registerList.get(aux1.getAssemblerResult()).getName())).setBusy(true);
		}
		
		else if (t.getSecondOperand() instanceof Integer){
			Terceto aux1 = this.tercetoList.get(((Integer)t.getSecondOperand()).intValue() - 1);
			addToCodeStructure("ADD " + registerList.get(aux1.getAssemblerResult()).getName() + " " + t.getFirstOperand() );
			t.setAsseblerResult(registerMapping(registerList.get(aux1.getAssemblerResult()).getName()));
			registerList.get(registerMapping(registerList.get(aux1.getAssemblerResult()).getName())).setBusy(true);
		}
	}
	
	
	
	public void mulCode(Terceto t){
		String currentRegister = getRegister("MUL");
		if(!(t.getFirstOperand() instanceof Terceto)&&!(t.getSecondOperand() instanceof Terceto)){
			addToCodeStructure("MOV " + currentRegister + " " + t.getFirstOperand());
			addToCodeStructure("MUL " + currentRegister + " " + t.getSecondOperand());
			t.setAsseblerResult(registerMapping(currentRegister));
			registerList.get(registerMapping(currentRegister)).setBusy(true);
		}
		else{
			 if((t.getFirstOperand() instanceof Terceto)&&(t.getSecondOperand() instanceof Terceto)){
					addToCodeStructure("MUL " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
							" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
					t.setAsseblerResult(registerMapping(currentRegister));
					registerList.get(registerMapping(currentRegister)).setBusy(true);

				}
			 else if (t.getFirstOperand() instanceof Terceto){
						addToCodeStructure("MUL" +  ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + " " +  t.getSecondOperand());
						t.setAsseblerResult(registerMapping(currentRegister));
						registerList.get(registerMapping(currentRegister)).setBusy(true);

			 }
			 	else if (t.getSecondOperand() instanceof Terceto){
				addToCodeStructure("MUL " + currentRegister +  " " + t.getFirstOperand());
				addToCodeStructure("ADD " + currentRegister + " " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
				t.setAsseblerResult(registerMapping(currentRegister));
				registerList.get(registerMapping(currentRegister)).setBusy(true);

			}
		}
	}
	
	public void divCode(Terceto t){
		String currentRegister = getRegister("DIV");
		addToCodeStructure("MOV" + currentRegister + t.getFirstOperand());
		addToCodeStructure("DIV" + currentRegister + t.getSecondOperand());	
		t.setAsseblerResult(Integer.parseInt(currentRegister));
	}
	
	public void subCode(Terceto t){
		String currentRegister = getRegister("SUB");
		addToCodeStructure("MOV" + currentRegister + t.getFirstOperand());
		addToCodeStructure("SUB" + currentRegister + t.getSecondOperand());	
		t.setAsseblerResult(Integer.parseInt(currentRegister));
	}
	
	
	public void CMPCode(Terceto t){
		String currentRegister = getRegister("CMP");
		if (!(t.getFirstOperand() instanceof Terceto) && !(t.getSecondOperand() instanceof Terceto)) {
			 addToCodeStructure("MOV " + currentRegister + t.getFirstOperand());
			 addToCodeStructure("CMP " + currentRegister + t.getSecondOperand());
			 t.setAsseblerResult(registerMapping(currentRegister));
			 registerList.get(registerMapping(currentRegister)).setBusy(true);
		} else {
			if ((t.getFirstOperand() instanceof Terceto) && (t.getSecondOperand() instanceof Terceto)) {
				//addToCodeStructure("MOV " + currentRegister + t.getFirstOperand());
				addToCodeStructure("CMP " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
								" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
			} else if (t.getFirstOperand() instanceof Terceto) {
				addToCodeStructure("MOV " + currentRegister + t.getFirstOperand());
				addToCodeStructure("CMP " + currentRegister + 
						" " + ((Register)registerList.get(((Terceto)t.getSecondOperand()).getAssemblerResult())).getName());
			} else if (t.getSecondOperand() instanceof Terceto) {
				addToCodeStructure("MOV " + currentRegister + t.getSecondOperand());
				addToCodeStructure("CMP " + ((Register)registerList.get(((Terceto)t.getFirstOperand()).getAssemblerResult())).getName() + 
						" " + currentRegister);
			}
		}

	}
	

	public String getRegister(String operation) {	
		if((operation.equals("MUL") || operation.equals("DIV"))){
			if (!registerList.get(1).isBusy()){
				return registerList.get(1).getName();
			}
			else if (!registerList.get(2).isBusy()){
				return registerList.get(2).getName();
			}
			else{
				return freeReg(1).getName();
			}
		}
		else{
			if (!registerList.get(0).isBusy()){
				return registerList.get(0).getName();
			}
			else if (!registerList.get(3).isBusy()){
				return registerList.get(3).getName();
			}
			else if (!registerList.get(1).isBusy()){
				return registerList.get(1).getName();
			}
			else if (!registerList.get(2).isBusy()){
				return registerList.get(2).getName();
			}
			else{
				return freeReg(3).getName();
			}

		}
	}

	public Register freeReg(int i){
		registerList.get(i).getTerceto().setAsseblerResult(contVariables);
		symbolTable.add(new Token("@vaux" + contVariables));
		addToCodeStructure("MOV " + "@vaux"+ contVariables + " "+ registerList.get(i).getName());
		contVariables++;
		registerList.get(i).setBusy(false);
		return registerList.get(i);
	}
	
	public void createVariable(Token t){
		//deberia ser en el .data
		addToDataStructure(t.getToken() + " dd 000000000");
	}
	
	public void initCode(){

        //genero encabezado del archivo
		addToDataStructure("; Codigo assembler generado a partir del c?digo intermedio\n\n");
        
		addToDataStructure(".model  small");
		addToDataStructure(".stack  100h");
		addToDataStructure(".data");
		addToDataStructure("");

        //creo las variables utilizadas (recorro la T.S)
		if (symbolTable!=null){
	        for(Token t :symbolTable){
	            createVariable(t);
	        }
		}

        //creo mensajes de error
        addToDataStructure("");
        addToDataStructure("msgZERO db 'FATAL ERROR: DIVISION BY ZERO ',0Ah,0Dh,'$'");
        addToDataStructure("msgOV db 'FATAL ERROR: MUL OVERFLOW ',0Ah,0Dh,'$'");
        addToDataStructure("");
        
        //this.addPrintVar(); //agrego las variables para los print


        /*
        this.archivo.add("mov ah, 09h");
        this.archivo.add("lea dx, msgERROR");
        this.archivo.add("int 21h");
        */


        addToCodeStructure("");
        addToCodeStructure(".code");
        addToCodeStructure(".586");
        addToCodeStructure(".587");
        addToCodeStructure("");
        addToCodeStructure("START: ");
        addToCodeStructure("MOV ax, @data");
        addToCodeStructure("MOV ds, ax");
        addToCodeStructure("");
        addToCodeStructure("jmp inicio");
        addToCodeStructure("");
        addToCodeStructure("; Imprime el error de overflow");
        addToCodeStructure("");
        addToCodeStructure("ErrOF PROC");
        addToCodeStructure("    mov ah,09h");
        addToCodeStructure("    lea dx,msgOV");
        addToCodeStructure("    int 21h");
        addToCodeStructure("    ret");
        addToCodeStructure("ErrOF ENDP");
        addToCodeStructure("");
        addToCodeStructure("; Imprime el error de division por cero");
        addToCodeStructure("");
        addToCodeStructure("ErrZERO PROC"); //nombre de la subrutina
        addToCodeStructure("    mov ah,09h");
        addToCodeStructure("    lea dx,msgZERO"); //mensaje que muesta la subrutina
        addToCodeStructure("    int 21h");
        addToCodeStructure("    ret");
        addToCodeStructure("ErrZERO ENDP"); //end de la subrutina

        addToCodeStructure("");
        addToCodeStructure("");
        addToCodeStructure("inicio: ");
	}
        
      
}
