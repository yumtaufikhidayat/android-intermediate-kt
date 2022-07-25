package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.StudentRepository
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentAndUniversity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentWithCourse
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityAndStudent
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.SortType

class MainViewModel(private val repository: StudentRepository) : ViewModel(){

    private val _sort = MutableLiveData<SortType>()

    init {
        _sort.value = SortType.ASCENDING
    }

    fun changeSortType(sortType: SortType) {
        _sort.value = sortType
    }

    fun getAllStudent(): LiveData<List<StudentEntity>> = _sort.switchMap {
        repository.getAllStudent(it)
    }
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = repository.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = repository.getAllUniversityAndStudent()
    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = repository.getAllStudentWithCourse()
}