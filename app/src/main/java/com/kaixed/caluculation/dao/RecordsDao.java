package com.kaixed.caluculation.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.kaixed.caluculation.entity.Records;

import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2024/1/1 10:35
 * @Description: TODO
 */
@Dao
public interface RecordsDao {

    @Insert
    void insert(Records records);

    @Query("SELECT * FROM records ORDER BY id")
    List<Records> getAllItemsInOrder();

}
