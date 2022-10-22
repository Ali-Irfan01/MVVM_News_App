package com.example.myapplication.rssNewsReader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityGeneralNewsBinding
import com.example.myapplication.rssNewsReader.ParserEnum.*
import com.example.myapplication.rssNewsReader.adapter.ParsedNewsAdapter
import com.example.myapplication.rssNewsReader.model.GeneralNewsModel
import com.example.myapplication.rssNewsReader.util.channelType
import com.example.myapplication.rssNewsReader.util.loadXmlFromNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GeneralNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneralNewsBinding
//        private val url = "https://arynews.tv/category/international-2/feed/"                 //-> Ary
//    private val url = "https://dailypakistan.com.pk/rss/national"                               //-> DailyPakistan
//    private val url = "https://www.neonetwork.pk/rss/pakistan"                                //-> DailyPakistan
//    private val url = "https://www.nation.com.pk/rss/national"                                //-> DailyPakistan
//    private val url = "https://www.24newshd.tv/rss/pakistan"                                  //-> DailyPakistan
//    private var url = "https://feeds.feedburner.com/geo/GiKR"                                 //-> Geo
//    private val url = "https://feeds.feedburner.com/GeoAmazingAndInteresting-GeoTvNetwork"    //-> Geo
    private val url = "https://www.express.pk/feed/"                                          //-> Express
//    private val url = "https://www.samaa.tv/feeds/pakistan/"                                  //-> Samaa
//    private val url = "https://www.samaa.tv/feeds/global/"                                    //-> Samaa
//    private val url = "https://feeds.feedburner.com/com/Yeor"                                 //-> Jang
//    private val url = "https://www.urdupoint.com/en/sitemap/news.rss"                         //-> UrduPoint
//    private val url = "https://www.aaj.tv/feeds/latest-news"                                  //-> Aaj
//    private val url = "https://abbtakk.tv/en/feed/"                                           //-> Abb Tak
//    private val url = "https://www.city42.tv/rss/headlines"                                   //-> Daily Pakistan
//    private val url = "https://www.humnews.pk/category/pakistan/feed/"                        //-> Hum
//    private val url = "https://newsone.tv/feed/"                                              //-> Hum
    //    private val url = "https://tribune.com.pk/feed/latest"                                      //-> Tribune

    private var newsList: List<GeneralNewsModel> = emptyList()
    private var newsAdapter: ParsedNewsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        channelType = EXPRESS.name
        setupRecyclerView()

        binding.btnFetchFeed.setOnClickListener {
            newsAdapter!!.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            newsList = loadXmlFromNetwork(url)
            newsAdapter = ParsedNewsAdapter(newsList)
            withContext(Dispatchers.Main) {
                binding.rvNewsMain.adapter = newsAdapter
                binding.rvNewsMain.layoutManager = LinearLayoutManager(baseContext)
            }
            setUpAdapterClickListener()
        }
    }

    private fun setUpAdapterClickListener() {
        newsAdapter?.setOnItemClickListener {
            Toast.makeText(baseContext, "Adapter click listened", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.link))
            logItemDetails(it)
            startActivity(intent)
        }
    }

    private fun logItemDetails(it: GeneralNewsModel) {
        Log.d("Title", "${it.title}")
        Log.d("Link", "${it.link}")
        Log.d("Description", "${it.description}")
        Log.d("Publish Date", "${it.pubDate}")
        Log.d("Image Url", "${it.imgUrl}")
        Log.d("Content", "${it.content}")
    }
}