package com.kaixed.caluculation.entity;

/**
 * @Author: kaixed
 * @Date: 2023/12/30 16:32
 * @Description: TODO
 */
public class Mistakes extends Equation{
    private String time;
    private int counts;
    public Mistakes(String equation, String result, String inPutValue , String time , int counts) {
        super(equation, result, inPutValue);
        this.counts = counts;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
