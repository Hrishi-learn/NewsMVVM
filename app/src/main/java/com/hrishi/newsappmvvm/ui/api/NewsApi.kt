package com.hrishi.newsappmvvm.ui.api

import com.hrishi.newsappmvvm.ui.NewsResponse
import com.hrishi.newsappmvvm.ui.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi{
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country:String="in",
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apikey:String=API_KEY
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchResults(
        @Query("q")
        searchedParameter:String = "",
        @Query("page")
        pageNumber:Int=1,
        @Query("apikey")
        apikey: String = API_KEY
    ):Response<NewsResponse>
}