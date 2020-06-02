package com.newsapp.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.crimsonlogic.data.remote.AppApiHelper
import com.newsapp.data.model.Articles
import com.newsapp.data.model.Sources
import com.newsapp.data.model.ResponseParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(context: Application) : AndroidViewModel(context) {

    val sourceList: MutableLiveData<List<Sources>> = MutableLiveData<List<Sources>>()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>();
    val apiHelper: AppApiHelper by lazy { AppApiHelper.getAPPAPIInstance() }
    val articleList: ArrayList<Articles> = ArrayList<Articles>();
    val articleListLiveData = MutableLiveData<List<Articles>>()
    val currentPage: MutableLiveData<Int> = MutableLiveData()
    var compositeDisposable = CompositeDisposable();

    fun getSourcesList() {
        isLoading.value = true
        compositeDisposable.add(apiHelper.getSourceList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ResponseParser>() {
                override fun onSuccess(response: ResponseParser) {
                    isLoading.value = false
                    if (response.status.equals("ok")) {
                        sourceList.value = response.sources
                    }
                }

                override fun onError(e: Throwable) {
                    isLoading.value = true
                    e.printStackTrace()
                }
            })
        );
    }


    fun getArticles(sourceId: String) {
        val articleList = articleListLiveData.value?.filter { it.source.id == sourceId } ?: listOf()
        if (articleList.isEmpty()) {
            loadArticles(sourceId)
        }
    }

    fun loadArticles(articleId: String) {
        compositeDisposable.add(apiHelper.getArticles(articleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ResponseParser>() {
                override fun onSuccess(response: ResponseParser) {
                    isLoading.value = false
                    if (response.status.equals("ok")) {
                        articleList.addAll(response.articles)
                        articleListLiveData.value = articleList
                    }
                }
                override fun onError(e: Throwable) {
                    isLoading.value = false
                    e.printStackTrace()
                }
            })
        )

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}