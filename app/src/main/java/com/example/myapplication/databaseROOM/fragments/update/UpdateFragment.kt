package com.example.myapplication.databaseROOM.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databaseROOM.model.User
import com.example.myapplication.databaseROOM.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var myUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        myUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        view.findViewById<EditText>(R.id.edUpdateFirstName).setText(args.currentUser.firstname)
        view.findViewById<EditText>(R.id.etUpdateLastName).setText(args.currentUser.lastname)
        view.findViewById<EditText>(R.id.etUpdateAge).setText(args.currentUser.age.toString())

        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            updateItem()
            Toast.makeText(requireContext(),"I am Clicked!", Toast.LENGTH_SHORT).show()
        }


        // Add menu
        setHasOptionsMenu(true)



        return view
    }

    private fun updateItem(){
        val firstName  = view?.findViewById<EditText>(R.id.edUpdateFirstName)?.text.toString()
        val lastname = view?.findViewById<EditText>(R.id.etUpdateLastName)?.text.toString()
        val age = view?.findViewById<EditText>(R.id.etUpdateAge)?.text.toString()

        if(inputCheck(firstName,lastname,age)){
            // Create user object and validate it with correct data
            val updatedUser = User(args.currentUser.id, firstName, lastname, age.toInt())
            // Now use view model to update the user on screen
            myUserViewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Item Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate back to list fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean   {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            myUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentUser.firstname}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Delete ${args.currentUser.firstname}")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstname}")
        builder.create().show()
    }

}