package com.kaixed.caluculation.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kaixed.caluculation.entity.User;

/**
 * @Author: kaixed
 * @Date: 2023/12/31 10:19
 * @Description: TODO
 */
@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

}
