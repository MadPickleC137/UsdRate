package com.madpickle.usdrate.database

import androidx.room.*
import com.madpickle.usdrate.data.CourseDay
import kotlinx.coroutines.flow.Flow

/**
 * Created by David Madilyan on 01.06.2022.
 */
@Dao
interface CourseDayDao {
    @Query("SELECT * FROM courses_of_day")
    fun getAllDayCourses(): Flow<List<CourseDayEntity>>

    @Transaction
    suspend fun updateAllCourseDay(day: String, list: List<CourseDayEntity>){
        deleteCurrencies(day)
        insertAllCourseDay(list)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCourseDay(list: List<CourseDayEntity>)

    @Query("SELECT * FROM courses_of_day WHERE date=:day")
    fun selectCourseByDay(day: String): Flow<List<CourseDayEntity>>

    @Query("DELETE FROM courses_of_day WHERE date=:day")
    suspend fun deleteCurrencies(day: String)
}

@Entity(tableName = "courses_of_day")
data class CourseDayEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                           val idCode: String? = null,
                           val nominal: Int? = null,
                           val charCode: String? = null,
                           val date: String? = null,            // формат строки должен быть таким dd/MM/yyyy
                           val name: String? = null,
                           val value: Double? = null){
    fun toCourseDay() = CourseDay(idCode, nominal, charCode, name, value)

    companion object{
        fun fromCourseDay(courseDay: CourseDay, day: String): CourseDayEntity{
            return CourseDayEntity(
                idCode = courseDay.idCode,
                nominal = courseDay.nominal,
                name = courseDay.name,
                value = courseDay.value,
                date = day,
                charCode = courseDay.charCode
            )
        }
    }
}