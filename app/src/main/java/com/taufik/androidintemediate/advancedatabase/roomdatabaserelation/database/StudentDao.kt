package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityEntity

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: List<StudentEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUniversity(university: List<UniversityEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: List<CourseEntity>)

    @Query("SELECT * FROM studententity")
    fun getAllStudent(): LiveData<List<StudentEntity>>
}