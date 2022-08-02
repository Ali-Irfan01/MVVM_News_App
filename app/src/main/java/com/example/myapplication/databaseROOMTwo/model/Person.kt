package com.example.myapplication.databaseROOMTwo

import android.graphics.Bitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_table")
data class Person (
    @PrimaryKey(autoGenerate = true)
        val id: Int,
        val firstName: String,
        val lastName: String,
        val age: Int,
        val profilePhoto: Bitmap,
    @Embedded
        val address: Address

)
data class Address(
    val streetName: String,
    val streetNumber: Int
    )