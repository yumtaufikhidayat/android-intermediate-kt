package com.taufik.androidintemediate.advancedtesting.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taufik.androidintemediate.advancedtesting.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE news.title = :newsTitle")
    fun deleteNews(newsTitle: String)

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title)")
    fun isNewsBookmarked(title: String): LiveData<Boolean>
}