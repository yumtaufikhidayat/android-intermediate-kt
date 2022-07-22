package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.Common

@Entity (tableName = Common.COURSE_ENTITY)
data class CourseEntity(
    @PrimaryKey
    val courseId: Int,
    val name: String,
)