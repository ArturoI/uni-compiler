package com.uni.compiler.parser;

public class Terceto {
    private Object firstOperand;
    private Object secondOperand;
    private Object Operator;
    private Object Value;

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
    
}
