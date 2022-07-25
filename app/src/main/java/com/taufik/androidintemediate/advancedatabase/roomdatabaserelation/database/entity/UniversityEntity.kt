package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.Common

@Entity (tableName = Common.UNIVERSITY_ENTITY)
data class UniversityEntity(
    @PrimaryKey
    val universityId: Int,
    @ColumnInfo(name = "universityName")
    val name: String,
)