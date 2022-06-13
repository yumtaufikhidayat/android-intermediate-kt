package com.taufik.androidintemediate.animation.propertyanimation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taufik.androidintemediate.animation.propertyanimation.model.UserPreference
import com.taufik.androidintemediate.animation.propertyanimation.view.login.LoginViewModel
import com.taufik.androidintemediate.animation.propertyanimation.view.main.MainViewModel
import com.taufik.androidintemediate.animation.propertyanimation.view.signup.SignUpViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(pref) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(pref) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(pref) as T
            else -> throw IllegalAccessException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}