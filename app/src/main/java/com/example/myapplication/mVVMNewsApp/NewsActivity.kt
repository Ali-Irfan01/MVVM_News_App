package com.example.myapplication.mVVMNewsApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityNewsBinding
import com.example.myapplication.mVVMNewsApp.db.ArticleDatabase
import com.example.myapplication.mVVMNewsApp.repository.NewsRepository
import com.example.myapplication.mVVMNewsApp.ui.fragments.BreakingNewsFragment
import com.example.myapplication.mVVMNewsApp.ui.fragments.SavedNewsFragment
import com.example.myapplication.mVVMNewsApp.ui.fragments.SearchNewsFragment
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewViewModelProviderFactory
import com.example.myapplication.mVVMNewsApp.ui.viewModel.NewsViewModel

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewViewModelProviderFactory(newsRepository = repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

        val breakingNewsFragment = BreakingNewsFragment()
        val savedNewsFragment = SavedNewsFragment()
        val searchNewsFragment = SearchNewsFragment()

        //setupActionBarWithNavController(findNavController(R.id.newsNavHostFragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.breakingNewsFragment -> setCurrentFragment(breakingNewsFragment)
                R.id.savedNewsFragment -> setCurrentFragment(savedNewsFragment)
                R.id.searchNewsFragment -> setCurrentFragment(searchNewsFragment)
            }
            true
        }

    }
    private fun setCurrentFragment (fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}