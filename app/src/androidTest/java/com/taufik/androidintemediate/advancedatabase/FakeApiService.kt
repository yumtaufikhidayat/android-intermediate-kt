package com.taufik.androidintemediate.advancedatabase

import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem
import com.taufik.androidintemediate.advancedatabase.paging.network.ApiService

class FakeApiService : ApiService {

    override suspend fun getQuote(page: Int, size: Int): List<QuoteResponseItem> {
        val items: MutableList<QuoteResponseItem> = arrayListOf()

        for (i in 0..100) {
            val quote = QuoteResponseItem(
                i.toString(),
                "author + $i",
                "quote $i",
            )
            items.add(quote)
        }

        return items.subList((page - 1) * size, (page - 1) * size + size)
    }
}