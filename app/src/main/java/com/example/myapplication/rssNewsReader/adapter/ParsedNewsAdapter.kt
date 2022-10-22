package com.example.myapplication.rssNewsReader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.rssNewsReader.model.GeneralNewsModel

class ParsedNewsAdapter(
    private var newsList: List<GeneralNewsModel>
) : RecyclerView.Adapter<ParsedNewsAdapter.NewsAdapterViewHolder>() {

    inner class NewsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var view: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.inflateable_news, parent, false)
        return NewsAdapterViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsAdapterViewHolder, position: Int) {
        val newsHolder = newsList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.txtNewsTitle).text = newsHolder.title
            findViewById<TextView>(R.id.txtNewsLink).text = newsHolder.link
            findViewById<TextView>(R.id.txtPubDate).text = newsHolder.pubDate
            if (newsHolder.imgUrl != "imgUrl")
                Glide.with(this.context)
                    .load(newsHolder.imgUrl)
                    .centerCrop()
                    .into(findViewById(R.id.ivNews))
            setOnClickListener {
                onItemClickListener?.let { it(newsHolder) }
            }
        }
    }

    private var onItemClickListener: ((GeneralNewsModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (GeneralNewsModel) -> Unit) {
        onItemClickListener = listener
    }
}