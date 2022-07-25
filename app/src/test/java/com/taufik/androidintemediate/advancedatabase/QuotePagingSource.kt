package com.taufik.androidintemediate.advancedatabase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem

class QuotePagingSource : PagingSource<Int, LiveData<List<QuoteResponseItem>>>() {
    companion object {
        fun snapshot(items: List<QuoteResponseItem>): PagingData<QuoteResponseItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<QuoteResponseItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<QuoteResponseItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}