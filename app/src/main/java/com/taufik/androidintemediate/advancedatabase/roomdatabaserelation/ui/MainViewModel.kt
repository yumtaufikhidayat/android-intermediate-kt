package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.StudentRepository
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StudentRepository) : ViewModel(){

    init {
        insertAllData()
    }

    fun getAllStudent(): LiveData<List<StudentEntity>> = repository.getAllStudent()

    private fun insertAllData() = viewModelScope.launch {
        repository.insertAllData()
    }
}