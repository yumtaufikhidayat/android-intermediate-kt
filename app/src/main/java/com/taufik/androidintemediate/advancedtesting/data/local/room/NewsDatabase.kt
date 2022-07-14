package com.taufik.androidintemediate.advancedtesting.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taufik.androidintemediate.advancedtesting.data.local.entity.NewsEntity
import com.taufik.androidintemediate.utils.Common

@Database(entities = [NewsEntity::class], version = Common.DB_VERSION, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: NewsDatabase? = null
        fun getInstance(context: Context) : NewsDatabase =
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                Common.DB_NAME,

            ).fallbackToDestructiveMigration().build()
    }
}