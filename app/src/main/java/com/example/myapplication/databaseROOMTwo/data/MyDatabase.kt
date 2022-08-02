package com.example.myapplication.databaseROOMTwo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.databaseROOMTwo.Converters
import com.example.myapplication.databaseROOMTwo.Person


@Database(entities = [Person::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase:RoomDatabase() {

    abstract fun myDao(): MyDao

    companion object{
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            //else create a new database and return it
            kotlin.synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}