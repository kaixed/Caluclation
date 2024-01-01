package com.kaixed.caluculation.common;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kaixed.caluculation.dao.MistakesDao;
import com.kaixed.caluculation.dao.RecordsDao;
import com.kaixed.caluculation.dao.UniqueEquationDao;
import com.kaixed.caluculation.dao.UserDao;
import com.kaixed.caluculation.entity.Equation;
import com.kaixed.caluculation.entity.Mistakes;
import com.kaixed.caluculation.entity.Records;
import com.kaixed.caluculation.entity.UniqueEquation;
import com.kaixed.caluculation.entity.User;

/**
 * @Author: kaixed
 * @Date: 2023/12/31 10:19
 * @Description: TODO
 */
@Database(entities = {User.class, UniqueEquation.class, Records.class, Mistakes.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract UniqueEquationDao equationDao();

    public abstract RecordsDao recordsDao();

    public abstract MistakesDao mistakesDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "user_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
