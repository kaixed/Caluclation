package com.kaixed.caluculation.entity;

/**
 * @Author: kaixed
 * @Date: 2023/12/29 23:05
 * @Description: TODO
 */
public class Equation {
    private String equation;
    private String result;
    private String inPutValue;

    public Equation(String equation, String result, String inPutValue) {
        this.equation = equation;
        this.result = result;
        this.inPutValue = inPutValue;
    }

    public Equation(String equation, String result) {
        this.equation = equation;
        this.result = result;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInPutValue() {
        return inPutValue;
    }

    public void setInPutValue(String inPutValue) {
        this.inPutValue = inPutValue;
    }
}
