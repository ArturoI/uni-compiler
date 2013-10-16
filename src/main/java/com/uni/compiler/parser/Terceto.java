package com.uni.compiler.parser;

public class Terceto {
    private Object firstOperand;
    private Object secondOperand;
    private Object Operator;
    private Object Value;
    private int id;

    public Terceto(Object o, Object fo, Object so, Object v, int id){
        this.Operator = o;
        this.firstOperand = fo;
        this.secondOperand = so;
        this.Value = v;
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
        terceto = "[terceto-" + this.id + "]: \"" + this.Operator.toString() + "\"";
        terceto += " | " + operandToString(this.firstOperand);
        terceto += " | " + operandToString(this.secondOperand);
        return terceto;
    }
    
}
