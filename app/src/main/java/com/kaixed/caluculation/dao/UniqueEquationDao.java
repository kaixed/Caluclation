package com.kaixed.caluculation.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kaixed.caluculation.entity.Equation;
import com.kaixed.caluculation.entity.UniqueEquation;

import java.util.List;

/**
 * @Author: kaixed
 * @Date: 2023/12/31 10:19
 * @Description: TODO
 */
@Dao
public interface UniqueEquationDao {
    @Insert
    void insert(UniqueEquation uniqueEquation);

    @Query("SELECT * FROM uniqueEquations")
    List<UniqueEquation> getAllData();

    @Query("SELECT * FROM uniqueEquations WHERE equationId LIKE :equationId || '%' ")
    List<UniqueEquation> findItemsWithId(String equationId);
}
