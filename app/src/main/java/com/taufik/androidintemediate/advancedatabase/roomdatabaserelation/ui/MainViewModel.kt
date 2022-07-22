package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.StudentRepository
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentAndUniversity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityAndStudent
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StudentRepository) : ViewModel(){

    init {
        insertAllData()
    }

    fun getAllStudent(): LiveData<List<StudentEntity>> = repository.getAllStudent()
    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> = repository.getAllStudentAndUniversity()
    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> = repository.getAllUniversityAndStudent()

    private fun insertAllData() = viewModelScope.launch {
        repository.insertAllData()
    }
}