package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    @PrimaryKey
    val courseId: Int,
    val name: String,
)