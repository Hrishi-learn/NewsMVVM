package com.hrishi.newsappmvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hrishi.newsappmvvm.ui.repository.NewsRepository
import com.hrishi.newsappmvvm.ui.ui.NewsViewModel

class NewsModelProviderFactory(val newsRepository: NewsRepository): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}