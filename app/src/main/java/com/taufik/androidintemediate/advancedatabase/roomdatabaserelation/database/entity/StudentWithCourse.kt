package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class StudentWithCourse(
    @Embedded
    val studentAndUniversity: StudentAndUniversity,
    @Relation(
        parentColumn = "studentId",
        entity = CourseEntity::class,
        entityColumn = "courseId",
        associateBy = Junction(
            value = CourseStudentCrossRef::class,
            parentColumn = "sId",
            entityColumn = "cId"
        )
    )
    val course: List<CourseEntity>
)
