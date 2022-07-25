package com.taufik.androidintemediate.advancedatabase.paging.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuote(quote: List<QuoteResponseItem>)

    @Query("SELECT * FROM quote")
    fun getAllQuote(): PagingSource<Int, QuoteResponseItem>

    @Query("DELETE FROM quote")
    fun deleteAll()
}