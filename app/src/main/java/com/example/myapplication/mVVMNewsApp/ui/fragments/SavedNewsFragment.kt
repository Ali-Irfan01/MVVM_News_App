package com.example.myapplication.mVVMNewsApp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSavedNewsBinding
import com.example.myapplication.mVVMNewsApp.NewsActivity
import com.example.myapplication.mVVMNewsApp.adapters.NewsAdapter
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {

    private lateinit var _binding: FragmentSavedNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel

// To bind the fragment with the
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)
        val view = _binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()


        // To navigate to article fragment with current article object
        newsAdapter.setOnItemClickListener {
            // Bundle the article object
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            // navigate to next fragment with the bundle obj packed article
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        // Item Touch helper call back object instantiation with onSwiped delete from DB and undo to insert back in DB
        val itemTouchHelperCallback = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            // No functionality to be implemented in ON Move item
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }
            // On swipe we have to delete and on undo in SnackBar we have to restore
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Fetching position going to delete
                val position = viewHolder.absoluteAdapterPosition
                val article = newsAdapter.differ.currentList[position]
                // position fetched now initialising delete
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                    // SnackBar to give message and give option to undo delete
                    setAction("Undo")   {
                        // Item restored
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        // ItemTouchHelper implemented
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(_binding.rvSavedNews)
        }

        // Display all saved Items from DB, ViewModel -> Repository -> ArticleDAO -> FetchFromDBQuery
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            // Differ only updates changed items not all items
            newsAdapter.differ.submitList(articles)
        })
    }

    private fun setUpRecyclerView(){
        newsAdapter = NewsAdapter()
        _binding?.rvSavedNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}