package com.taufik.androidintemediate.advancedtesting.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.taufik.androidintemediate.DataDummy
import com.taufik.androidintemediate.advancedtesting.data.NewsRepository
import com.taufik.androidintemediate.advancedtesting.data.local.entity.NewsEntity
import com.taufik.androidintemediate.advancedtesting.data.remote.Result
import com.taufik.androidintemediate.advancedtesting.data.remote.Result.Success
import com.taufik.androidintemediate.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.generateDummyNewsEntity()

    @Before
    fun setUp() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when get headline news should not null and return success`() {
        val observer = Observer<Result<List<NewsEntity>>> {}
        try {
            val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
            expectedNews.value = Success(dummyNews)

            `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

            val actualNews = newsViewModel.getHeadLineNews().getOrAwaitValue()

            Mockito.verify(newsRepository).getHeadlineNews()
            Assert.assertNotNull(actualNews)
            Assert.assertTrue(actualNews is Success)
            Assert.assertEquals(dummyNews.size, (actualNews as Success).data.size)
        } finally {
            newsViewModel.getHeadLineNews().removeObserver(observer)
        }
    }

    @Test
    fun `when network error should return error`() {
        val headlineNews = MutableLiveData<Result<List<NewsEntity>>>()
        headlineNews.value = Result.Error("Error")
        `when`(newsRepository.getHeadlineNews()).thenReturn(headlineNews)

        val actualNews = newsViewModel.getHeadLineNews().getOrAwaitValue()
        Mockito.verify(newsRepository).getHeadlineNews()

        Assert.assertNotNull(actualNews)
        Assert.assertTrue(actualNews is Result.Error)
    }
}