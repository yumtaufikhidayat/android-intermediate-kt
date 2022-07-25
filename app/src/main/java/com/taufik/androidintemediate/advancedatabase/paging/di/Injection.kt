package com.taufik.androidintemediate.advancedatabase.paging.di

import android.content.Context
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteRepository
import com.taufik.androidintemediate.advancedatabase.paging.database.QuoteDatabase
import com.taufik.androidintemediate.advancedatabase.paging.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): QuoteRepository {
        val database = QuoteDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return QuoteRepository(database, apiService)
    }
}