package com.example.news_mvvm.ui.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.news_mvvm.databinding.ActivityNewsBinding
import com.example.news_mvvm.ui.db.ArticleDatabase
import com.example.news_mvvm.ui.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = NewsRepository(ArticleDatabase.getInstance(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]


        val navController =
            (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment).navController

        binding.bottomNavBar.setupWithNavController((navController))

    }
}