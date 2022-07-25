package com.taufik.androidintemediate.advancedatabase.paging.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteRepository
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem

class MainViewModel(quoteRepository: QuoteRepository) : ViewModel() {
    val quote: LiveData<PagingData<QuoteResponseItem>> =
        quoteRepository.getQuote().cachedIn(viewModelScope)
}