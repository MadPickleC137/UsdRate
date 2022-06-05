package com.madpickle.usdrate.dailyCourses

import androidx.lifecycle.MutableLiveData
import com.madpickle.usdrate.core.SyncResult
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.database.usecase.CourseDayUseCase
import com.madpickle.usdrate.remote.CbrUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by David Madilyan on 04.06.2022.
 */
class DailyCoursesRepository @Inject constructor(private val cbrUseCase: CbrUseCase,
                                                 private val courseDayUseCase: CourseDayUseCase
) {

    /**
     * Получение данных о курсах валют за выбранный день
     * @param day формат строки dd/mm/yyyy
     * */
    suspend fun syncDailyCourse(day: String): MutableLiveData<SyncResult> = coroutineScope {
        val result = MutableLiveData(SyncResult.LOADING)
        val response = withContext(Dispatchers.IO){ cbrUseCase.getCourseByDay(day) }
        when {
            response == null -> result.value = SyncResult.ERROR
            response.isNotEmpty() -> {
                courseDayUseCase.putNewCoursesDay(response)
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
        return  courseDayUseCase.getCoursesByDay(day)
    }
}