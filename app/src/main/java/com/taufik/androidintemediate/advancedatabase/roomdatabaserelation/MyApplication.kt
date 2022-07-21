package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation

import android.app.Application
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.StudentDatabase

class MyApplication : Application(){
    private val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}