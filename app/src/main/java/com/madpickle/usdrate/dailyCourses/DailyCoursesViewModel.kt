package com.madpickle.usdrate.dailyCourses

import androidx.lifecycle.*
import com.madpickle.usdrate.core.extensions.getStringPointDate
import com.madpickle.usdrate.core.extensions.getStringSlashDate
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by David Madilyan on 04.06.2022.
 */
@HiltViewModel
class DailyCoursesViewModel @Inject constructor(private val repository: DailyCoursesRepository): ViewModel() {

    private var _selectedDay = MutableLiveData(LocalDateTime.now())
    val selectedDay = _selectedDay.map {
        it.getStringPointDate()
    }

    val syncResult = _selectedDay.switchMap {
        liveData(viewModelScope.coroutineContext) {
            emitSource(repository.syncDailyCourse(it.getStringSlashDate()))
        }
    }

    val listCourseDay = _selectedDay.switchMap {
        liveData(viewModelScope.coroutineContext) {
            repository.flowCoursesByDay(_selectedDay.value.getStringSlashDate()).collect {
                this.emit(it)
            }
        }
    }

    fun setDay(dateTime: LocalDateTime){
        _selectedDay.value = dateTime
    }
}