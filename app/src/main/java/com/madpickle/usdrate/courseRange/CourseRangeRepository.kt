package com.madpickle.usdrate.courseRange

import androidx.lifecycle.MutableLiveData
import com.madpickle.usdrate.core.SyncResult
import com.madpickle.usdrate.core.extensions.convertToDate
import com.madpickle.usdrate.data.CourseRange
import com.madpickle.usdrate.database.usecase.CoursesRangeUseCase
import com.madpickle.usdrate.remote.CbrUseCase
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

/**
 * Created by David Madilyan on 07.06.2022.
 */
class CourseRangeRepository @Inject constructor(private val cbrUseCase: CbrUseCase,
                                                private val courseUseCase: CoursesRangeUseCase) {

    suspend fun syncCourseRange(start: String, end: String, code: String): MutableLiveData<SyncResult>{
        val result = MutableLiveData(SyncResult.LOADING)
        val response = cbrUseCase.getCourseRange(start, end, code)
        when {
            response == null -> result.value = SyncResult.ERROR
            response.isNotEmpty() -> {
                courseUseCase.putListCourseRange(response)
                result.value = SyncResult.SUCCESS
            }
            else -> result.value = SyncResult.EMPTY
        }
        return result
    }


    suspend fun flowCourseRange(code: String, start: String, end: String): Flow<List<CourseRange>>{
        //конвертировать строки в даты
        val dateStart =  start.convertToDate() ?:
        Date.from(LocalDateTime.now()
            .minusMonths(1)
            .atZone(ZoneId.systemDefault())
            .toInstant())
        val dateEnd = end.convertToDate() ?: Date.from(LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant())
       return  courseUseCase.getFlowCourseRange(code, dateStart, dateEnd)
    }

}