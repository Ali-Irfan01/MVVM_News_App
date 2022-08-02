package com.example.myapplication.databaseROOMTwo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.databaseROOMTwo.Person
import com.example.myapplication.databaseROOMTwo.data.MyDatabase
import com.example.myapplication.databaseROOMTwo.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application): AndroidViewModel(application) {

    private val dao = MyDatabase.getDatabase(application).myDao()
    private val repository = MyRepository(dao)

    val readPerson: LiveData<List<Person>> = repository.readPerson

    fun insertPerson(person: Person)    {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPerson(person)
        }
    }
}