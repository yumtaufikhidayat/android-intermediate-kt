package com.taufik.androidintemediate.animation.propertyanimation.view.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufik.androidintemediate.animation.propertyanimation.model.UserModel
import com.taufik.androidintemediate.animation.propertyanimation.model.UserPreference
import kotlinx.coroutines.launch

class SignUpViewModel(private val pref: UserPreference): ViewModel() {
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}