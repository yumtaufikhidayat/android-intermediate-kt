package com.taufik.androidintemediate.advancedatabase.paging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taufik.androidintemediate.advancedatabase.paging.network.ApiService

class QuotePagingSource(private val apiService: ApiService): PagingSource<Int, QuoteResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, QuoteResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteResponseItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getQuote(page, params.loadSize)

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}