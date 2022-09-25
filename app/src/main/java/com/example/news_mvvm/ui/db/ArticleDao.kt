package com.example.news_mvvm.ui.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.news_mvvm.ui.models.Article

@Dao
interface ArticleDao {

    // we return long --> the id which was inserted or updated
    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(article: Article): Long

    @Query("SELECT * FROM article_table")
    fun getSavedArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}