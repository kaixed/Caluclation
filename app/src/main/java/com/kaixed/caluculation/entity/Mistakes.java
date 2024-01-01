package com.kaixed.caluculation.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author: kaixed
 * @Date: 2024/1/1 10:40
 * @Description: TODO
 */
@Entity(tableName = "mistakes")
public class Mistakes {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "equation")
    private String equation;

    @ColumnInfo(name = "result")
    private String result;

    @ColumnInfo(name = "inPutValue")
    private String inPutValue;

    @ColumnInfo(name = "isTrue")
    private boolean isTrue;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "counts")
    private int counts;

    public Mistakes(){

    }

    public Mistakes(String equation, String result, String inPutValue, boolean isTrue, String time, int counts) {
        this.equation = equation;
        this.result = result;
        this.inPutValue = inPutValue;
        this.isTrue = isTrue;
        this.time = time;
        this.counts = counts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
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
