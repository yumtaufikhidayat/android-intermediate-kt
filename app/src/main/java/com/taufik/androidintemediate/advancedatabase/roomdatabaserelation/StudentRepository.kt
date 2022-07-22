package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation

import androidx.lifecycle.LiveData
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.StudentDao
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.InitialDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudent(): LiveData<List<StudentEntity>> = studentDao.getAllStudent()
    fun insertAllData() {
        CoroutineScope(Dispatchers.IO).launch { studentDao.insertStudent(InitialDataSource.getStudents()) }
        CoroutineScope(Dispatchers.IO).launch { studentDao.insertUniversity(InitialDataSource.getUniversities()) }
        CoroutineScope(Dispatchers.IO).launch { studentDao.insertCourse(InitialDataSource.getCourses()) }
    }
}