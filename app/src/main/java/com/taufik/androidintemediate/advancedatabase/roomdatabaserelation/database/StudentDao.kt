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
    fun insertStudent(student: List<StudentEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUniversity(university: List<UniversityEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: List<CourseEntity>)

    @Query("SELECT * FROM tbl_student")
    fun getAllStudent(): LiveData<List<StudentEntity>>
}