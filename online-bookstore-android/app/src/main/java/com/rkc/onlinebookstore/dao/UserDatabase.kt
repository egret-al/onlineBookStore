package com.rkc.onlinebookstore.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rkc.onlinebookstore.model.user.User

/**
 * @author rkc
 * @date 2020/11/18 22:03
 * @version 1.0
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    companion object {
        private lateinit var instance: UserDatabase

        @Synchronized fun getInstance(context: Context) : UserDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database")
                    .build()
            }
            return instance
        }
    }
}