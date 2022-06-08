package com.madpickle.usdrate.data

import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by David Madilyan on 31.05.2022.
 *
 * Курс одной валюты по каждому дню
 */
data class CourseRange(val idCode: String?,
                  val date: String?,
                  val nominal: Int?,
                  val value: Double?)

/**
 * Курс валюты тоже по дню, но получаемый из списка по разным валютам, за определенную дату
 * */
@Parcelize
data class CourseDay(val idCode: String?,
                       val nominal: Int?,
                       val charCode: String?,
                       val name: String?,
                       val value: Double?): Parcelable {

    companion object{
        const val ARG_COURSE_DAY = "ARG_COURSE_DAY"

        fun toBundle(courseDay: CourseDay): Bundle {
            return Bundle().apply {
                putParcelable(ARG_COURSE_DAY, courseDay)
            }
        }
    }
}