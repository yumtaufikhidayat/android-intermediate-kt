package com.taufik.androidintemediate.advancedtesting.ui.list

import androidx.lifecycle.ViewModel
import com.taufik.androidintemediate.advancedtesting.data.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadLineNews() = newsRepository.getHeadlineNews()
    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
}