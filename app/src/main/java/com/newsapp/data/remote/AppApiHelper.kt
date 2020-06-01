package com.crimsonlogic.data.remote

import com.newsapp.data.model.ResponseParser
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


class AppApiHelper {

    private val API_KEY = "ac2770ffd05a44b6b9bc5f38075000ea";
    private val LANGUAGE = "en"

    companion object {
        private val BASE_URL = "https://newsapi.org/v2/"
        lateinit var apiHelper: ApiHelper;
        lateinit var appAPIHelper: AppApiHelper;

        fun getAPPAPIInstance(): AppApiHelper {

            if (::appAPIHelper.isInitialized && appAPIHelper != null) {
                return appAPIHelper;
            } else {
                getAPIInstance()
                appAPIHelper = AppApiHelper();
            }
            return appAPIHelper;
        }

        fun getAPIInstance(): ApiHelper {
            if (::apiHelper.isInitialized && apiHelper != null) {
                return apiHelper;
            } else {

                val gson = GsonBuilder()
                    .setLenient()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create()

                apiHelper = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create<ApiHelper>(ApiHelper::class.java)
            }
            return apiHelper;
        }
    }

    fun getSourceList(): Single<ResponseParser> {
        return apiHelper.getSourceList(LANGUAGE, API_KEY);
    }

    fun getArticles(sourceId: String): Single<ResponseParser> {
        return apiHelper.getArticleList(sourceId, API_KEY);
    }

}