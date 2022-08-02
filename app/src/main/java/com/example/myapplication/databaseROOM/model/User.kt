package com.example.myapplication.databaseROOM.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)    val id: Long,

    /*@ColumnInfo(name = "first_Name")*/    val firstname: String,

    /*@ColumnInfo(name = "last_name")*/     val lastname: String,

    /*@ColumnInfo(name = "age")*/           val age: Int
) : Parcelable{
    constructor(Id: String, firstname: String, lastname: String, age: Int): this(0,firstname,lastname,age)
}