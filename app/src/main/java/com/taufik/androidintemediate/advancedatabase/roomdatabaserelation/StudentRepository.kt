package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation

import androidx.lifecycle.LiveData
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.StudentDao
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.InitialDataSource

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudent(): LiveData<List<StudentEntity>> = studentDao.getAllStudent()
    suspend fun insertAllData() {
        studentDao.insertStudent(InitialDataSource.getStudents())
        studentDao.insertUniversity(InitialDataSource.getUniversities())
        studentDao.insertCourse(InitialDataSource.getCourses())
    }
}