package com.taufik.androidintemediate.advancedatabase.paging

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidintemediate.advancedatabase.paging.di.Injection
import com.taufik.androidintemediate.advancedatabase.paging.ui.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(Injection.provideRepository(context)) as T
            else -> throw IllegalArgumentException("Unknown viewModel class ${modelClass.name}")
        }
    }
}