package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.StudentDao
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentAndUniversity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentWithCourse
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityAndStudent
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.SortType
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.SortUtils

class StudentRepository(private val studentDao: StudentDao) {
    fun getAllStudent(sortType: SortType): LiveData<PagedList<StudentEntity>> {
        val query = SortUtils.getSortedQuery(sortType)
        val student = studentDao.getAllStudent(query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(student, config).build()
    }
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = studentDao.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = studentDao.getAllUniversityAndStudent()
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = studentDao.getAllStudentWithCourse()
}