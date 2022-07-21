package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UniversityEntity(
    @PrimaryKey
    val universityId: Int,
    val name: String,
)