package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
        }
    }
}