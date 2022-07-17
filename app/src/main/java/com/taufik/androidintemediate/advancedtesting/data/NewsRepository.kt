package com.taufik.androidintemediate.advancedtesting.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.taufik.androidintemediate.BuildConfig
import com.taufik.androidintemediate.advancedtesting.data.local.entity.NewsEntity
import com.taufik.androidintemediate.advancedtesting.data.local.room.NewsDao
import com.taufik.androidintemediate.advancedtesting.data.remote.Result
import com.taufik.androidintemediate.advancedtesting.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {
    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        val apiKey = BuildConfig.API_KEY
        try {
            val response = apiService.getNews(apiKey)
            val articles = response.articles
            val newsList = articles.map { article ->
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url
                )
            }
            emit(Result.Success(newsList))
        } catch (e: Exception) {
            Log.e(TAG, "getHeadlineNews: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    fun saveNews(newsEntity: NewsEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.saveNews(newsEntity)
        }
    }

    fun deleteNews(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.deleteNews(title)
        }
    }

    fun isNewsBookmarked(title: String): LiveData<Boolean> {
        return newsDao.isNewsBookmarked(title)
    }

    companion object {
        private val TAG = NewsRepository::class.java.simpleName

        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewsRepository = instance ?: synchronized(this) {
            instance ?: NewsRepository(apiService, newsDao)
        }.also { instance = it }
    }
}