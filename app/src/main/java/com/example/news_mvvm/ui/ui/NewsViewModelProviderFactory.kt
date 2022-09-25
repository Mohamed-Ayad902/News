package com.example.news_mvvm.ui.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news_mvvm.ui.repository.NewsRepository

// we use this class because normally we can't use constructor at the viewModel
class NewsViewModelProviderFactory(
    val app: Application,
    private val newsRepository: NewsRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }

}