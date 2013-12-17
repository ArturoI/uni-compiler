package com.uni.compiler.assembler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.uni.compiler.lexicAnalizer.Token;
import com.uni.compiler.parser.Terceto;
import java.io.FileNotFoundException;

public class AssemblerCode {

    private ArrayList<Terceto> tercetoList;
    private ArrayList<Register> registerList;
    private Stack pila;
    private Queue<String> labelQueue;
    private ArrayList<Token> symbolTable;
    private int contVariables = 4;
    private int intMSG = 0;
    private int intSeguimiento = 0;
    private ArrayList<String> dataStructure = new ArrayList<String>();
    private ArrayList<String> codeStructure = new ArrayList<String>();
    private ArrayList<String> variableStructure = new ArrayList<String>();
    private Escribe ass_cod;

    public AssemblerCode(List<Terceto> TL, List<Token> ST) throws FileNotFoundException {
        tercetoList = (ArrayList<Terceto>) TL;
        pila = new Stack();
        labelQueue = new ArrayDeque<String>();
        loadRegister();

        symbolTable = (ArrayList<Token>) ST;

        this.ass_cod = new Escribe("Grupo-1-ass_code.asm");

    }

    private void loadRegister() {
        registerList = new ArrayList<Register>();
        registerList.add(new Register("EAX"));
        registerList.add(new Register("EBX"));
        registerList.add(new Register("ECX"));
        registerList.add(new Register("EDX"));
    }

    public ArrayList<String> getAssemblerCode() {
        addFunctionsCode();
        for (Terceto t : tercetoList) {
            generateCode(t);
        }
        /*for (String s : dataStructure){
         System.out.println(s);
         }*/

        initCode();
        addFunctionsDeclarations();
        addVariables();
        addToCodeStructure("fin:");
        addToCodeStructure("    invoke  ExitProcess,0");
        addToCodeStructure("END START");

        for (String s : codeStructure) {
            dataStructure.add(s);
            System.out.println(s);
        }
        // Crea el archivo .asm
        for (String s : dataStructure) {
            this.ass_cod.escln(s);
        }
        this.ass_cod.cierra();
        return dataStructure;
    }

    private void generateCode(Terceto t) {
        switch (operationMapping((String) t.getOperator())) {
            case 0://ADD
                codeForOpeation(t, "ADD");
                break;
            case 1://SUB
                codeForOpeation(t, "SUB");
                break;
            case 2://DIV
                divCode(t);
                break;
            case 3://MUL
                mulCode(t);
                break;
            case 4://MOV
                doMov(t);
                break;
            case 5://CMP
                CMPCode(t);
                break;
            case 6://PRINT
                doPrint(t);
                break;
            case 7://JE
                addToCodeStructure("    " + (String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 8://
                addToCodeStructure((String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 9://label
                addToCodeStructure((String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 10://label
                addToCodeStructure((String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 11://label
                addToCodeStructure((String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 12://label
                addToCodeStructure((String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 13://label
                addToCodeStructure(((Token) t.getFirstOperand()).getToken() + ": ");
                break;
            case 14://label
                addToCodeStructure((String) t.getOperator() + " " + t.getFirstOperand());
                break;
            case 15://labelf
                addToCodeStructure(t.getFirstOperand() + ": ");
                break;
            case 16://ret
                addToCodeStructure("    " + (String) t.getOperator());
                break;
            case 17://call
                addToCodeStructure("    " + (String) t.getOperator() + " " + t.getFirstOperand());
                break;
            default:
                break;
        }
    }

    private void addToDataStructure(String t) {
        dataStructure.add(t);
    }

    private void addToCodeStructure(String t) {
        codeStructure.add(t);
    }

    private void addToVariableStructure(String t) {
        variableStructure.add(t);
    }

    private void doPrint(Terceto t) {
        //agrego el mensaje en el .data
        intMSG++;

        String s = ((Token) t.getFirstOperand()).getToken();

        //meter en la estructura que tiene todo lo del .data, el bodoque de assembler para el print.

        addToVariableStructure(" msgSTR_" + intMSG + "   db '" + s + "',0");
        addToCodeStructure("    invoke  MessageBox, 0, ADDR msgSTR_" + intMSG +", ADDR MyBoxTitle, MB_OK");
        //addToCodeStructure("    invoke  ExitProcess,0");
    }

    private void doMov(Terceto t) {
        if (t.getSecondOperand() instanceof Integer) {
            String secondOperand = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1).getVariable();
            addToCodeStructure("    MOV " + t.getFirstOperand() + ", " + secondOperand);
        } else {
            addToCodeStructure("    MOV " + t.getFirstOperand() + ", " + t.getSecondOperand());
        }
    }
//Mapping Codigo Assembler

    private int operationMapping(String op) {
        if (op.equals("ADD")) {
            return 0;
        }
        if (op.equals("SUB")) {
            return 1;
        }
        if (op.equals("DIV")) {
            return 2;
        }
        if (op.equals("MUL")) {
            return 3;
        }
        if (op.equals("MOV")) {
            return 4;
        }
        if (op.equals("CMP")) {
            return 5;
        }
        if (op.equals("PRINT")) {
            return 6;
        }
        if (op.equals("JAE")) {
            return 7;
        }
        if (op.equals("JA")) {
            return 8;
        }
        if (op.equals("JB")) {
            return 9;
        }
        if (op.equals("JBE")) {
            return 10;
        }
        if (op.equals("JNE")) {
            return 11;
        }
        if (op.equals("JE")) {
            return 12;
        }
        if (op.equals("LABEL")) {
            return 13;
        }
        if (op.equals("JMP")) {
            return 14;
        }
        if (op.equals("LABELF")) {
            return 15;
        }
        if (op.equals("RET")) {
            return 16;
        }
        if (op.equals("CALL")) {
            return 17;
        }
        return -1;
    }

    public int registerMapping(String register) {
        if (register.equals("EAX")) {
            return 0;
        }
        if (register.equals("EBX")) {
            return 1;
        }
        if (register.equals("ECX")) {
            return 2;
        }
        if (register.equals("EDX")) {
            return 3;
        }
        return -1;
    }

    //esta en un registro, o en una variable assembler
    private boolean isInRegister(Terceto t) {
        int registro = t.getAssemblerResult().intValue();
        return (registro <= 3 && registro >= 0);
    }

    public void codeForOpeation(Terceto t, String op) {
        //Directo Directo (2 + 3)
        String registroResultado = "";
        if (!(t.getFirstOperand() instanceof Integer) && !(t.getSecondOperand() instanceof Integer)) {
            String currentRegister = getRegister();
            addToCodeStructure("    MOV " + currentRegister + ", " + t.getFirstOperand());
            addToCodeStructure("    " + op + " " + currentRegister + ", " + t.getSecondOperand());
            t.setAsseblerResult(registerMapping(currentRegister));
            registerList.get(registerMapping(currentRegister)).setBusy(true);
            registroResultado = currentRegister;
        } //terceto terceto 
        else if ((t.getFirstOperand() instanceof Integer) && (t.getSecondOperand() instanceof Integer)) {
            Terceto aux1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);
            Terceto aux2 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);

            if (this.isInRegister(aux1)) {
                addToCodeStructure("    " + op + " " + aux1.getVariable() + ", " + aux2.getVariable());
                t.setAsseblerResult(aux1.getAssemblerResult());
                //ya esta en un registro, no hay que setearlo como busy
                registroResultado = aux1.getVariable();
            } else {
                String currentRegister = getRegister();
                addToCodeStructure("    MOV " + currentRegister + ", " + aux1.getVariable());
                addToCodeStructure("    " + op + " " + currentRegister + ", " + aux2.getVariable());
                t.setAsseblerResult(registerMapping(currentRegister));
                registerList.get(registerMapping(currentRegister)).setBusy(true);
                registroResultado = currentRegister;
            }
        } //Terceto - Directo
        else if (t.getFirstOperand() instanceof Integer) {
            Terceto aux1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);

            if (this.isInRegister(aux1)) {
                addToCodeStructure("    " + op + " " + aux1.getVariable() + ", " + t.getSecondOperand());
                t.setAsseblerResult(aux1.getAssemblerResult());
                registerList.get(aux1.getAssemblerResult()).setBusy(true);
                registroResultado = aux1.getVariable();
            } else { // esta en una variable en lugar de un registro.
                String currentRegister = getRegister();
                addToCodeStructure("    MOV " + currentRegister + ", " + aux1.getVariable());
                addToCodeStructure("    " + op + " " + currentRegister + ", " + t.getSecondOperand());
                t.setAsseblerResult(registerMapping(currentRegister));
                registerList.get(registerMapping(currentRegister)).setBusy(true);
                registroResultado = currentRegister;
            }
        } //Directo - Terceto
        else if (t.getSecondOperand() instanceof Integer) {
            Terceto aux2 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);
            String currentRegister = getRegister();
            addToCodeStructure("    MOV " + currentRegister + ", " + t.getFirstOperand());
            addToCodeStructure("    " + op + " " + currentRegister + ", " + aux2.getVariable());
            t.setAsseblerResult(registerMapping(currentRegister));
            registerList.get(registerMapping(currentRegister)).setBusy(true);
            registroResultado = currentRegister;
        }
        
        if (op.equals("ADD")){
            addToCodeStructure("    CMP " + registroResultado + ", 0");
            addToCodeStructure("    JNE OFLABEL_" + this.intSeguimiento);
            addToCodeStructure("    call ErrOF");
            addToCodeStructure("    JMP fin");
            addToCodeStructure("OFLABEL_" + this.intSeguimiento + ": ");
            this.intSeguimiento++;
        }
    }

    private void addOverflowCode(String result) {
        addToCodeStructure("    CMP EDX, 65535");
        addToCodeStructure("    JBE OFLABEL_" + this.intSeguimiento);
        addToCodeStructure("    call ErrOF");
        addToCodeStructure("    JMP fin");
        addToCodeStructure("OFLABEL_" + this.intSeguimiento + ": ");
        this.intSeguimiento++;
    }

    public void mulCode(Terceto t) {
        String result = "";
        if (!(t.getFirstOperand() instanceof Integer) && !(t.getSecondOperand() instanceof Integer)) { //directo directo (2 * 3)

            if (this.registerList.get(0).isBusy()) {
                this.freeReg(0);
            }
            if (this.registerList.get(3).isBusy()) {
                this.freeReg(3);
            }

            addToCodeStructure("    MOV EDX, 0");//pongo en 0 el reg EDX (parte alta del resultado)
            addToCodeStructure("    MOV EAX, " + t.getSecondOperand()); //cargo en EAX el segundo operando
            this.registerList.get(0).setBusy(true);
            Register r = this.registerList.get(registerMapping(this.getRegister()));
            addToCodeStructure("    MOV " + r.getName() + ", " + t.getFirstOperand()); //cargo el primer operando
            addToCodeStructure("    MUL " + r.getName());

            t.setAsseblerResult(0); //El resultado esta en EAX
            registerList.get(0).setBusy(true); //seteo EAX en uso
            registerList.get(0).setTerceto(t);
            result = "EAX";

        } //Terceto Terceto (EAX * _@vaux8)
        else if ((t.getFirstOperand() instanceof Integer) && (t.getSecondOperand() instanceof Integer)) {

            Terceto aux1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);
            Terceto aux2 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);

            if (this.registerList.get(0).isBusy()) {
                this.freeReg(0);
            }
            if (this.registerList.get(3).isBusy()) {
                this.freeReg(3);
            }

            addToCodeStructure("    MOV EDX, 0");//pongo en 0 el reg EDX (parte alta del resultado)
            addToCodeStructure("    MOV EAX, " + aux2.getVariable()); //cargo en EAX el segundo operando
            Register r = this.registerList.get(registerMapping(this.getRegister()));
            addToCodeStructure("    MOV " + r.getName() + ", " + aux1.getVariable()); //cargo el primer operando
            addToCodeStructure("    MUL " + r.getName());

            t.setAsseblerResult(0); //El resultado esta en EAX
            registerList.get(0).setBusy(true); //seteo EAX en uso
            result = "EAX";

        } //Terceto Directo (EAX * 2)
        else if (t.getFirstOperand() instanceof Integer) {

            Terceto aux1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);

            if (!((Register) t.getSecondOperand()).getName().equals("EAX")) {
                if (this.registerList.get(0).isBusy()) {
                    this.freeReg(0);
                } //libero EAX
                addToCodeStructure("    MOV EAX," + t.getSecondOperand()); //muevo el primer registro a EAX
                registerList.get(0).setBusy(true); //seteo EAX como usado.
            }

            if (this.registerList.get(3).isBusy()) {
                this.freeReg(3);
            } //libero EDX
            addToCodeStructure("    MOV EDX, 0");//pongo en 0 el reg EDX (parte alta del resultado)

            Register r = this.registerList.get(registerMapping(this.getRegister()));
            addToCodeStructure("    MOV " + r.getName() + ", " + aux1.getVariable()); //cargo el primer operando
            addToCodeStructure("    MUL " + r.getName());

            t.setAsseblerResult(0); //El resultado esta en EAX
            registerList.get(0).setBusy(true); //seteo EAX en uso
            result = "EAX";

        } else if (t.getSecondOperand() instanceof Integer) { //reg var
            Terceto aux1 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);

            if (!((Register) t.getFirstOperand()).getName().equals("EAX")) {
                if (this.registerList.get(0).isBusy()) {
                    this.freeReg(0);
                } //libero EAX
                addToCodeStructure("    MOV EAX," + t.getFirstOperand()); //muevo el primer registro a EAX
                registerList.get(0).setBusy(true); //seteo EAX como usado.
            }

            if (this.registerList.get(3).isBusy()) {
                this.freeReg(3);
            } //libero EDX
            addToCodeStructure("    MOV EDX, 0");//pongo en 0 el reg EDX (parte alta del resultado)

            Register r = this.registerList.get(registerMapping(this.getRegister()));
            addToCodeStructure("    MOV " + r.getName() + ", " + aux1.getVariable()); //cargo el primer operando
            addToCodeStructure("    MUL " + r.getName());

            t.setAsseblerResult(0); //El resultado esta en EAX
            registerList.get(0).setBusy(true); //seteo EAX en uso
            result = "EAX";

        }
        //result is always on EAX                
        addOverflowCode(result);
    }

    private void divTerTer(Terceto t1, Terceto t2) {
        Register r = new Register(null);
        //reg reg
        if (isInRegister(t1) && isInRegister(t2)) {
            if (!t1.getVariable().equals("EAX")) {
                if (t2.getVariable().equals("EAX")) {
                    r = this.registerList.get(registerMapping(this.getRegister(t1.getVariable()))); //busco un registro libre que no sea el usado por t1
                    addToCodeStructure("    MOV " + r.getName() + ", " + t2.getVariable());
                } else {
                    if (this.registerList.get(0).isBusy()) {
                        this.freeReg(0);
                    }
                    this.registerList.get(0).setBusy(true);
                    if (t2.getVariable().equals("EDX")) {
                        r = this.registerList.get(registerMapping(this.getRegister(t1.getVariable()))); //busco un registro libre que no sea el usado por t1
                        addToCodeStructure("    MOV " + r.getName() + ", " + t2.getVariable());
                        //setear el 1 desocupado
                        this.registerList.get(t2.getAssemblerResult().intValue()).setBusy(false);
                    } else {
                        this.freeReg(3);
                        r = this.registerList.get(registerMapping(this.getRegister(t2.getVariable())));
                    }
                    addToCodeStructure("    MOV EAX, " + t1.getVariable()); //muevo el t1 al EAX
                    this.registerList.get(t1.getAssemblerResult()).setBusy(false);
                    this.freeReg(3);
                }
            } else {//si t1 es EAX t2 puede ser EDX
                if (t2.getVariable().equals("EDX")) {
                    r = this.registerList.get(registerMapping(this.getRegister(t1.getVariable())));//busco un registro libre que no sea el usado por t1
                    addToCodeStructure("    MOV " + r.getName() + ", " + t2.getVariable());
                    this.registerList.get(t2.getAssemblerResult().intValue()).setBusy(false);
                } else {
                    this.freeReg(3);
                    r = this.registerList.get(registerMapping(this.getRegister(t2.getVariable())));
                }
            }
            addToCodeStructure("    DIV " + r.getName());
            r.setBusy(false);
            //var var
        } else if (!isInRegister(t1) && !isInRegister(t2)) {
            divDirDir(t1.getVariable(), t2.getVariable());
            //reg var
        } else if (isInRegister(t1)) {
            divTerDir(t1, t2.getVariable());
            //var reg
        } else if (isInRegister(t2)) {
            divDirTer(t1.getVariable(), t2);
        }
    }

    private void divTerDir(Terceto t1, Object o2) {
        if (!t1.getVariable().equals("EAX")) {
            this.freeReg(0);//EAX
            addToCodeStructure("    MOV EAX, " + t1.getVariable()); //MOV EAX, reg
            this.registerList.get(0).setBusy(false);
            this.registerList.get(t1.getAssemblerResult()).setBusy(false);
        }
        this.freeReg(3);//libero EDX para no pisarla con el resto de la div
        this.registerList.get(3).setBusy(false);//reservo el EDX
        // paso a memoria el o1 por si es directa. (puede ser una variable ass y no afectaria en nada)
        String registro = this.getRegister();
        addToCodeStructure("    MOV " + registro + ", " + o2);
        addToCodeStructure("    DIV " + registro);

        this.registerList.get(3).setBusy(false);//libero donde obtuve el resto de la div
    }

    private void divDirTer(Object o1, Terceto t2) {
        Register r = this.registerList.get(registerMapping(this.getRegister(t2.getVariable())));
        if (r.getName().equals("EAX") || r.getName().equals("EDX")) {
            this.freeReg(3); //para no pisarlo con el resto de la div
            this.registerList.get(3).setBusy(false);
            r = this.registerList.get(registerMapping(this.getRegister()));
            addToCodeStructure("    MOV " + r.getName() + ", " + t2.getVariable());
            this.registerList.get(t2.getAssemblerResult()).setBusy(false);
        } else {
            this.freeReg(3); //para no pisarlo con el resto de la div
            this.registerList.get(3).setBusy(false);
        }

        this.freeReg(0);
        this.registerList.get(0).setBusy(false);

        addToCodeStructure("    MOV EAX, " + o1);
        addToCodeStructure("    DIV " + r.getName());
        r.setBusy(false);
        this.registerList.get(3).setBusy(false);
    }

    private void divDirDir(Object o1, Object o2) {
        if (this.registerList.get(0).isBusy()) {
            this.freeReg(0);
        } //libero EAX y la cargo con el o2
        addToCodeStructure("    MOV EAX, " + o1);
        this.registerList.get(0).setBusy(true);

        if (this.registerList.get(3).isBusy()) {
            this.freeReg(3);
        }//para no pisar el resto de la div
        this.registerList.get(3).setBusy(true);

        if (!o2.toString().contains("_")) {
            String r = this.getRegister();
            addToCodeStructure("    MOV " + r + ", " + o2);
            addToCodeStructure("    DIV " + r);
        } else {
            addToCodeStructure("    DIV " + o2);
        }
        this.registerList.get(3).setBusy(false);//libero donde obtuve el resto de la div
    }

    private void divCode(Terceto t) {
        if (t.getSecondOperand() instanceof Integer) {
            Terceto t2 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);
            //Begin Checkeo Div by Zero
            if (isInRegister(t2) || t2.getVariable().contains("_")) {
                addToCodeStructure("    CMP " + t2.getVariable() + ", 0");
            } else {
                String registro = getRegister();
                addToCodeStructure("    MOV " + registro + ", " + t2.getVariable());
                addToCodeStructure("    CMP " + registro + ", 0");
            }
            addToCodeStructure("    JNE OFLABEL_" + intSeguimiento);
            addToCodeStructure("    call ErrZERO");
            addToCodeStructure("    JMP fin");
            addToCodeStructure("OFLABEL_" + intSeguimiento + ": ");
            this.intSeguimiento++;
            //End Checkeo Div by Zero
            if (t.getFirstOperand() instanceof Integer) { //Terceto Terceto
                Terceto t1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);
                divTerTer(t1, t2);
            } else { //Directo Terceto
                divDirTer(t.getFirstOperand(), t2);
            }
        } else { //2nd = Directo
            //Begin Checkeo Div by Zero
            if (!t.getSecondOperand().toString().contains("_")) {
                String registro = getRegister();
                addToCodeStructure("    MOV " + registro + ", " + t.getSecondOperand());
                addToCodeStructure("    CMP " + registro + ", 0");
            } else {
                addToCodeStructure("    CMP " + t.getSecondOperand() + ", 0");
            }
            addToCodeStructure("    JNE OFLABEL_" + intSeguimiento);
            addToCodeStructure("    call ErrZERO");
            addToCodeStructure("    JMP fin");
            addToCodeStructure("OFLABEL_" + intSeguimiento + ": ");
            this.intSeguimiento++;
            //End Checkeo Div by Zero
            if (t.getFirstOperand() instanceof Integer) { //Terceto Directo
                Terceto t1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);
                divTerDir(t1, t.getSecondOperand());
            } else { //Directo Directo
                divDirDir(t.getFirstOperand(), t.getSecondOperand());
            }
        }
        t.setAsseblerResult(0);
        this.registerList.get(0).setTerceto(t);
        this.registerList.get(0).setBusy(true);
    }

    private void CMPCode(Terceto t) {
        if (!(t.getFirstOperand() instanceof Integer) && !(t.getSecondOperand() instanceof Integer)) {
            String currentRegister = getRegister();
            addToCodeStructure("    MOV " + currentRegister + ", " + t.getFirstOperand());
            addToCodeStructure("    CMP " + currentRegister + ", " + t.getSecondOperand());
            t.setAsseblerResult(registerMapping(currentRegister));
            registerList.get(registerMapping(currentRegister)).setBusy(true);

        } else if ((t.getFirstOperand() instanceof Integer) && (t.getSecondOperand() instanceof Integer)) {
            Terceto aux1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);
            Terceto aux2 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);
            String currentRegister = getRegister();
            if (!isInRegister(aux1) && !isInRegister(aux2)) { //Variable Variable
                addToCodeStructure("    MOV " + currentRegister + ", " + aux1.getVariable());
                addToCodeStructure("    CMP " + currentRegister + ", " + aux2.getVariable());
                t.setAsseblerResult(this.registerMapping(currentRegister));
                registerList.get(this.registerMapping(currentRegister)).setBusy(true);
            } else if ((isInRegister(aux1) && isInRegister(aux2)) || (isInRegister(aux1) && !isInRegister(aux2))) { //Registro Registro o Registro Variable
                addToCodeStructure("    CMP " + aux1.getVariable() + ", " + aux2.getVariable());
                t.setAsseblerResult(aux1.getAssemblerResult());
                registerList.get(aux2.getAssemblerResult()).setBusy(false);
            } else if (!isInRegister(aux1) && isInRegister(aux2)) { //variable Registro
                addToCodeStructure("    MOV " + currentRegister + ", " + aux1.getVariable());
                addToCodeStructure("    CMP " + currentRegister + ", " + aux2.getVariable());
                t.setAsseblerResult(this.registerMapping(currentRegister));
                registerList.get(this.registerMapping(currentRegister)).setBusy(true);
                //registerList.get(aux2.getAssemblerResult()).setBusy(false);
            }
        } else if (t.getFirstOperand() instanceof Integer) { //Terceto Directo
            Terceto aux1 = this.tercetoList.get(((Integer) t.getFirstOperand()).intValue() - 1);
            if (!isInRegister(aux1)) {//Terceto = Variable
                String currentRegister = getRegister();
                addToCodeStructure("    MOV " + currentRegister + ", " + aux1.getVariable());
                addToCodeStructure("    CMP " + currentRegister + ", " + t.getSecondOperand());
                t.setAsseblerResult(this.registerMapping(currentRegister));
                registerList.get(this.registerMapping(currentRegister)).setBusy(true);
            } else { //Terceto = registro
                addToCodeStructure("    CMP " + aux1.getVariable() + ", " + t.getSecondOperand());
                t.setAsseblerResult(aux1.getAssemblerResult());
                registerList.get(aux1.getAssemblerResult()).setBusy(true);
            }
        } else if (t.getSecondOperand() instanceof Integer) {//Directo Terceto
            Terceto aux1 = this.tercetoList.get(((Integer) t.getSecondOperand()).intValue() - 1);
            String currentRegister = getRegister();
            addToCodeStructure("    MOV " + currentRegister + ", " + t.getFirstOperand());
            addToCodeStructure("    CMP " + currentRegister + ", " + aux1.getVariable());
            t.setAsseblerResult(this.registerMapping(currentRegister));
            registerList.get(this.registerMapping(currentRegister)).setBusy(true);
            //registerList.get(aux1.getAssemblerResult()).setBusy(false);
        }
    }

    private String getRegister() {
        for (Register r : registerList) {
            if (!r.isBusy()) {
                return r.getName();
            }
        }
        return freeReg(1).getName();
    }

    private String getRegister(String inUse) {
        for (Register r : registerList) {
            if (!r.isBusy()) {
                return r.getName();
            }
        }

        //en el caso de que todos los registros esten ocupados, guardo el registro en una variable auxiliar
        int i = 1;
        if (this.registerList.get(i).getName().equals(inUse)) {
            i = 2;
        }
        this.freeReg(i);
        return this.registerList.get(i).getName();
    }

    private Register freeReg(int i) {
        registerList.get(i).getTerceto().setAsseblerResult(contVariables);
        symbolTable.add(new Token("@vaux" + contVariables, "Identificador"));
        addToCodeStructure("    MOV " + "_@vaux" + contVariables + ", " + registerList.get(i).getName());
        contVariables++;
        registerList.get(i).setBusy(false);
        return registerList.get(i);
    }

    private void createVariable(Token t) {
        //deberia ser en el .data
        addToVariableStructure("_" + t.getToken() + " \t dd 0"); //dd limit 0-4294967295
    }

    private void initCode() {

        //genero encabezado del archivo
        addToDataStructure("; Codigo assembler generado a partir del codigo intermedio\n\n");
        addToDataStructure(".386                   ; For 80386 CPUs or higher.");
        addToDataStructure(".model flat, stdcall   ; Windows is always the 32-bit FLAT model");
        addToDataStructure("option casemap:none    ; Masm32 will use case-sensitive labels.");
        addToDataStructure("; The Windows.inc and other include files take care of many things!");
        addToDataStructure("include    \\masm32\\include\\windows.inc");
        addToDataStructure("include    \\masm32\\include\\kernel32.inc");
        addToDataStructure("include    \\masm32\\include\\user32.inc");
        addToDataStructure("includelib \\masm32\\lib\\user32.lib");
        addToDataStructure("includelib \\masm32\\lib\\kernel32.lib");
        addToDataStructure(".data");
        addToDataStructure("");

        //creo las variables utilizadas (recorro la T.S)
        if (symbolTable != null) {
            for (Token t : symbolTable) {
                if (t.getType().equals("Identificador")) {
                    createVariable(t);
                }
            }
        }

    }

    private void addVariables() {
        for (String s : this.variableStructure) {
            addToDataStructure(s);
        }
    }

    private void addFunctionsDeclarations() {
        addToDataStructure("MyBoxTitle  db  'Salida del programa:',0");
        addToDataStructure("msgZERO db 'FATAL ERROR: DIVISION BY ZERO ',0");
        addToDataStructure("msgOV db 'FATAL ERROR: OVERFLOW EN SUMA o MUL ',0");
        addToDataStructure("");
    }

    private void addFunctionsCode() {
        addToCodeStructure("");
        addToCodeStructure(".code");
        addToCodeStructure("");
        addToCodeStructure("START: ");
        addToCodeStructure("jmp inicio");
        addToCodeStructure("");
        addToCodeStructure("; Imprime el error de overflow");
        addToCodeStructure("ErrOF PROC");
        addToCodeStructure("    invoke  MessageBox, 0, ADDR msgOV, ADDR MyBoxTitle, MB_OK");
        addToCodeStructure("    invoke  ExitProcess,0");
        addToCodeStructure("ErrOF ENDP");
        addToCodeStructure("");
        addToCodeStructure("; Imprime el error de division por cero");
        addToCodeStructure("ErrZERO PROC");
        addToCodeStructure("    invoke  MessageBox, 0, ADDR msgZERO, ADDR MyBoxTitle, MB_OK");
        addToCodeStructure("    invoke  ExitProcess,0");
        addToCodeStructure("ErrZERO ENDP");
        addToCodeStructure("");
        addToCodeStructure("; -------------------- ");
    }
}
