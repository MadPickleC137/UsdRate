package com.madpickle.usdrate.calendar

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.madpickle.usdrate.R
import com.madpickle.usdrate.core.extensions.getStringSlashDate
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

/**
 * Created by David Madilyan on 08.06.2022.
 *
 * Выбор периода дат для одной валюты
 *
 * Полноэкранный календарь
 */

fun showRangeDatePicker(context: Context, supportFragmentManager: FragmentManager, action: (String, String) -> Unit){
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR , -1 )
    val start = Calendar.getInstance()
    start.add(Calendar.MONTH, -1)
    val end = Calendar.getInstance()
    val datePicker = MaterialDatePicker.Builder.dateRangePicker()
        .setCalendarConstraints( CalendarConstraints.Builder()
            .setStart(calendar.timeInMillis)
            .setEnd(Date().time)
            .build()
        )
        .setSelection(androidx.core.util.Pair(start.timeInMillis, end.timeInMillis))
        .setTitleText(context.getString(R.string.select_date_title))
        .build()
    datePicker.show(supportFragmentManager, context.getString(R.string.select_date_title))
    datePicker.addOnPositiveButtonClickListener { pairRange ->
        val dateStart =  LocalDateTime.ofInstant(
            Instant.ofEpochMilli(pairRange.first),
            TimeZone.getDefault().toZoneId()).getStringSlashDate()
        val dateEnd =  LocalDateTime.ofInstant(
            Instant.ofEpochMilli(pairRange.second),
            TimeZone.getDefault().toZoneId()).getStringSlashDate()
        Timber.d("PICKED DATE RANGE: $dateStart----$dateEnd")
        action.invoke(dateStart, dateEnd)
    }
}

