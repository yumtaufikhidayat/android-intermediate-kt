package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.*

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStudent(student: List<StudentEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUniversity(university: List<UniversityEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourseStudentCrossRef(courseStudentCrossRef: List<CourseStudentCrossRef>)

    @RawQuery(observedEntities = [StudentEntity::class])
    fun getAllStudent(query: SupportSQLiteQuery): DataSource.Factory<Int, StudentEntity>

    @Transaction
    @Query("SELECT * FROM tbl_student")
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>>

    @Transaction
    @Query("SELECT * FROM tbl_university")
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>>

    @Transaction
    @Query("SELECT * FROM tbl_student")
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>>
}