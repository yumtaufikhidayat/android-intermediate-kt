package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.CourseStudentCrossRef
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityEntity
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.Common
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.InitialDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [StudentEntity::class, UniversityEntity::class, CourseEntity::class, CourseStudentCrossRef::class],
    version = Common.DB_VERSION,
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = StudentDatabase.MyAutoMigration::class)
                     ],
    exportSchema = true
)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    @RenameColumn(tableName = Common.UNIVERSITY_ENTITY, fromColumnName = "name", toColumnName = "universityName")
    class MyAutoMigration: AutoMigrationSpec

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
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    applicationScope.launch {
                                        val studentDao = database.studentDao()
                                        studentDao.insertStudent(InitialDataSource.getStudents())
                                        studentDao.insertUniversity(InitialDataSource.getUniversities())
                                        studentDao.insertCourse(InitialDataSource.getCourses())
                                        studentDao.insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation())
                                    }
                                }
                            }
                        })
                        .build()
                }
            }

            return INSTANCE as StudentDatabase
        }
    }
}