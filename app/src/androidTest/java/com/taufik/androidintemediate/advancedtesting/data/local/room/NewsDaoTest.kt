package com.taufik.androidintemediate.advancedtesting.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.taufik.androidintemediate.advancedtesting.utils.DataDummy
import com.taufik.androidintemediate.advancedtesting.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NewsDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsDatabase
    private lateinit var dao: NewsDao
    private val sampleNews = DataDummy.generateDummyNewsEntity()[0]

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDatabase::class.java
        ).build()

        dao = database.newsDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveNewsSuccess() = runTest {
        dao.saveNews(sampleNews)

        val actualNews = dao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertEquals(sampleNews.title, actualNews[0].title)
        Assert.assertTrue(dao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }

    @Test
    fun deleteNewsSuccess() = runTest {
        dao.saveNews(sampleNews)
        dao.deleteNews(sampleNews.title)

        val actualNews = dao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.isEmpty())
        Assert.assertFalse(dao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
}