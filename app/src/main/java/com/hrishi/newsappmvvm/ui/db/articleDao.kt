package com.hrishi.newsappmvvm.ui.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hrishi.newsappmvvm.ui.models.Article
@Dao
interface articleDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article):Long
    @Query("SELECT * FROM articles")
    fun getArticles():LiveData<List<Article>>
    @Delete
    suspend fun delete(article: Article)
}