package com.example.newsapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    var source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
):java.io.Serializable