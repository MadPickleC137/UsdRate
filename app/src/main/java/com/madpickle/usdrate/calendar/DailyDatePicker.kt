package com.madpickle.usdrate.calendar

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.madpickle.usdrate.R
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

/**
 * Created by David Madilyan on 08.06.2022.
 *
 * Выбор одной даты для списка разных валют
 */
fun showDailyDatePicker(context: Context, supportFragmentManager: FragmentManager, action: (LocalDateTime) -> Unit){
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR , -1 )
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setInputMode(INPUT_MODE_CALENDAR)
        .setCalendarConstraints( CalendarConstraints.Builder()
            .setStart(calendar.timeInMillis)
            .setEnd(Date().time)
            .build()
        )
        .setSelection(Date().time)
        .setTitleText(context.getString(R.string.select_date_title))
        .build()

    datePicker.show(supportFragmentManager, context.getString(R.string.select_date_title))
    datePicker.addOnPositiveButtonClickListener { timestamp ->
        val now = Calendar.getInstance()
        val dateTime =  LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            TimeZone.getDefault().toZoneId())
        Timber.d("PICKED DATE: $dateTime")
        if(now.timeInMillis >= timestamp){
            action.invoke(dateTime)
        }
    }
}