package com.madpickle.usdrate.database

import androidx.room.*
import com.madpickle.usdrate.data.CourseRange
import kotlinx.coroutines.flow.Flow

/**
 * Created by David Madilyan on 31.05.2022.
 */
@Dao
interface CourseRangeDao {

    @Query("SELECT * FROM courses")
    suspend fun getAllCourses(): Flow<List<CourseRangeEntity>>

    @Query("SELECT * FROM courses WHERE idCode=:code")
    suspend fun getCoursesByCode(code: String): Flow<List<CourseRangeEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourseRanges(list: List<CourseRangeEntity>)


    @Delete
    suspend fun deleteItemCourse(courseEntity: CourseRangeEntity)

    @Query("DELETE FROM courses")
    suspend fun deleteAll()
}


@Entity(tableName = "courses")
data class CourseRangeEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                        val idCode: String? = null,
                        val date: String? = null,
                        val value: Long? = null,
                        val nominal: Int? = null){
    fun toCourseRange() = CourseRange(idCode, date, nominal, value)

    companion object{
        fun fromCourseRange(courseRange: CourseRange): CourseRangeEntity{
            return CourseRangeEntity(
                idCode = courseRange.idCode,
                date = courseRange.date,
                value = courseRange.value,
                nominal = courseRange.nominal
            )
        }
    }

}