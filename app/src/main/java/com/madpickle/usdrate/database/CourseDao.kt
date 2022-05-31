package com.madpickle.usdrate.database

import androidx.room.*

/**
 * Created by David Madilyan on 31.05.2022.
 */
@Dao
interface CourseDao {

    @Query("SELECT * FROM courses")
    fun getAllCourses(): List<CourseEntity>

    @Query("SELECT * FROM courses WHERE idCode=:code")
    fun selectCourseByCode(code: String): CourseEntity

    @Insert
    fun insertCourse(courseEntity: CourseEntity)

    @Insert
    fun insertAllCourses(list: List<CourseEntity>)

    @Update
    fun updateCourse(newCourseEntity: CourseEntity)

    @Delete
    fun deleteCourse(courseEntity: CourseEntity)
}


@Entity(tableName = "courses")
data class CourseEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                        val name: String? = null,
                        val idCode: String? = null,
                        val date: String? = null,
                        val nominal: Int? = null)