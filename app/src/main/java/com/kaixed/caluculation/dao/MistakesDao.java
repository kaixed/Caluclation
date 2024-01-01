package com.kaixed.caluculation.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kaixed.caluculation.entity.Mistakes;
import com.kaixed.caluculation.entity.Records;

import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2024/1/1 10:35
 * @Description: TODO
 */
@Dao
public interface MistakesDao {
    @Insert
    void insert(Mistakes mistakes);

    @Query("SELECT * FROM mistakes")
    List<Mistakes> getAllItems();
}