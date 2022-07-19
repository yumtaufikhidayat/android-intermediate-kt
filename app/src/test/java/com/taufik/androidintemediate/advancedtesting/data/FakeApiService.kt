package com.taufik.androidintemediate.advancedtesting.data

import com.taufik.androidintemediate.DataDummy
import com.taufik.androidintemediate.advancedtesting.data.remote.response.NewsResponse
import com.taufik.androidintemediate.advancedtesting.data.remote.retrofit.ApiService

class FakeApiService: ApiService{
    private val dummyResponse = DataDummy.generateDummyNewsResponse()
    override suspend fun getNews(apiKey: String): NewsResponse {
        return dummyResponse
    }
}