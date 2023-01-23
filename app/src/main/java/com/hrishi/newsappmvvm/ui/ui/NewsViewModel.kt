package com.hrishi.newsappmvvm.ui.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hrishi.newsappmvvm.ui.NewsResponse
import com.hrishi.newsappmvvm.ui.models.Article
import com.hrishi.newsappmvvm.ui.repository.NewsRepository
import com.hrishi.newsappmvvm.ui.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository):ViewModel(){
    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var pageNumber = 1
    init {
        getBreakingNews("us")
    }
    fun getSearchNews(search: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.getSearchNews(search,pageNumber)
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    private fun handleSearchNewsResponse(response:Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
    fun getBreakingNews(countryCode:String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,pageNumber)
        Log.e("msg","$newsRepository")
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }
    private fun handleBreakingNewsResponse(response:Response<NewsResponse>):Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    fun saveArticle(article:Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }
    fun deleteArticle(article:Article) = viewModelScope.launch {
        newsRepository.delete(article)
    }
    fun getSavedNews() = newsRepository.getAllArticles()
}