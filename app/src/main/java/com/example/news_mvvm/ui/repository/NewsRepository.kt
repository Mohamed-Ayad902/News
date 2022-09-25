package com.example.news_mvvm.ui.repository

import com.example.news_mvvm.ui.api.RetrofitInstance
import com.example.news_mvvm.ui.db.ArticleDatabase
import com.example.news_mvvm.ui.models.Article

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNr: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNr)

    suspend fun searchNews(searchQuery: String, pageNr: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNr)

    fun getSavedNews() = db.dao().getSavedArticles()

    suspend fun insertNews(article: Article) = db.dao().insertArticle(article)

    suspend fun deleteNews(article: Article) = db.dao().deleteArticle(article)

}