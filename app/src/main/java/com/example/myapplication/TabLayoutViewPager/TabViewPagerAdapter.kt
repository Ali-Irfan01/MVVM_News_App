package com.example.myapplication.TabLayoutViewPager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class TabViewPagerAdapter(
    val images: List<Int>
) : RecyclerView.Adapter<TabViewPagerAdapter.TabViewPageViewHolder>(){

    inner class TabViewPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager, parent, false)
        return TabViewPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: TabViewPageViewHolder, position: Int) {
        val curImage = images[position]
        holder.itemView.findViewById<ImageView>(R.id.ivImage).setImageResource(curImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}