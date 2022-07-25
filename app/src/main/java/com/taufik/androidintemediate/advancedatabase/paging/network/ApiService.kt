package com.taufik.androidintemediate.advancedatabase.paging.network

import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getQuote(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<QuoteResponseItem>
}