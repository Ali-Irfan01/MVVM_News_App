package com.example.myapplication.databaseROOM.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.databaseROOM.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase:RoomDatabase() {

    abstract fun userDao(): UserDao

    //Abstract class because only one instance of the database is required
    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            //Check if database exists already, if yes return the same instance
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            //else create a new database and return it
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}