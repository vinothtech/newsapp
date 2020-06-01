package com.newsapp.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*

data class Articles(

    @SerializedName("source")
    val source: Sources,
    @SerializedName("author")
    val author: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("urlToImage")
    val image: String,
    @SerializedName("publishedAt")
    val publihsedAt: Date,
    @SerializedName("content")
    val content: String,
    @SerializedName("url")
    val articleUrl: String


)
