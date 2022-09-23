package com.madpickle.usdrate.dailyCourses

import androidx.lifecycle.MutableLiveData
import com.madpickle.usdrate.core.SyncResult
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.database.datasource.CourseDayDataSource
import com.madpickle.usdrate.remote.CbrDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by David Madilyan on 04.06.2022.
 */
class DailyCoursesRepository @Inject constructor(private val cbrDataSource: CbrDataSource,
                                                 private val courseDayDataSource: CourseDayDataSource
) {

    /**
     * Получение данных о курсах валют за выбранный день
     * @param day формат строки dd/mm/yyyy
     * */
    suspend fun syncDailyCourse(day: String): MutableLiveData<SyncResult> = coroutineScope {
        val result = MutableLiveData(SyncResult.LOADING)
        val response = cbrDataSource.getCourseByDay(day)
        when {
            response == null -> result.value = SyncResult.ERROR
            response.isNotEmpty() -> {
                courseDayDataSource.putNewCoursesDay(response, day)
                result.value = SyncResult.SUCCESS
            }
            else -> result.value = SyncResult.EMPTY
        }
        result
    }

    /**
     * Подписка на курсы валют по дню
     * */
    suspend fun flowCoursesByDay(day: String): Flow<List<CourseDay>> {
        return  courseDayDataSource.getCoursesByDay(day)
    }
}