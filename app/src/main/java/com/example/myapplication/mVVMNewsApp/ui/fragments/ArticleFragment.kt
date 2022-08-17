package com.example.myapplication.mVVMNewsApp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentArticleBinding
import com.example.myapplication.mVVMNewsApp.NewsActivity
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    private lateinit var _binding: FragmentArticleBinding
    val args: ArticleFragmentArgs by navArgs()
    var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        val view = _binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        _binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
        _binding.fab.setOnClickListener {
            lifecycleScope.launch {
                count = viewModel.isAvailable(article.url)

                Log.d("TAG", "Article url: ${article.url} count: $count")

                if (count == 0) {
                    viewModel.saveArticle(article)
                    Snackbar.make(view, "Article saved Successfully", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(view, "Article is already Saved", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }


//    val temp = lifecycleScope.launch {
//        val count = viewModel.isAvailable(article.url)
//    }
//    private suspend fun isAvailable(article: Article): Int? {
//        return viewModel.isAvailable(article.url)
//    }
}