package com.example.myapplication.databaseROOM.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databaseROOM.model.User
import com.example.myapplication.databaseROOM.viewmodel.UserViewModel

class addFragment : Fragment() {

    private lateinit var myUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        myUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<Button>(R.id.btnAddTwo).setOnClickListener {
            insertDataTODatabase()
        }

        return  view
    }

    private fun insertDataTODatabase(){
        val firstName = view?.findViewById<EditText>(R.id.edFirstName)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.etLastNameTwo)?.text.toString()
        val age  = view?.findViewById<EditText>(R.id.etAgeTwo)?.text.toString()

        if(inputCheck(firstName,lastName,age)){
            val user = User(0, firstName, lastName, age.toInt())
            myUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            //Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else   {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }
    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean   {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }
}