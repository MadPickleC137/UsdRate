package com.madpickle.usdrate.dailyCourses

import androidx.lifecycle.ViewModel
import com.madpickle.usdrate.data.CourseDay
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by David Madilyan on 04.06.2022.
 */
@HiltViewModel
class DailyCoursesViewModel @Inject constructor(private val repository: DailyCoursesRepository):
    ViewModel() {
    fun onSelectCourse(courseDay: CourseDay) {
        TODO("Not yet implemented")
    }

}