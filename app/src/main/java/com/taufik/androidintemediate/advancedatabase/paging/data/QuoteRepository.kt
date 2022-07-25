package com.taufik.androidintemediate.advancedatabase.paging.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.taufik.androidintemediate.advancedatabase.paging.database.QuoteDatabase
import com.taufik.androidintemediate.advancedatabase.paging.network.ApiService

class QuoteRepository(private val quoteDatabase: QuoteDatabase, private val apiService: ApiService) {
    fun getQuote(): LiveData<PagingData<QuoteResponseItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = QuoteRemoteMediator(quoteDatabase, apiService),
            pagingSourceFactory = { quoteDatabase.quoteDao().getAllQuote() }
//            pagingSourceFactory = { QuotePagingSource(apiService) }
        ).liveData
    }
}