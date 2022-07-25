package com.taufik.androidintemediate.advancedatabase.paging.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.taufik.androidintemediate.DataDummy
import com.taufik.androidintemediate.MainDispatcherRule
import com.taufik.androidintemediate.advancedatabase.QuotePagingSource
import com.taufik.androidintemediate.advancedatabase.paging.adapter.QuoteListAdapter
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteRepository
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem
import com.taufik.androidintemediate.advancedatabase.paging.ui.NoopList.noopListUpdateCallback
import com.taufik.androidintemediate.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var quoteRepository: QuoteRepository

    @Test
    fun `when Get Quote Should Not Null and Return Success`() = runTest {
        val dummyQuote = DataDummy.generateDummyQuoteResponse()
        val data: PagingData<QuoteResponseItem> = QuotePagingSource.snapshot(dummyQuote)
        val expectedQuote = MutableLiveData<PagingData<QuoteResponseItem>>()
        expectedQuote.value = data
        Mockito.`when`(quoteRepository.getQuote()).thenReturn(expectedQuote)

        val mainViewModel = MainViewModel(quoteRepository)
        val actualQuote: PagingData<QuoteResponseItem> = mainViewModel.quote.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = QuoteListAdapter.quoteDiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyQuote, differ.snapshot())
        Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
        Assert.assertEquals(dummyQuote[0].author, differ.snapshot()[0]?.author)
    }
}