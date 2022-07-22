package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.Common

@Entity (tableName = Common.STUDENT_ENTITY)
data class StudentEntity(
    @PrimaryKey
    val studentId: Int,
    val name: String,
    val univId: Int
)