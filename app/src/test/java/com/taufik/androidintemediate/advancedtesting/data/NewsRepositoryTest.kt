package com.taufik.androidintemediate.advancedtesting.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.taufik.androidintemediate.DataDummy
import com.taufik.androidintemediate.MainDispatcherRule
import com.taufik.androidintemediate.advancedtesting.data.local.room.NewsDao
import com.taufik.androidintemediate.advancedtesting.data.remote.retrofit.ApiService
import com.taufik.androidintemediate.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: ApiService
    private lateinit var newsDao: NewsDao
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        newsDao = FakeNewsDao()
        newsRepository = NewsRepository(apiService, newsDao)
    }

    @Test
    fun `when getNews should not null`() = runTest {
        val expectedNews = DataDummy.generateDummyNewsResponse()
        val actualNews = apiService.getNews("apiKey")
        Assert.assertNotNull(actualNews)
        Assert.assertEquals(expectedNews.articles.size, actualNews.articles.size)
    }

    @Test
    fun `when saveNews should exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.saveNews(sampleNews)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.contains(sampleNews))
        Assert.assertTrue(newsDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }

    @Test
    fun `when deleteNews should not exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.apply {
            saveNews(sampleNews)
            deleteNews(sampleNews.title)
        }

        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertFalse(actualNews.contains(sampleNews))
        Assert.assertFalse(newsDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
}