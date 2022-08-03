package com.example.myapplication.mVVMNewsApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.mVVMNewsApp.NewsActivity
import com.example.myapplication.mVVMNewsApp.adapters.NewsAdpater
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewsViewModel
import com.example.myapplication.mVVMNewsApp.util.Resource

class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdpater

    val TAG = "BreakingNewsFragment "

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An Error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                else -> {}
            }
        })
    }

    private fun hideProgressBar(){
        view?.findViewById<ProgressBar>(R.id.paginationProgressBar)?.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        view?.findViewById<ProgressBar>(R.id.paginationProgressBar)?.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView(){
        newsAdapter = NewsAdpater()
        view?.findViewById<RecyclerView>(R.id.rvBreakingNews)?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}