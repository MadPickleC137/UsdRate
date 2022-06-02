package com.madpickle.usdrate.database

import androidx.room.*

/**
 * Created by David Madilyan on 31.05.2022.
 */
@Dao
interface CourseRangeDao {

    @Query("SELECT * FROM courses")
    fun getAllCourses(): List<CourseRangeEntity>

    @Query("SELECT * FROM courses WHERE idCode=:code")
    fun selectCourseByCode(code: String): CourseRangeEntity

    @Insert
    fun insertCourse(courseEntity: CourseRangeEntity)

    @Insert
    fun insertAllCourses(list: List<CourseRangeEntity>)

    @Update
    fun updateCourseRange(newCourseRangeEntity: CourseRangeEntity)

    @Delete
    fun deleteCourseRange(courseEntity: CourseRangeEntity)
}


@Entity(tableName = "courses")
data class CourseRangeEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                        val name: String? = null,
                        val idCode: String? = null,
                        val date: String? = null,
                        val nominal: Int? = null)