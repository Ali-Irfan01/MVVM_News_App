package com.example.myapplication.databaseROOMTwo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.R
import com.example.myapplication.databaseROOMTwo.Person

class PersonAdapter: RecyclerView.Adapter<PersonAdapter.MyViewHolder>() {

    private var personList = emptyList<Person>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.room_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.txtfirstnameroomtwo).text = personList[position].firstName
        holder.itemView.findViewById<TextView>(R.id.txtsecondnameroomtwo).text = personList[position].lastName
        holder.itemView.findViewById<TextView>(R.id.txtAgeroomtwo).text = personList[position].age.toString()
        holder.itemView.findViewById<TextView>(R.id.txtstreetname).text = personList[position].address.streetName
        holder.itemView.findViewById<TextView>(R.id.txtstreetnumber).text = personList[position].address.streetNumber.toString()
        holder.itemView.findViewById<ImageView>(R.id.ivroomtwo).load(personList[position].profilePhoto)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun setData(person: List<Person>){
        personList = person
        notifyDataSetChanged()
    }
}