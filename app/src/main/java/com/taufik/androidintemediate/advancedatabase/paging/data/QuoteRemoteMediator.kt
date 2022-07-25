package com.taufik.androidintemediate.advancedatabase.paging.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.taufik.androidintemediate.advancedatabase.paging.database.QuoteDatabase
import com.taufik.androidintemediate.advancedatabase.paging.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator(
    private val database: QuoteDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, QuoteResponseItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, QuoteResponseItem>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX

        try {
            val responseData = apiService.getQuote(page, state.config.pageSize)
            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    CoroutineScope(Dispatchers.IO).launch {
                        database.quoteDao().getAllQuote()
                    }
                }
                CoroutineScope(Dispatchers.IO).launch { database.quoteDao().insertQuote(responseData) }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}