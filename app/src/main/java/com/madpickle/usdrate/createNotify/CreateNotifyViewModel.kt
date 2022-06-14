package com.madpickle.usdrate.createNotify

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.AppContext
import com.madpickle.usdrate.core.ValidationFields
import com.madpickle.usdrate.core.utils.Event
import com.madpickle.usdrate.core.utils.TAG_WORKER
import com.madpickle.usdrate.data.CourseDay
import com.madpickle.usdrate.data.NotificationData
import com.madpickle.usdrate.database.usecase.NotificationDataSource
import com.madpickle.usdrate.worker.CourseEqualWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by David Madilyan on 09.06.2022.
 */
@HiltViewModel
class CreateNotifyViewModel @Inject constructor(private val appContext: AppContext,
                                                private val workManager: WorkManager,
                                                private val notificationDataSource: NotificationDataSource): ViewModel(){
    private val courseDay = MutableLiveData<CourseDay>()

    private val _validation = MutableLiveData<ValidationFields>()
    val validation = _validation.map { it }
    val createWorkerEvent = MutableLiveData<Event<Any>>()
    private var _minValue: Double = -0.1
    private var _maxValue: Double = -0.1

    val courseName = courseDay.map {
        String.format(appContext.context.getString(R.string.create_notify_course_title), it.name)
    }

    val courseValue = courseDay.map {
        String.format(appContext.context.getString(R.string.create_notify_value), it.value)
    }

    fun setCourseDay(_courseDay: CourseDay){
        courseDay.value = _courseDay
    }

    fun setMinValue(value: Double){
        _minValue = value
    }
    fun setMaxValue(value: Double){
        _maxValue = value
    }

    private fun validationValues(): Boolean{
        return when{
            _minValue <= 0.0 ||_maxValue <= 0.0 -> {
                _validation.value = ValidationFields.EMPTY
                false
            }
            _minValue >= _maxValue -> {
                _validation.value = ValidationFields.MISMATCH
                false
            }
            else -> {
                _validation.value = ValidationFields.SUCCESS
                true
            }
        }
    }

    fun setNotification() = viewModelScope.launch {
        if(validationValues()){
            val notify = NotificationData(
                codeValute = courseDay.value?.idCode,
                minValue = _minValue,
                maxValue = _maxValue,
                nameValute = courseDay.value?.name
            )
            notificationDataSource.setNewNotification(notify)
            makeWorker()
        }
    }

    private fun makeWorker() {
        val constraints: Constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val courseEqualWorker = PeriodicWorkRequest.Builder(CourseEqualWorker::class.java, 12, TimeUnit.HOURS)
                .addTag(TAG_WORKER)
                .setConstraints(constraints).build()
        workManager.enqueue(courseEqualWorker)
        createWorkerEvent.postValue(Event(Any()))
    }

}