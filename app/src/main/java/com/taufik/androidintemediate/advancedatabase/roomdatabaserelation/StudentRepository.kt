package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation

import androidx.lifecycle.LiveData
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.StudentDao
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentAndUniversity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentWithCourse
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityAndStudent
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
        CoroutineScope(Dispatchers.IO).launch { studentDao.insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation()) }
    }

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentDao.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentDao.getAllUniversityAndStudent()
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentDao.getAllStudentWithCourse()
}