package com.example.news_mvvm.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val source: Source?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable