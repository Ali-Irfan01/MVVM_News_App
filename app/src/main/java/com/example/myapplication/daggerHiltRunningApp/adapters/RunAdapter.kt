package com.example.myapplication.daggerHiltRunningApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.NavGraphDirections
import com.example.myapplication.R
import com.example.myapplication.daggerHiltRunningApp.db.Run
import com.example.myapplication.daggerHiltRunningApp.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var view: View

    /** List Differ
     * Very efficient for recycler views
     * Because we only have to update the items that are changed
     * so we don't have to update the complete lists again
     * */

    val diffCallBack = object : DiffUtil.ItemCallback<Run>()    {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return  oldItem.hashCode() == newItem.hashCode()
        }

    }
    val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Run>) = differ.submitList(list)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_run,
            parent,
            false
        )
        return RunViewHolder(
                view
            )
    }




    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(run.img).into(findViewById(R.id.ivRunImage))


            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp!!
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            view.findViewById<TextView>(R.id.tvDate).text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKH}km/h"
            view.findViewById<TextView>(R.id.tvAvgSpeed).text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            view.findViewById<TextView>(R.id.tvDistance).text = distanceInKm

            view.findViewById<TextView>(R.id.tvTime).text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned}kcal"
            view.findViewById<TextView>(R.id.tvCalories).text = caloriesBurned
        }
    }
}