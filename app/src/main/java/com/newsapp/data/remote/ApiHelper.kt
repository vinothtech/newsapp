package com.crimsonlogic.data.remote

import com.newsapp.data.model.ResponseParser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {

    @GET("sources")
    fun getSourceList(
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Single<ResponseParser>;

    @GET("top-headlines")
    fun getArticleList(
        @Query("sources") type: String,
        @Query("apikey") apiKey: String
    ): Single<ResponseParser>;

}