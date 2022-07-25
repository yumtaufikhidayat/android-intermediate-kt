package com.taufik.androidintemediate.advancedatabase.paging.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.taufik.androidintemediate.advancedatabase.paging.database.QuoteDatabase
import com.taufik.androidintemediate.advancedatabase.paging.network.ApiService

class QuoteRepository(private val quoteDatabase: QuoteDatabase, private val apiService: ApiService) {
    fun getQuote(): LiveData<PagingData<QuoteResponseItem>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { QuotePagingSource(apiService) }
        ).liveData
    }
}