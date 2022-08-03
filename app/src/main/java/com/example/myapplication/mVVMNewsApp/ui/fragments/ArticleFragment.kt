package com.example.myapplication.mVVMNewsApp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentArticleBinding
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewsActivity
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment: Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    private lateinit var _binding: FragmentArticleBinding
    val args: ArticleFragmentArgs by navArgs()

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

        _binding.fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved Successfully", Snackbar.LENGTH_SHORT).show()
        }
    }
}