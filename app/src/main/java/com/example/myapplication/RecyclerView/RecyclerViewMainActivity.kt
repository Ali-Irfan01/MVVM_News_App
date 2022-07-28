package com.example.myapplication.RecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRecyclerViewMainBinding

class RecyclerViewMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creating a list to populate the Recycler View
        var todoList = mutableListOf<Todo>(
            Todo("Follow Android Developers", false),
            Todo("Learn about Recycler View", true),
            Todo("Feed my cat", false),
            Todo("Prank my boss", false),
            Todo("Eat some curry", false),
            Todo("Ask my crush", false),
            Todo("Take a Shower", false),
            Todo("Go to Sleep", false),
        )
        val adapter = TodoAdapter(todoList)
        binding.rvTodos.adapter = adapter
        binding.rvTodos.layoutManager = LinearLayoutManager(this)

        binding.btnAddTodo.setOnClickListener{
            val title = binding.etTodo.text.toString()
            val todo = Todo(title, false)
            todoList.add(todo)
            //adapter.notifyDataSetChanged()  DO NOT use cz it updates all entries
            adapter.notifyItemInserted(todoList.size-1)
            binding.etTodo.text.clear()
        }
    }
}