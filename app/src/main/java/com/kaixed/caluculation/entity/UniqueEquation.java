package com.kaixed.caluculation.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author: kaixed
 * @Date: 2023/12/31 22:15
 * @Description: TODO
 */
@Entity(tableName = "uniqueEquations")
public class UniqueEquation {

    @PrimaryKey
    @NonNull
    private String equationId;
    @ColumnInfo(name = "equation")
    private String equation;
    @ColumnInfo(name = "result")
    private String result;
    @ColumnInfo(name = "inPutValue")
    private String inPutValue;
    @ColumnInfo(name = "time")
    private String time;

    public UniqueEquation(String equation, String result, String inPutValue, String time) {
        this.equation = equation;
        this.result = result;
        this.inPutValue = inPutValue;
        this.time = time;
    }

    public String getEquationId() {
        return equationId;
    }

    public void setEquationId(String equationId) {
        this.equationId = equationId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
