package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class StudentAndUniversity(
    @Embedded
    val student: StudentEntity,

    @Relation(
        parentColumn = "univId",
        entityColumn = "universityId"
    )
    val university: UniversityEntity? = null
)
