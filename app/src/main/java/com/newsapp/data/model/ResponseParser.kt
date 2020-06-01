package com.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseParser(
    @SerializedName("status")
    val status: String,
    @SerializedName("sources")
    val sources: ArrayList<Sources>,
    @SerializedName("articles")
    val articles: ArrayList<Articles>
)