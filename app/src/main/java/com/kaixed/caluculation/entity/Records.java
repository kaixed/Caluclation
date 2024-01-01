package com.kaixed.caluculation.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author: kaixed
 * @Date: 2024/1/1 10:32
 * @Description: TODO
 */
@Entity(tableName = "records")
public class Records {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "record")
    public String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
