package com.hrishi.newsappmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hrishi.newsappmvvm.R
import com.hrishi.newsappmvvm.databinding.ActivityNewsBinding
import com.hrishi.newsappmvvm.ui.db.ArticleDatabase
import com.hrishi.newsappmvvm.ui.repository.NewsRepository
import com.hrishi.newsappmvvm.ui.ui.NewsViewModel

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val newsModelProviderFactory = NewsModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,newsModelProviderFactory).get(NewsViewModel::class.java)
    }
}