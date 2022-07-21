package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.Common

@Database(
    entities = [StudentEntity::class, UniversityEntity::class, CourseEntity::class],
    version = Common.DB_VERSION,
    exportSchema = false
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        Common.DB_NAME
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return INSTANCE as StudentDatabase
        }
    }
}