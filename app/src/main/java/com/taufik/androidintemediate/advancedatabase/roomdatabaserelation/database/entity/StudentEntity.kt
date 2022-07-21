package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentEntity(
    @PrimaryKey
    val studentId: Int,
    val name: String,
    val univId: Int
)