package com.madpickle.usdrate.database

import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

/**
 * Created by David Madilyan on 01.06.2022.
 */
interface CourseDayDao {
    @Query("SELECT * FROM courses_of_day")
    fun getAllDayCourses(): List<CourseDayEntity>

    @Insert
    fun insertCourseDay(courseDayEntity: CourseDayEntity)

    @Insert
    fun insertAllCourseDay(list: List<CourseDayEntity>)

    @Query("SELECT * FROM courses_of_day WHERE idCode=:code")
    fun selectCourseDayByCode(code: String): CourseDayEntity


}

@Entity(tableName = "courses_of_day")
data class CourseDayEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                           val idCode: String? = null,
                           val nominal: Int? = null,
                           val charCode: String? = null,
                           val name: String? = null,
                           val value: Long? = null)