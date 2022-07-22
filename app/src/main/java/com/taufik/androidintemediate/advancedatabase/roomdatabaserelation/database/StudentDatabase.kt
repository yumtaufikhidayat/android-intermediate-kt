package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseStudentCrossRef
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.Common
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.InitialDataSource

@Database(
    entities = [StudentEntity::class, UniversityEntity::class, CourseEntity::class, CourseStudentCrossRef::class],
    version = Common.DB_VERSION,
    exportSchema = false
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        Common.DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .createFromAsset("StudentDatabase.db")
//                        .addCallback(object : Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                INSTANCE?.let { database ->
//                                    applicationScope.launch {
//                                        val studentDao = database.studentDao()
//                                        studentDao.insertStudent(InitialDataSource.getStudents())
//                                        studentDao.insertUniversity(InitialDataSource.getUniversities())
//                                        studentDao.insertCourse(InitialDataSource.getCourses())
//                                        studentDao.insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation())
//                                    }
//                                }
//                            }
//                        })
                        .build()
                }
            }

            return INSTANCE as StudentDatabase
        }
    }
}