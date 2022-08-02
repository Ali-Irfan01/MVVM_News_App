package com.example.myapplication.databaseROOMTwo.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.databaseROOMTwo.data.MyDao
import com.example.myapplication.databaseROOMTwo.Person

class MyRepository(private val myDao: MyDao) {

    val readPerson: LiveData<List<Person>> = myDao.readPerson()

    suspend fun insertPerson(person: Person)    {
        myDao.insertPerson(person)
    }
}