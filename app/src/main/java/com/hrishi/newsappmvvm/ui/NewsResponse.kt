package com.hrishi.newsappmvvm.ui

import com.hrishi.newsappmvvm.ui.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)