package com.taufik.androidintemediate.advancedtesting.di

import android.content.Context
import com.taufik.androidintemediate.advancedtesting.data.NewsRepository
import com.taufik.androidintemediate.advancedtesting.data.local.room.NewsDatabase
import com.taufik.androidintemediate.advancedtesting.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}