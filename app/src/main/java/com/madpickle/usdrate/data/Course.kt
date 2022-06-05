package com.madpickle.usdrate.data

import java.util.*

/**
 * Created by David Madilyan on 31.05.2022.
 *
 * Курс одной валюты по каждому дню
 */
data class CourseRange(val idCode: String?,
                  val date: String?,
                  val nominal: Int?,
                  val value: Long?)

/**
 * Курс валюты тоже по дню, но получаемый из списка по разным валютам, за определенную дату
 * */
data class CourseDay(val idCode: String?,
                       val nominal: Int?,
                       val charCode: String?,
                       val name: String?,
                       val value: Long?)