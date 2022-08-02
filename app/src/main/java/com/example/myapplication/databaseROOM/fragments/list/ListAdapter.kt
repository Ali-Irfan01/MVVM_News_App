package com.example.myapplication.databaseROOM.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databaseROOM.fragments.update.UpdateFragmentDirections
import com.example.myapplication.databaseROOM.model.User

class ListAdapter(): RecyclerView.Adapter<com.example.myapplication.databaseROOM.fragments.list.ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): MyViewHolder  {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_layout_room, parent,false))
    }

    override fun getItemCount(): Int{
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val currentItem = userList[position]
        holder.itemView.findViewById<TextView>(R.id.txtId).text = currentItem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.txtName).text = currentItem.firstname.toString()
        holder.itemView.findViewById<TextView>(R.id.txtLastName).text = currentItem.lastname.toString()
        holder.itemView.findViewById<TextView>(R.id.txtAge).text = currentItem.age.toString()

        holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayout_custom).setOnClickListener {
            val action = listFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

}