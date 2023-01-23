package com.hrishi.newsappmvvm.ui.repository

import com.hrishi.newsappmvvm.ui.NewsResponse
import com.hrishi.newsappmvvm.ui.api.RetrofitInstance
import com.hrishi.newsappmvvm.ui.db.ArticleDatabase
import com.hrishi.newsappmvvm.ui.db.articleDao
import com.hrishi.newsappmvvm.ui.models.Article
import retrofit2.Response

class NewsRepository(
    val db:ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode:String,pageNumber:Int): Response<NewsResponse> {
        return RetrofitInstance().api.getBreakingNews(countryCode,pageNumber)
    }
    suspend fun getSearchNews(countryCode: String,pageNumber: Int):Response<NewsResponse>{
        return RetrofitInstance().api.getSearchResults(countryCode,pageNumber)
    }
    suspend fun insert(article: Article) = db.getArticleDao().insert(article)
    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
    fun getAllArticles() = db.getArticleDao().getArticles()
}