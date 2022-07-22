package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UniversityAndStudent(
    @Embedded
    val university: UniversityEntity,
    @Relation(
        parentColumn = "universityId",
        entityColumn = "univId"
    )
    val student: List<StudentEntity>
)