package com.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class Sources(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)


