package com.uni.compiler.parser;

public class Terceto {
    private Object firstOperand;
    private Object secondOperand;
    private Object Operator;
    private Object Value;

    private Integer assemblerResult=null;

    public Terceto(){
    }

    public Terceto(Object o, Object fo, Object so, Object v){
        this.Operator = o;
        this.firstOperand = fo;
        this.secondOperand = so;
        this.Value = v;
    }

    public Object getFirstOperand() {
        return firstOperand;
    }

    public Object getSecondOperand() {
        return secondOperand;
    }

    public Object getOperator() {
        return Operator;
    }

    public Object getValue() {
        return Value;
    }

    public void setFirstOperand(Object firstOperand) {
        this.firstOperand = firstOperand;
    }

    public void setSecondOperand(Object secondOperand) {
        this.secondOperand = secondOperand;
    }

    public void setOperator(Object Operator) {
        this.Operator = Operator;
    }

    public void setValue(Object Value) {
        this.Value = Value;
    }
    
    private String operandToString(Object o){
        String s;
        if (o instanceof Integer){
            s = "[terceto-" + o.toString() + "]";
        } else {
            s = o.toString();
        }
        return s;
    }
    
    @Override
    public String toString(){
        String terceto;
        terceto = "[" + this.Operator.toString();
        terceto += " | ";
        if (this.Operator.equals("BF") || this.Operator.equals("BI")){
            terceto += this.firstOperand.toString();
        } else {
            terceto += operandToString(this.firstOperand);
        }
        terceto += " | " + operandToString(this.secondOperand) + " ]";
        return terceto;
    }
    
    
    public void setAsseblerResult(int i){
    	assemblerResult=i;
    }
    
    public Integer getAssemblerResult(){
    	return assemblerResult;
    }
  
    
}
