package com.madpickle.usdrate.courseRange

import androidx.lifecycle.*
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.AppContext
import com.madpickle.usdrate.core.extensions.getStringSlashDate
import com.madpickle.usdrate.data.CourseDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CourseRangeViewModel @Inject constructor(private val appContext: AppContext,
                                               private val repository: CourseRangeRepository)
    : ViewModel() {

    private val courseDay = MutableLiveData<CourseDay>()
    private val dateRange = MutableLiveData(getDefaultDateRange())

    val subTitle = dateRange.map { dateRange ->
        String.format(appContext.context.getString(R.string.course_range_subtitle),
            dateRange.first,
            dateRange.second
        )
    }

    val title = courseDay.map {
        it.name ?: appContext.context.getString(R.string.course_range_title_plug)
    }

    private val collectorData = dateRange.map { dateRange ->
        RequestCourseRange(courseDay.value?.idCode, dateRange.first, dateRange.second)
    }

    val listCourse = collectorData.switchMap { request ->
        liveData(viewModelScope.coroutineContext)  {
            repository.flowCourseRange(request.code ?: "", request.start, request.end
            ).collect {
                this.emit(it)
            }
        }
    }

    val chartSeries = listCourse.map { list ->
        list.mapNotNull { it.value }
    }

    val resultState = collectorData.switchMap { request ->
        liveData(viewModelScope.coroutineContext)  {
            emitSource(repository.syncCourseRange(request.start, request.end, request.code ?: ""))
        }
    }

    private fun getDefaultDateRange(): Pair<String, String>{
        return Pair(
            LocalDateTime.now().minusMonths(1).getStringSlashDate(),
            LocalDateTime.now().getStringSlashDate()
        )
    }

    fun setCourseDay(params: CourseDay) = viewModelScope.launch {
        checkNotNull(params.idCode)
        courseDay.value = params
    }

    fun getCourseDay() = courseDay.value

    fun setDateRange(start: String, end: String){
        dateRange.value = Pair(start, end)
    }

}

private data class RequestCourseRange(val code: String?, val start: String, val end: String)