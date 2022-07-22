package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper

import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseStudentCrossRef
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityEntity

object InitialDataSource {
    fun getUniversities(): List<UniversityEntity> {
        return listOf(
            UniversityEntity(1, "Android University"),
            UniversityEntity(2, "Web University"),
            UniversityEntity(3, "Machine Learning University"),
            UniversityEntity(4, "Cloud University"),
        )
    }

    fun getStudents(): List<StudentEntity> {
        return listOf(
            StudentEntity(1, "Andy Rubin", 1),
            StudentEntity(2, "Rich Miner", 1),
            StudentEntity(3, "Tim Berners-Lee", 2),
            StudentEntity(4, "Robert Cailliau", 2),
            StudentEntity(5, "Arthur Samuel", 3),
            StudentEntity(6, "JCR Licklider", 4),
        )
    }

    fun getCourses(): List<CourseEntity> {
        return listOf(
            CourseEntity(1, "Kotlin Basic"),
            CourseEntity(2, "Java Basic"),
            CourseEntity(3, "Javascript Basic"),
            CourseEntity(4, "Python Basic"),
            CourseEntity(5, "Dart Basic"),
        )
    }

    fun getCourseStudentRelation(): List<CourseStudentCrossRef> {
        return listOf(
            CourseStudentCrossRef(1, 1),
            CourseStudentCrossRef(1, 2),
            CourseStudentCrossRef(2, 2),
            CourseStudentCrossRef(2, 5),
            CourseStudentCrossRef(3, 3),
            CourseStudentCrossRef(4, 3),
            CourseStudentCrossRef(4, 4),
            CourseStudentCrossRef(5, 4),
            CourseStudentCrossRef(6, 3),
            CourseStudentCrossRef(6, 4),
        )
    }
}